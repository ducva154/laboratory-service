package vn.edu.fpt.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.edu.fpt.laboratory.config.kafka.producer.SendEmailProducer;
import vn.edu.fpt.laboratory.constant.*;
import vn.edu.fpt.laboratory.dto.common.CreateFileRequest;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.common.UserInfoResponse;
import vn.edu.fpt.laboratory.dto.event.SendEmailEvent;
import vn.edu.fpt.laboratory.dto.request.material.*;
import vn.edu.fpt.laboratory.dto.response.image.GetImageResponse;
import vn.edu.fpt.laboratory.dto.response.material.CreateMaterialResponse;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialDetailResponse;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialResponse;
import vn.edu.fpt.laboratory.dto.response.material.OrderMaterialResponse;
import vn.edu.fpt.laboratory.entity.*;
import vn.edu.fpt.laboratory.exception.BusinessException;
import vn.edu.fpt.laboratory.repository.*;
import vn.edu.fpt.laboratory.service.MaterialService;
import vn.edu.fpt.laboratory.service.S3BucketStorageService;
import vn.edu.fpt.laboratory.service.UserInfoService;
import vn.edu.fpt.laboratory.utils.FileUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 19:24
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final _ImageRepository imageRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final SendEmailProducer sendEmailProducer;
    private final AppConfigRepository appConfigRepository;
    private final MongoTemplate mongoTemplate;
    private final UserInfoService userInfoService;
    private final S3BucketStorageService s3BucketStorageService;

    @Override
    public CreateMaterialResponse createMaterial(String labId, CreateMaterialRequest request) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Lab ID not exist"));

        List<Material> currentMaterial = laboratory.getMaterials();
        if (currentMaterial.stream().anyMatch(v -> v.getMaterialName().equals(request.getMaterialName()))) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material name already exist");
        }
        _Image image = null;
        CreateFileRequest createFileRequest = request.getImages();
        if(Objects.nonNull(createFileRequest)) {
            String fileKey = UUID.randomUUID().toString();
            s3BucketStorageService.uploadFile(createFileRequest, fileKey);

                image = _Image.builder()
                    .imageName(createFileRequest.getName())
                    .size(FileUtils.getFileSize(createFileRequest.getSize()))
                    .type(createFileRequest.getName().split("\\.")[1])
                    .mimeType(createFileRequest.getMimeType())
                    .length(createFileRequest.getSize())
                    .fileKey(fileKey)
                    .build();
                try {
                    image = imageRepository.save(image);
                }catch (Exception ex){
                    throw new BusinessException("Can't save image to database: "+ ex.getMessage());
                }
        }

        Material material = Material.builder()
                .materialName(request.getMaterialName())
                .description(request.getDescription())
                .amount(request.getAmount())
                .note(request.getNote())
                .images(image)
                .build();

        try {
            material = materialRepository.save(material);
        } catch (Exception ex) {
            throw new BusinessException("Can't save material to database: " + ex.getMessage());
        }

        currentMaterial.add(material);
        laboratory.setMaterials(currentMaterial);

        try {
            laboratoryRepository.save(laboratory);
        } catch (Exception ex) {
            throw new BusinessException("Can't update laboratory after add material to database: " + ex.getMessage());
        }
        return CreateMaterialResponse.builder()
                .materialId(material.getMaterialId())
                .build();
    }


    @Override
    public void updateMaterial(String laboratoryId, String materialId, UpdateMaterialRequest request) {
        Laboratory laboratory = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not exist"));
        List<Material> materials = laboratory.getMaterials();
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material ID not exist"));

        if (materials.stream().noneMatch(m -> m.getMaterialId().equals(materialId))) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory not contain this material");
        }

        if (Objects.nonNull(request.getMaterialName())) {
            if (materials.stream().anyMatch(m -> m.getMaterialName().equals(request.getMaterialName()))) {
                throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material name is already exist");
            } else {
                material.setMaterialName(request.getMaterialName());
            }
        }
        if (Objects.nonNull(request.getDescription())) {
            material.setDescription(request.getDescription());
        }
        if (Objects.nonNull(request.getStatus())) {
            material.setStatus(request.getStatus());
        }
        if (Objects.nonNull(request.getAmount()) && request.getAmount() > 0) {
            material.setAmount(request.getAmount());
        }

        try {
            materialRepository.save(material);
        } catch (Exception ex) {
            throw new BusinessException("Can't save material to database: " + ex.getMessage());
        }

    }

    @Override
    public void deleteMaterial(String laboratoryId, String materialId) {
        Laboratory laboratory = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not exist"));

        List<Material> materialInLab = laboratory.getMaterials();
        Material material = materialInLab.stream().filter(m -> m.getMaterialId().equals(materialId)).findAny()
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material ID not exist"));

        materialInLab.remove(material);

        laboratory.setMaterials(materialInLab);

        try {
            laboratoryRepository.save(laboratory);
        } catch (Exception ex) {
            throw new BusinessException("Can't update laboratory in database");
        }

        try {
            materialRepository.delete(material);
        } catch (Exception ex) {
            throw new BusinessException("Can't delete material in database");
        }

    }

    @Override
    public PageableResponse<GetMaterialResponse> getMaterial(GetMaterialRequest request) {
        Query query = new Query();
        if (Objects.nonNull(request.getMaterialId())) {
            query.addCriteria(Criteria.where("_id").is(request.getMaterialId()));
        }
        if (Objects.nonNull(request.getMaterialName())) {
            query.addCriteria(Criteria.where("material_name").regex(request.getMaterialName()));
        }

        if (Objects.nonNull(request.getStatus())) {
            query.addCriteria(Criteria.where("status").is(request.getStatus()));
        }
        if (Objects.nonNull(request.getDescription())) {
            query.addCriteria(Criteria.where("description").regex(request.getDescription()));
        }

        BaseMongoRepository.addCriteriaWithAuditable(query, request);

        Long totalElements = mongoTemplate.count(query, Material.class);

        BaseMongoRepository.addCriteriaWithPageable(query, request);
        BaseMongoRepository.addCriteriaWithSorted(query, request);

        List<Material> materials = mongoTemplate.find(query, Material.class);
        List<GetMaterialResponse> getMaterialResponses = materials.stream().map(this::convertMaterialToGetMaterialResponse).collect(Collectors.toList());

        return new PageableResponse<>(request, totalElements, getMaterialResponses);
    }

    private GetMaterialResponse convertMaterialToGetMaterialResponse(Material material) {
        return GetMaterialResponse.builder()
                .materialId(material.getMaterialId())
                .materialName(material.getMaterialName())
                .description(material.getDescription())
                .status(material.getStatus())
                .amount(material.getAmount())
                .createdBy(UserInfoResponse.builder()
                        .accountId(material.getCreatedBy())
                        .userInfo(userInfoService.getUserInfo(material.getCreatedBy()))
                        .build())
                .createdDate(material.getCreatedDate())
                .lastModifiedBy(UserInfoResponse.builder()
                        .accountId(material.getLastModifiedBy())
                        .userInfo(userInfoService.getUserInfo(material.getLastModifiedBy()))
                        .build())
                .lastModifiedDate(material.getLastModifiedDate())
                .build();
    }

    @Override
    public GetMaterialDetailResponse getMaterialId(String materialId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material ID not exist"));

        _Image image =  material.getImages();

        return GetMaterialDetailResponse.builder()
                .materialId(material.getMaterialId())
                .materialName(material.getMaterialName())
                .description(material.getDescription())
                .status(material.getStatus())
                .images(convertImageToGetImageResponse(image))
                .note(material.getNote())
                .amount(material.getAmount())
                .createdBy(UserInfoResponse.builder()
                        .accountId(material.getCreatedBy())
                        .userInfo(userInfoService.getUserInfo(material.getCreatedBy()))
                        .build())
                .createdDate(material.getCreatedDate())
                .lastModifiedBy(UserInfoResponse.builder()
                        .accountId(material.getLastModifiedBy())
                        .userInfo(userInfoService.getUserInfo(material.getLastModifiedBy()))
                        .build())
                .lastModifiedDate(material.getLastModifiedDate())
                .build();
    }

    private GetImageResponse convertImageToGetImageResponse(_Image image) {
        return GetImageResponse.builder()
                .imageId(image.getImageId())
                .imageName(image.getImageName())
                .url(s3BucketStorageService.getPublicURL(image.getFileKey()))
                .build();
    }

    @Override
    public OrderMaterialResponse orderMaterial(String laboratoryId, String materialId, OrderMaterialRequest request) {
        Laboratory laboratory = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(()-> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not exist"));
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material ID not exist"));
        if (material.getStatus().equals(MaterialStatusEnum.IN_USED.getStatus())) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "The material unavailable");
        } else if (request.getAmount() > material.getAmount()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Not enough material to order");
        } else if (request.getAmount() == material.getAmount()) {
            material.setAmount(0);
            material.setStatus(MaterialStatusEnum.IN_USED.getStatus());
        } else {
            material.setAmount(material.getAmount() - request.getAmount());
        }

        OrderHistory orderHistory = OrderHistory.builder()
                .reason(request.getReason())
                .materialId(request.getMaterialId())
                .amount(request.getAmount())
                .orderFrom(request.getOrderFrom())
                .orderTo(request.getOrderTo())
                .status(OrderStatusEnum.WAITING_FOR_APPROVAL.getStatus())
                .build();

        List<MemberInfo> managers = laboratory.getMembers().stream()
                .filter(v -> v.getRole().equals(LaboratoryRoleEnum.MANAGER.getRole()) || v.getRole().equals(LaboratoryRoleEnum.OWNER.getRole()))
                .collect(Collectors.toList());
        if(!managers.isEmpty()){
            Optional<AppConfig> orderMaterialTemplateId = appConfigRepository.findByConfigKey("ORDER_MATERIAL_TEMPLATE_ID");
            if(orderMaterialTemplateId.isPresent()) {
                for (MemberInfo member : managers) {
                    String memberEmail = userInfoService.getUserInfo(member.getAccountId()).getEmail();
                    SendEmailEvent sendEmailEvent = SendEmailEvent.builder()
                            .sendTo(memberEmail)
                            .bcc(null)
                            .cc(null)
                            .templateId(orderMaterialTemplateId.get().getConfigValue())
                            .params(Map.of("ORDER_ID", orderHistory.getOrderId()))
                            .build();
                    sendEmailProducer.sendMessage(sendEmailEvent);
                }
            }
        }
        try {
            materialRepository.save(material);
        } catch (Exception ex) {
            throw new BusinessException("Can't save material to database: " + ex.getMessage());
        }

        try {
            orderHistoryRepository.save(orderHistory);
        } catch (Exception ex) {
            throw new BusinessException("Can't save order history to database: " + ex.getMessage());
        }
        return OrderMaterialResponse.builder()
                .orderId(orderHistory.getOrderId())
                .build();
    }

    @Override
    public void returnMaterial(String orderId) {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderId)
                .orElseThrow(()-> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Order history not exist"));
        Material material = materialRepository.findById(orderHistory.getMaterialId())
                .orElseThrow(()-> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material not exist"));
        if (material.getStatus().equals(MaterialStatusEnum.IN_USED.getStatus())) {
            material.setStatus(MaterialStatusEnum.FREE.getStatus());
        }
        material.setAmount(material.getAmount() + orderHistory.getAmount());
        orderHistory.setActuallyReturn(LocalDateTime.now());
        orderHistory.setStatus(OrderStatusEnum.COMPLETED.getStatus());
        try {
            materialRepository.save(material);
        } catch (Exception ex) {
            throw new BusinessException("Can't save material to database: " + ex.getMessage());
        }

        try {
            orderHistoryRepository.save(orderHistory);
        } catch (Exception ex) {
            throw new BusinessException("Can't save order history to database: " + ex.getMessage());
        }
    }

    @Override
    public void removeImage(String materialId, String imageId) {
//        Material material = materialRepository.findById(materialId)
//                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material ID not exist"));
//
//        if (material.getStatus().equals(MaterialStatusEnum.FREE.getStatus())) {
//            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "The material unavailable");
//        }
//        _Image images = material.getImages();
//        if(images.getImageId().equals(imageId)){
//                throw new BusinessException("Can't get image exists");
//        }
//        material.setImages(images);
//        try {
//            materialRepository.save(material);
//        } catch (Exception ex) {
//            throw new BusinessException("Can't addImage material to database: " + ex.getMessage());
//        }
//
//        imageRepository.delete(image);
//        s3BucketStorageService.deleteFile(image.getFullPath());

    }

    public void addImage(String materialId, AddImageRequest request) {
//        Material material = materialRepository.findById(materialId)
//                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material ID not exist"));
//        Query query = new Query();
//        query.addCriteria(Criteria.where("materials.$id").is(new ObjectId(materialId)));
//        Laboratory laboratories;
//        try {
//            laboratories = mongoTemplate.findOne(query, Laboratory.class);
//            log.info("get laboratories: {}", laboratories);
//        }catch (Exception ex){
//            throw new BusinessException("Can't get laboratories");
//        }
//        if(laboratories == null){
//            throw new BusinessException("laboratories is null can't get Image");
//        }
//        List<_Image> images = new ArrayList<>();
//        if (request.getImages() != null) {
//            for (CreateFileRequest file :
//                    request.getImages()) {
//                String fileName = String.format("%s_%s",UUID.randomUUID(), file.getOriginalFilename());
//                String path = String.format("%s/%s", laboratories.getLaboratoryName(), fileName);
//                String url = s3BucketStorageService.uploadFile(path, fileName, file);
//                images.add(_Image.builder()
//                        .imageName(fileName)
//                        .fullPath(path)
//                        .url(url)
//                        .build());
//            }
//        }
//        if (!images.isEmpty()) {
//            images = imageRepository.saveAll(images);
//        }
//        List<_Image> currentImage = material.getImages();
//        currentImage.addAll(images);
//        material.setImages(currentImage);
//        try {
//            materialRepository.save(material);
//        } catch (Exception ex) {
//            throw new BusinessException("Can't add Image to material in database: " + ex.getMessage());
//        }
    }

    @Override
    public PageableResponse<GetMaterialResponse> getMaterialByLabId(String labId) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(()-> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not exist"));
        List<Material> materials = laboratory.getMaterials();
        List<GetMaterialResponse> materialResponses = materials.stream().map(this::convertMaterialToGetMaterialResponse).collect(Collectors.toList());
        return new PageableResponse<>(materialResponses);
    }
}
