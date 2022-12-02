package vn.edu.fpt.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.laboratory.constant.LaboratoryRoleEnum;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.dto.common.MemberInfoResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.common.UserInfoResponse;
import vn.edu.fpt.laboratory.dto.request.laboratory.CreateLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.laboratory.GetLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.laboratory.UpdateLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.response.laboratory.*;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectResponse;
import vn.edu.fpt.laboratory.entity.Laboratory;
import vn.edu.fpt.laboratory.entity.MemberInfo;
import vn.edu.fpt.laboratory.entity.Project;
import vn.edu.fpt.laboratory.exception.BusinessException;
import vn.edu.fpt.laboratory.repository.BaseMongoRepository;
import vn.edu.fpt.laboratory.repository.LaboratoryRepository;
import vn.edu.fpt.laboratory.repository.MemberInfoRepository;
import vn.edu.fpt.laboratory.service.LaboratoryService;
import vn.edu.fpt.laboratory.service.UserInfoService;
import vn.edu.fpt.laboratory.utils.AuditorUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:43
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class LaboratoryServiceImpl implements LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final UserInfoService userInfoService;
    private final MongoTemplate mongoTemplate;

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public CreateLaboratoryResponse createLaboratory(CreateLaboratoryRequest request) {
        AuditorUtils auditorUtils = new AuditorUtils();
        String accountId = auditorUtils.getAccountId();

        Optional<Laboratory> laboratoryInDb = laboratoryRepository.findByLaboratoryName(request.getLabName());

        if (laboratoryInDb.isPresent()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory name already exist");
        }
        MemberInfo memberInfo = MemberInfo.builder()
                .accountId(accountId)
                .role(LaboratoryRoleEnum.OWNER.getRole())
                .build();
        try {
            memberInfo = memberInfoRepository.save(memberInfo);
            log.info("Create member info success");
        } catch (Exception ex) {
            throw new BusinessException("Can't create member info in database: " + ex.getMessage());
        }

        Laboratory laboratory = Laboratory.builder()
                .laboratoryName(request.getLabName())
                .description(request.getDescription())
                .ownerBy(memberInfo)
                .members(List.of(memberInfo))
                .major(request.getMajor())
                .build();
        try {
            laboratory = laboratoryRepository.save(laboratory);
            log.info("Create laboratory success");
        } catch (Exception ex) {
            throw new BusinessException("Can't create laboratory in database: " + ex.getMessage());
        }
        return CreateLaboratoryResponse.builder()
                .labId(laboratory.getLaboratoryId())
                .build();
    }

    @Override
    public void
    updateLaboratory(String labId, UpdateLaboratoryRequest request) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory id not found"));

        if (Objects.nonNull(request.getLaboratoryName())) {
            if (laboratoryRepository.findByLaboratoryName(request.getLaboratoryName()).isPresent()) {
                throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory name already in database");
            }
            log.info("Update file name: {}", request.getLaboratoryName());
            laboratory.setLaboratoryName(request.getLaboratoryName());
        }
        try {
            laboratoryRepository.save(laboratory);
            log.info("Update Laboratory success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save Laboratory in database when update: " + ex.getMessage());
        }
    }

    @Override
    public void deleteLaboratory(String labId) {
        laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not found"));
        try {
            laboratoryRepository.deleteById(labId);
            log.info("Delete Laboratory: {} success", labId);
        } catch (Exception ex) {
            throw new BusinessException("Can't delete Laboratory by ID: " + ex.getMessage());
        }
    }

    @Override
    public GetLaboratoryDetailResponse getLaboratoryDetail(String labId) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not exist"));

        MemberInfo ownerBy = laboratory.getOwnerBy();
        return GetLaboratoryDetailResponse.builder()
                .laboratoryId(laboratory.getLaboratoryId())
                .laboratoryName(laboratory.getLaboratoryName())
                .major(laboratory.getMajor())
                .members(laboratory.getMembers().size())
                .description(laboratory.getDescription())
                .projects(laboratory.getProjects().stream()
                        .map(this::convertProjectToGetProjectResponse)
                        .collect(Collectors.toList()))
                .ownerBy(MemberInfoResponse.builder()
                        .memberId(ownerBy.getMemberId())
                        .accountId(ownerBy.getAccountId())
                        .role(ownerBy.getRole())
                        .userInfo(userInfoService.getUserInfo(ownerBy.getAccountId()))
                        .build())
                .createdBy(UserInfoResponse.builder()
                        .accountId(laboratory.getCreatedBy())
                        .userInfo(userInfoService.getUserInfo(laboratory.getCreatedBy()))
                        .build())
                .createdDate(laboratory.getCreatedDate())
                .lastModifiedBy(UserInfoResponse.builder()
                        .accountId(laboratory.getLastModifiedBy())
                        .userInfo(userInfoService.getUserInfo(laboratory.getLastModifiedBy()))
                        .build())
                .lastModifiedDate(laboratory.getLastModifiedDate())
                .build();
    }

    private GetProjectResponse convertProjectToGetProjectResponse(Project project) {
        return GetProjectResponse.builder()
                .projectId(project.getProjectId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .members(project.getMembers().size())
                .build();
    }

    @Override
    public PageableResponse<GetMemberResponse> getMemberInLab(String labId) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Lab ID not exist"));
        List<MemberInfo> memberInfos = laboratory.getMembers();
        List<GetMemberResponse> getMemberResponses = memberInfos.stream().map(this::convertMemberToGetMemberResponse).collect(Collectors.toList());
        return new PageableResponse<>( getMemberResponses);
    }

    private GetMemberResponse convertMemberToGetMemberResponse(MemberInfo memberInfo) {
        return GetMemberResponse.builder()
                .memberId(memberInfo.getMemberId())
                .role(memberInfo.getRole())
                .userInfo(UserInfoResponse.builder()
                        .accountId(memberInfo.getAccountId())
                        .userInfo(userInfoService.getUserInfo(memberInfo.getAccountId()))
                        .build())
                .build();
    }

    @Override
    public GetLaboratoryContainerResponse getLaboratory(GetLaboratoryRequest request) {
        request.setIsContain(true);
        PageableResponse<GetLaboratoryResponse> joinedLaboratories = getLaboratoryInDatabase(request);

        request.setIsContain(false);
        request.setSize(request.getSuggestionSize());
        request.setPage(request.getSuggestionPage());

        PageableResponse<GetLaboratoryResponse> suggestionLaboratories = getLaboratoryInDatabase(request);

        return GetLaboratoryContainerResponse.builder()
                .joinedLaboratories(joinedLaboratories)
                .suggestLaboratories(suggestionLaboratories)
                .build();
    }

    private PageableResponse<GetLaboratoryResponse> getLaboratoryInDatabase(GetLaboratoryRequest request){
        Query query = new Query();

        if (Objects.nonNull(request.getLaboratoryId())) {
            query.addCriteria(Criteria.where("_id").is(request.getLaboratoryId()));
        }
        if (Objects.nonNull(request.getLaboratoryName())) {
            query.addCriteria(Criteria.where("laboratory_name").regex(request.getLaboratoryName()));
        }

        if (Objects.nonNull(request.getMajor())) {
            query.addCriteria(Criteria.where("major").regex(request.getMajor()));
        }
        if (Objects.nonNull(request.getDescription())) {
            query.addCriteria(Criteria.where("description").regex(request.getDescription()));
        }

        if (Objects.nonNull(request.getAccountId())) {
            if (!ObjectId.isValid(request.getAccountId())) {
                throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "User ID invalid");
            }
            List<MemberInfo> memberInfos = memberInfoRepository.findAllByAccountId(request.getAccountId());
            List<ObjectId> memberId = memberInfos.stream().map(MemberInfo::getMemberId).map(ObjectId::new).collect(Collectors.toList());
            if(request.getIsContain()){
                query.addCriteria(Criteria.where("members.$id").in(memberId));
            }else{
                query.addCriteria(Criteria.where("members.$id").nin(memberId));
            }
        }

        Long totalElement = mongoTemplate.count(query, Laboratory.class);

        BaseMongoRepository.addCriteriaWithPageable(query, request);

        List<Laboratory> laboratories = mongoTemplate.find(query, Laboratory.class);

        List<GetLaboratoryResponse> getLaboratoryDetailResponses = laboratories.stream().map(this::convertLaboratoryToGetLaboratoryResponse).collect(Collectors.toList());

        return new PageableResponse<>(request, totalElement, getLaboratoryDetailResponses);
    }

    private GetLaboratoryResponse convertLaboratoryToGetLaboratoryResponse(Laboratory laboratory) {
        MemberInfo ownerBy = laboratory.getOwnerBy();
        return GetLaboratoryResponse.builder()
                .laboratoryId(laboratory.getLaboratoryId())
                .laboratoryName(laboratory.getLaboratoryName())
                .description(laboratory.getDescription())
                .major(laboratory.getMajor())
                .projects(laboratory.getProjects().size())
                .members(laboratory.getMembers().size())
                .ownerBy(MemberInfoResponse.builder()
                        .memberId(ownerBy.getMemberId())
                        .accountId(ownerBy.getAccountId())
                        .role(ownerBy.getRole())
                        .userInfo(userInfoService.getUserInfo(ownerBy.getAccountId()))
                        .build())
                .build();
    }

}
