package vn.edu.fpt.laboratory.service;

import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.project._CreateProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._GetProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._UpdateProjectRequest;
import vn.edu.fpt.laboratory.dto.response.laboratory.GetMemberResponse;
import vn.edu.fpt.laboratory.dto.response.project.CreateProjectResponse;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectDetailResponse;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:38
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface ProjectService {

    CreateProjectResponse createProject(String labId, _CreateProjectRequest request);

    void updateProject(String labId, String projectId, _UpdateProjectRequest request);

    void deleteProject(String labId, String projectId);

    PageableResponse<GetProjectResponse> getProjectByCondition(_GetProjectRequest request);

    PageableResponse<GetProjectResponse> getProjectByLaboratoryId(String labId);

    PageableResponse<GetMemberResponse> getMemberInProject(String projectId);

    GetProjectDetailResponse getProjectDetailByProjectId(String projectId);

    void removeMemberFromProject(String projectId, String memberId);
}
