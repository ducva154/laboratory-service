package vn.edu.fpt.laboratory.service;

import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.laboratory.CreateLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.laboratory.GetLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.laboratory.UpdateLaboratoryRequest;
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
}
