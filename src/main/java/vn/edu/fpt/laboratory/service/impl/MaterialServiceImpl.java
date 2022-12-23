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
import vn.edu.fpt.laboratory.dto.request.laboratory.GetOrderRequest;
import vn.edu.fpt.laboratory.dto.request.material.*;
import vn.edu.fpt.laboratory.dto.response.image.GetImageResponse;
import vn.edu.fpt.laboratory.dto.response.material.*;
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
        if (Objects.nonNull(createFileRequest)) {
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
            } catch (Exception ex) {
                throw new BusinessException("Can't save image to database: " + ex.getMessage());
            }
        }

        Material material = Material.builder()
                .materialName(request.getMaterialName())
                .description(request.getDescription())
                .amount(request.getAmount())
                .totalAmount(request.getAmount())
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

        if (!material.getMaterialName().equals(request.getMaterialName())){
            if (Objects.nonNull(request.getMaterialName())) {
                if (materials.stream().anyMatch(m -> m.getMaterialName().equals(request.getMaterialName()))) {
                    throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material name is already exist");
                } else {
                    material.setMaterialName(request.getMaterialName());
                }
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
        if (Objects.nonNull(request.getImage())) {
            String fileKey = UUID.randomUUID().toString();
            s3BucketStorageService.uploadFile(request.getImage(), fileKey);
            String[] splits = request.getImage().getName().split("\\.");
            String type = splits[splits.length - 1];
            _Image image = _Image.builder()
                    .imageName(request.getImage().getName())
                    .fileKey(fileKey)
                    .size(FileUtils.getFileSize(request.getImage().getSize()))
                    .type(type)
                    .length(request.getImage().getSize())
                    .mimeType(request.getImage().getMimeType())
                    .build();
            try {
                image = imageRepository.save(image);
            } catch (Exception ex) {
                throw new BusinessException("Can't save image to database: " + ex.getMessage());
            }
            material.setImages(image);
        }
        if (Objects.nonNull(request.getNote())) {
            material.setNote(request.getNote());
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
        material.setDelete(true);

        try {
            materialRepository.save(material);
        } catch (Exception ex) {
            throw new BusinessException("Can't delete material in database");
        }

        List<OrderHistory> orderHistories = orderHistoryRepository.findByMaterialIdAndStatus(materialId, OrderStatusEnum.WAITING_FOR_APPROVAL.getStatus());
        for (OrderHistory o : orderHistories) {
            o.setStatus(OrderStatusEnum.REJECTED.getStatus());
        }
        try {
            orderHistoryRepository.saveAll(orderHistories);
        } catch (Exception ex) {
            throw new BusinessException("Can't update order history in database");
        }
    }

    @Override
    public PageableResponse<GetMaterialResponse> getMaterial(GetMaterialRequest request) {
        Laboratory laboratory = laboratoryRepository.findById(request.getLaboratoryId().toString())
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Lab Id not exist"));
        List<ObjectId> materialIds = laboratory.getMaterials().stream().map(Material::getMaterialId).map(ObjectId::new).collect(Collectors.toList());

        Query query = new Query();

        query.addCriteria(Criteria.where("_id").in(materialIds));
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
                .image(s3BucketStorageService.getPublicURL(material.getImages().getFileKey()))
                .build();
    }

    @Override
    public GetMaterialDetailResponse getMaterialById(String materialId) {
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material ID not exist"));

        _Image image = material.getImages();
        List<BorrowTime> borrowTimes = material.getBorrowTime();
        borrowTimes.removeIf(v -> v.getToDate().isBefore(LocalDateTime.now()));
        material.setBorrowTime(borrowTimes);
        try {
            materialRepository.save(material);
        }catch (Exception ex){
            throw new BusinessException("Can't save material to database"+ ex.getMessage());
        }

        return GetMaterialDetailResponse.builder()
                .materialId(material.getMaterialId())
                .materialName(material.getMaterialName())
                .description(material.getDescription())
                .status(material.getStatus())
                .images(convertImageToGetImageResponse(image))
                .note(material.getNote())
                .amount(material.getAmount())
                .borrowTime(material.getBorrowTime())
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
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not exist"));
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material ID not exist"));

        if (request.getAmount() > material.getAmount()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Not enough material to order");
        }

        List<BorrowTime> borrowTimeList = material.getBorrowTime();
        if (!borrowTimeList.isEmpty()) {
            for (BorrowTime b : borrowTimeList) {
                if (b.getFromDate().isBefore(request.getOrderFrom()) && b.getToDate().isAfter(request.getOrderFrom()) ||
                        b.getFromDate().isBefore(request.getOrderTo()) && b.getToDate().isAfter(request.getOrderTo()) ||
                        b.getFromDate().isBefore(request.getOrderFrom()) && b.getToDate().isAfter(request.getOrderTo()) ||
                        b.getFromDate().isAfter(request.getOrderFrom()) && b.getToDate().isBefore(request.getOrderTo()) ||
                        b.getFromDate().isEqual(request.getOrderFrom()) && b.getToDate().isEqual(request.getOrderTo())) {
                    throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "The material is in used in order time");
                }
            }
        }
        OrderHistory orderHistory = OrderHistory.builder()
                .reason(request.getReason())
                .materialId(materialId)
                .amount(request.getAmount())
                .orderFrom(request.getOrderFrom())
                .orderTo(request.getOrderTo())
                .status(OrderStatusEnum.WAITING_FOR_APPROVAL.getStatus())
                .build();

        List<MemberInfo> managers = laboratory.getMembers().stream()
                .filter(v -> v.getRole().equals(LaboratoryRoleEnum.MANAGER.getRole()) || v.getRole().equals(LaboratoryRoleEnum.OWNER.getRole()))
                .collect(Collectors.toList());
        if (!managers.isEmpty()) {
            Optional<AppConfig> orderMaterialTemplateId = appConfigRepository.findByConfigKey("ORDER_MATERIAL_TEMPLATE_ID");
            if (orderMaterialTemplateId.isPresent()) {
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
            }else{
                log.info("Missing config key: ORDER_MATERIAL_TEMPLATE_ID");
            }
        }else{
            log.info("Laboratory don't has manager");
        }

        try {
            materialRepository.save(material);
        } catch (Exception ex) {
            throw new BusinessException("Can't save material to database: " + ex.getMessage());
        }

        try {
            orderHistory = orderHistoryRepository.save(orderHistory);
        } catch (Exception ex) {
            throw new BusinessException("Can't save order history to database: " + ex.getMessage());
        }
        List<OrderHistory> orders = laboratory.getOrders();
        orders.add(orderHistory);
        laboratory.setOrders(orders);
        try {
            laboratoryRepository.save(laboratory);
        } catch (Exception ex) {
            throw new BusinessException("Can't save laboratory to database: " + ex.getMessage());
        }
        return OrderMaterialResponse.builder()
                .orderId(orderHistory.getOrderId())
                .build();
    }

    @Override
    public void returnMaterial(String orderId) {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Order history not exist"));
        orderHistory.setActuallyReturn(LocalDateTime.now());
        orderHistory.setStatus(OrderStatusEnum.COMPLETED.getStatus());
        try {
            orderHistoryRepository.save(orderHistory);
        } catch (Exception ex) {
            throw new BusinessException("Can't save order history to database: " + ex.getMessage());
        }

        Material material = materialRepository.findById(orderHistory.getMaterialId())
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material Id not eixst"));
        material.setAmount(material.getAmount() + orderHistory.getAmount());
        material.setStatus(MaterialStatusEnum.FREE.getStatus());
        try {
            materialRepository.save(material);
        } catch (Exception ex) {
            throw new BusinessException("Can't save material to database: " + ex.getMessage());
        }
    }

    @Override
    public PageableResponse<GetOrderedResponse> getOrderByLabId(String laboratoryId, GetOrderRequest request) {
        Query query = new Query();
        if (Objects.nonNull(request.getOrderId())) {
            query.addCriteria(Criteria.where("_id").is(request.getOrderId()));
        }
        if (Objects.nonNull(request.getMaterialName())) {
            query.addCriteria(Criteria.where("material_name").regex(request.getMaterialName()));
        }
        if (Objects.nonNull(request.getStatus())) {
            query.addCriteria(Criteria.where("status").is(request.getStatus()));
        }

        Laboratory laboratory = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not exist"));
        List<ObjectId> orderIds = laboratory.getOrders().stream().map(OrderHistory::getOrderId).map(ObjectId::new).collect(Collectors.toList());
        query.addCriteria(Criteria.where("_id").in(orderIds));

        BaseMongoRepository.addCriteriaWithAuditable(query, request);
        Long totalElements = mongoTemplate.count(query, OrderHistory.class);
        BaseMongoRepository.addCriteriaWithPageable(query, request);
        BaseMongoRepository.addCriteriaWithSorted(query, request);
        List<OrderHistory> orders = mongoTemplate.find(query, OrderHistory.class);

        List<GetOrderedResponse> orderedMaterialResponses = orders.stream().map(this::convertOrderHistoryToGetOrderedResponse).collect(Collectors.toList());
        return new PageableResponse<> (request, totalElements, orderedMaterialResponses);
    }

    private GetOrderedResponse convertOrderHistoryToGetOrderedResponse(OrderHistory orderHistory) {
        if (orderHistory == null){
            return null;
        } else {
            Material material = materialRepository.findById(orderHistory.getMaterialId())
                    .orElseThrow(()->new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material ID in order history not exist"));
            UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                    .accountId(orderHistory.getCreatedBy())
                    .userInfo(userInfoService.getUserInfo(orderHistory.getCreatedBy()))
                    .build();
            return GetOrderedResponse.builder()
                    .orderId(orderHistory.getOrderId())
                    .materialName(material.getMaterialName())
                    .borrowBy(userInfoResponse)
                    .amount(orderHistory.getAmount())
                    .reason(orderHistory.getReason())
                    .status(orderHistory.getStatus())
                    .orderFromDate(orderHistory.getOrderFrom())
                    .orderToDate(orderHistory.getOrderTo())
                    .build();
        }
    }

    @Override
    public PageableResponse<GetOrderedMaterialResponse> getOrderedMaterialInLabByAccountId(String laboratoryId, String accountId) {
        Laboratory laboratory = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(()-> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not exist"));
        List<OrderHistory> orderHistories = laboratory.getOrders().stream().filter(m->m.getCreatedBy().equals(accountId)).collect(Collectors.toList());
        List<GetOrderedMaterialResponse> orderedMaterialResponses = orderHistories.stream().map(this::convertOrderHistoryToGetOrderedMaterialResponse).collect(Collectors.toList());
        return new PageableResponse<> (orderedMaterialResponses);
    }

    private GetOrderedMaterialResponse convertOrderHistoryToGetOrderedMaterialResponse(OrderHistory orderHistory){
        Material material = materialRepository.findById(orderHistory.getMaterialId())
                .orElseThrow(()-> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material ID not exist"));
        GetImageResponse imageResponse = GetImageResponse.builder()
                .imageId(material.getImages().getImageId())
                .imageName(material.getImages().getImageName())
                .url(s3BucketStorageService.getPublicURL(material.getImages().getFileKey()))
                .build();
        return GetOrderedMaterialResponse.builder()
                .orderId(orderHistory.getOrderId())
                .materialId(orderHistory.getMaterialId())
                .materialName(material.getMaterialName())
                .images(imageResponse)
                .status(orderHistory.getStatus())
                .orderFromDate(orderHistory.getOrderFrom())
                .orderToDate(orderHistory.getOrderTo())
                .build();
    }

    @Override
    public void responseOrder(String orderId, ResponseOrderRequest request) {
        OrderHistory orderHistory = orderHistoryRepository.findById(orderId)
                .orElseThrow(()->new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Order ID not exist"));
        Material material = materialRepository.findById(orderHistory.getMaterialId())
                .orElseThrow(()->new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material ID not exist"));
        if (Objects.nonNull(request.getStatus())) {
            if (request.getStatus().equals(OrderStatusEnum.APPROVED.getStatus())) {
                orderHistory.setStatus(OrderStatusEnum.APPROVED.getStatus());
                BorrowTime borrowTime = BorrowTime.builder()
                        .fromDate(orderHistory.getOrderFrom())
                        .toDate(orderHistory.getOrderTo())
                        .build();
                List<BorrowTime> borrowTimeList = material.getBorrowTime();
                borrowTimeList.add(borrowTime);
                if(material.getAmount() < orderHistory.getAmount()){
                    orderHistory.setStatus(OrderStatusEnum.REJECTED.getStatus());
                    try {
                        orderHistoryRepository.save(orderHistory);
                    }catch (Exception ex){
                        throw new BusinessException("Can't update order history: "+ ex.getMessage());
                    }
                    throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Not enough material for this ticket");
                }
                if(material.getAmount() - orderHistory.getAmount() == 0){
                    material.setStatus(MaterialStatusEnum.IN_USED.getStatus());
                }
                material.setAmount(material.getAmount() - orderHistory.getAmount());
                material.setBorrowTime(borrowTimeList);
            } else {
                orderHistory.setStatus(OrderStatusEnum.REJECTED.getStatus());
            }
        }
        try {
            orderHistoryRepository.save(orderHistory);
        } catch (Exception ex) {
            throw new BusinessException("Can't save order history to database: " + ex.getMessage());
        }
        try {
            materialRepository.save(material);
        } catch (Exception ex) {
            throw new BusinessException("Can't save material to database: " + ex.getMessage());
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
}
