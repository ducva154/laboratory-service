package vn.edu.fpt.laboratory.service;

import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.laboratory.*;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.response.laboratory.*;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:38
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface LaboratoryService {

    CreateLaboratoryResponse createLaboratory(CreateLaboratoryRequest request);

    void updateLaboratory(String labId, UpdateLaboratoryRequest request);

    void deleteLaboratory(String labId);

    GetLaboratoryDetailResponse getLaboratoryDetail(String labId);

    GetLaboratoryContainerResponse getLaboratory(GetLaboratoryRequest request);

    PageableResponse<GetMemberResponse> getMemberInLab(String labId);

    void removeMemberFromLaboratory(String labId, String memberId);

    ApplyLaboratoryResponse applyToLaboratory(String labId, ApplyLaboratoryRequest request);

    GetApplicationDetailResponse getApplicationByApplicationId(String applicationId);

    PageableResponse<GetApplicationResponse> getApplicationByLabId(String labId, String status);

    void reviewApplication(String labId, String applicationId, ReviewApplicationRequest request);
}
