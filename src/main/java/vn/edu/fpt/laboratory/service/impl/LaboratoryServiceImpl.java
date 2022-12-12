package vn.edu.fpt.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.laboratory.config.kafka.producer.SendEmailProducer;
import vn.edu.fpt.laboratory.constant.ApplicationStatusEnum;
import vn.edu.fpt.laboratory.constant.LaboratoryRoleEnum;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.dto.cache.UserInfo;
import vn.edu.fpt.laboratory.dto.common.MemberInfoResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.common.UserInfoResponse;
import vn.edu.fpt.laboratory.dto.event.SendEmailEvent;
import vn.edu.fpt.laboratory.dto.request.laboratory.*;
import vn.edu.fpt.laboratory.dto.response.laboratory.*;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectResponse;
import vn.edu.fpt.laboratory.entity.*;
import vn.edu.fpt.laboratory.exception.BusinessException;
import vn.edu.fpt.laboratory.repository.*;
import vn.edu.fpt.laboratory.service.LaboratoryService;
import vn.edu.fpt.laboratory.service.ProjectService;
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
    private final ProjectService projectService;
    private final MongoTemplate mongoTemplate;
    private final ApplicationRepository applicationRepository;
    private final AppConfigRepository appConfigRepository;
    private final SendEmailProducer sendEmailProducer;

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
    public void updateLaboratory(String labId, UpdateLaboratoryRequest request) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory id not found"));

        if (Objects.nonNull(request.getLaboratoryName())) {
            if (laboratoryRepository.findByLaboratoryName(request.getLaboratoryName()).isPresent()) {
                throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory name already in database");
            }
            laboratory.setLaboratoryName(request.getLaboratoryName());
        }
        if (Objects.nonNull(request.getDescription())) {
            laboratory.setDescription(request.getDescription());
        }
        if (Objects.nonNull(request.getMajor())) {
            laboratory.setDescription(request.getMajor());
        }
        if (Objects.nonNull(request.getOwnerBy()) && ObjectId.isValid(request.getOwnerBy())) {
            log.info("Update Laboratory name: {}", request.getLaboratoryName());
            MemberInfo memberInfo = laboratory.getMembers().stream().filter((v) -> v.getMemberId().equals(request.getOwnerBy())).findAny().orElseThrow();
            laboratory.setOwnerBy(memberInfo);
        } else {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Invalid ownerBy");
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
    public PageableResponse<GetMemberResponse> getMemberInLab(String labId, GetMemberInLaboratoryRequest request) {
        Query query = new Query();
        if (Objects.nonNull(request.getMemberId())) {
            query.addCriteria(Criteria.where("_id").is(request.getMemberId()));
        }
        if (Objects.nonNull(request.getRole())) {
            query.addCriteria(Criteria.where("role").is(request.getRole()));
        }

        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Lab ID not exist"));
        List<MemberInfo> memberInfos = laboratory.getMembers();
        List<ObjectId> memberInfoIds = memberInfos.stream().map(MemberInfo::getMemberId).map(ObjectId::new).collect(Collectors.toList());
        query.addCriteria(Criteria.where("_id").in(memberInfoIds));

        BaseMongoRepository.addCriteriaWithAuditable(query, request);
        Long totalElements = mongoTemplate.count(query, MemberInfo.class);
        BaseMongoRepository.addCriteriaWithPageable(query, request);
        BaseMongoRepository.addCriteriaWithSorted(query, request);
        List<MemberInfo> memberInfoList = mongoTemplate.find(query, MemberInfo.class);

        List<GetMemberResponse> getMemberResponses = memberInfoList.stream().map(this::convertMemberToGetMemberResponse).collect(Collectors.toList());
        return new PageableResponse<>(request, totalElements, getMemberResponses);
    }

    @Override
    public void removeMemberFromLaboratory(String labId, String memberId) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Role id not found"));

        List<Project> projects = laboratory.getProjects();
//        projects.stream().map(Project::getProjectId).forEach((projectId) -> projectService.removeMemberFromProject(projectId, memberId));
        for (Project p : projects ) {
            List<MemberInfo> memberInfos = p.getMembers();
            for (MemberInfo m : memberInfos ) {
                if (m.getMemberId().equals(memberId)) {
                    projectService.removeMemberFromProject(p.getProjectId(), memberId);
                }
            }
        }
        List<MemberInfo> memberInfos = laboratory.getMembers();
        Optional<MemberInfo> memberInLabo = memberInfos.stream().filter(v -> v.getMemberId().equals(memberId)).findAny();

        if (memberInLabo.isEmpty()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Member id not found");
        }
        if (memberInfos.remove(memberInLabo.get())) {
            laboratory.setMembers(memberInfos);
            try {
                laboratoryRepository.save(laboratory);
                log.info("Remove member from lab success");
            } catch (Exception ex) {
                throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't update lab in database after remove member");
            }
        } else {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't remove member from lab");
        }
        try {
            memberInfoRepository.deleteById(memberId);
            log.info("Remove member in database success");
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't remove member in database");
        }

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

        PageableResponse<GetLaboratoryResponse> joinedLaboratories;
        try {
            joinedLaboratories = getLaboratoryInDatabase(request);
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw new BusinessException("INDSF");
        }
        request.setIsContain(false);
        request.setSize(request.getSuggestionSize());
        request.setPage(request.getSuggestionPage());

        PageableResponse<GetLaboratoryResponse> suggestionLaboratories ;
        try {
            suggestionLaboratories = getLaboratoryInDatabase(request);
        }catch (Exception ex){
            throw new BusinessException("SDF");
        }

        return GetLaboratoryContainerResponse.builder()
                .joinedLaboratories(joinedLaboratories)
                .suggestLaboratories(suggestionLaboratories)
                .build();
    }

    private PageableResponse<GetLaboratoryResponse> getLaboratoryInDatabase(GetLaboratoryRequest request) {
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
            if (request.getIsContain()) {
                query.addCriteria(Criteria.where("members.$id").in(memberId));
            } else {
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
        try {
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
        } catch (Exception ex) {
            log.error("ERROR: " + ex.getMessage());
            return null;
        }

    }

    @Override
    public ApplyLaboratoryResponse applyToLaboratory(String labId, ApplyLaboratoryRequest request) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "laboratory id not found"));

        List<MemberInfo> memberInfos = laboratory.getMembers();
        if (memberInfos.stream().anyMatch(v -> v.getAccountId().equals(request.getAccountId()))) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Account ID already exist in lab");
        }
        Application application = Application.builder()
                .accountId(request.getAccountId())
                .reason(request.getReason())
                .cvKey(request.getCvKey())
                .status(ApplicationStatusEnum.WAITING_FOR_APPROVE)
                .build();
        List<Application> applications = laboratory.getApplications();
        try {
            application = applicationRepository.save(application);
            log.info("Apply CV to Lab success");
        } catch (Exception ex) {
            throw new BusinessException("Can't apply CV in database: " + ex.getMessage());
        }
        applications.add(application);
        laboratory.setApplications(applications);
        try {
            laboratoryRepository.save(laboratory);
            log.info("Add application to laboratory success");
        } catch (Exception ex) {
            throw new BusinessException("Can't add application to laboratory in database: " + ex.getMessage());
        }
        Optional<AppConfig> appConfigOptional = appConfigRepository.findByConfigKey("NOTIFY_MANAGER_TEMPLATE_ID");
        if (appConfigOptional.isPresent()) {
            AppConfig appConfig = appConfigOptional.get();
            List<MemberInfo> managerMemberInfo = laboratory.getMembers().stream()
                    .filter(v -> LaboratoryRoleEnum.OWNER.getRole().equals(v.getRole()) || LaboratoryRoleEnum.MANAGER.getRole().equals(v.getRole()))
                    .collect(Collectors.toList());
            List<String> emails = managerMemberInfo.stream().map(this::getEmailOfMemberInfo).collect(Collectors.toList());
            List<String> correctEmail = emails.stream().filter(Objects::nonNull).collect(Collectors.toList());
            for (String email : correctEmail) {
                SendEmailEvent event = SendEmailEvent.builder()
                        .templateId(appConfig.getConfigValue())
                        .sendTo(email)
                        .cc(null)
                        .bcc(null)
                        .build();
                sendEmailProducer.sendMessage(event);
            }
        }
        return ApplyLaboratoryResponse.builder()
                .applicationId(application.getApplicationId())
                .build();
    }

    private String getEmailOfMemberInfo(MemberInfo memberInfo) {
        UserInfo userInfo = userInfoService.getUserInfo(memberInfo.getAccountId());
        if (userInfo != null) {
            return userInfo.getEmail();
        } else {
            return null;
        }
    }

    @Override
    public GetApplicationDetailResponse getApplicationByApplicationId(String applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Application id is not exist"));
        return GetApplicationDetailResponse.builder()
                .applicationId(application.getApplicationId())
                .status(application.getStatus())
                .accountId(application.getAccountId())
                .cvKey(application.getCvKey())
                .reason(application.getReason())
                .comment(application.getComment())
                .createdBy(UserInfoResponse.builder()
                        .accountId(application.getCreatedBy())
                        .userInfo(userInfoService.getUserInfo(application.getCreatedBy()))
                        .build())
                .createdDate(application.getCreatedDate())
                .lastModifiedBy(UserInfoResponse.builder()
                        .accountId(application.getLastModifiedBy())
                        .userInfo(userInfoService.getUserInfo(application.getLastModifiedBy()))
                        .build())
                .lastModifiedDate(application.getLastModifiedDate())
                .build();
    }

    @Override
    public PageableResponse<GetApplicationResponse> getApplicationByLabId(String labId, GetApplicationRequest request) {
        Query query = new Query();
        if (Objects.nonNull(request.getApplicationId())) {
            query.addCriteria(Criteria.where("_id").is(request.getApplicationId()));
        }
        if (Objects.nonNull(request.getStatus())) {
            query.addCriteria(Criteria.where("cv_name").is(request.getStatus()));
        }
        Laboratory laboratory = laboratoryRepository.findById(labId).orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Lab id is not exist"));
        List<ObjectId> applicationIds = laboratory.getApplications().stream().map(Application::getApplicationId).map(ObjectId::new).collect(Collectors.toList());
        query.addCriteria(Criteria.where("_id").in(applicationIds));

        BaseMongoRepository.addCriteriaWithAuditable(query, request);
        Long totalElements = mongoTemplate.count(query, Application.class);
        BaseMongoRepository.addCriteriaWithPageable(query, request);
        BaseMongoRepository.addCriteriaWithSorted(query, request);
        List<Application> applications = mongoTemplate.find(query, Application.class);

        List<GetApplicationResponse> getApplicationResponses = applications.stream().map(this::convertApplicationToGetApplicationResponse).collect(Collectors.toList());
        return new PageableResponse<>(request, totalElements, getApplicationResponses);
    }

    @Override
    public void reviewApplication(String labId, String applicationId, ReviewApplicationRequest request) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Lab Id not exist"));
        Application application = laboratory.getApplications().stream().filter(v -> v.getApplicationId().equals(applicationId)).findFirst().orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Application Id not exist"));

        try {
            ApplicationStatusEnum statusEnum = ApplicationStatusEnum.valueOf(request.getStatus());
            application.setComment(request.getComment());
            application.setStatus(statusEnum);
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Status invalid");
        }
        try {
            applicationRepository.save(application);
        } catch (Exception ex) {
            throw new BusinessException("Can't update application in database");
        }
        Optional<AppConfig> appConfigOptional = appConfigRepository.findByConfigKey("NOTIFY_MEMBER_AFTER_REVIEW_APPLICATION");
        if (appConfigOptional.isPresent()) {
            AppConfig appConfig = appConfigOptional.get();
            UserInfo userInfo = userInfoService.getUserInfo(application.getAccountId());
            if (Objects.nonNull(userInfo) && Objects.nonNull(userInfo.getEmail())) {
                SendEmailEvent sendEmailEvent = SendEmailEvent.builder()
                        .templateId(appConfig.getConfigValue())
                        .sendTo(userInfo.getEmail())
                        .bcc(null)
                        .cc(null)
                        .build();
                sendEmailProducer.sendMessage(sendEmailEvent);
            }
        }
        MemberInfo memberInfo = MemberInfo.builder()
                .accountId(application.getAccountId())
                .role(LaboratoryRoleEnum.MEMBER.getRole())
                .build();
        try {
            memberInfo = memberInfoRepository.save(memberInfo);
        } catch (Exception ex) {
            throw new BusinessException("Can't create member info in database");
        }
        List<MemberInfo> memberInfos = laboratory.getMembers();
        memberInfos.add(memberInfo);
        laboratory.setMembers(memberInfos);
        try {
            laboratoryRepository.save(laboratory);
        } catch (Exception ex) {
            throw new BusinessException("Can't save laboratory after add member in database");
        }
    }

    private GetApplicationResponse convertApplicationToGetApplicationResponse(Application application) {
        return GetApplicationResponse.builder()
                .applicationId(application.getApplicationId())
                .status(application.getStatus().getStatusName())
                .createdBy(UserInfoResponse.builder()
                        .accountId(application.getCreatedBy())
                        .userInfo(userInfoService.getUserInfo(application.getCreatedBy()))
                        .build())
                .createdDate(application.getCreatedDate())
                .lastModifiedBy(UserInfoResponse.builder()
                        .accountId(application.getLastModifiedBy())
                        .userInfo(userInfoService.getUserInfo(application.getLastModifiedBy()))
                        .build())
                .lastModifiedDate(application.getLastModifiedDate())
                .build();
    }
}
