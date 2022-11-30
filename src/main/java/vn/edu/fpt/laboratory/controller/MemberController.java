package vn.edu.fpt.laboratory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToProjectRequest;
import vn.edu.fpt.laboratory.dto.response.member.AddMemberToLaboratoryResponse;
import vn.edu.fpt.laboratory.dto.response.member.AddMemberToProjectResponse;
import vn.edu.fpt.laboratory.dto.response.member.RemoveMemberFromLaboratoryResponse;
import vn.edu.fpt.laboratory.dto.response.member.RemoveMemberFromProjectResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 21:48
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/public/api/v1/members")
public interface MemberController {

    ResponseEntity<GeneralResponse<AddMemberToProjectResponse>> addMemberToProject(@PathVariable(name = "project-id") String projectId, @RequestBody AddMemberToProjectRequest request);

    ResponseEntity<GeneralResponse<AddMemberToLaboratoryResponse>> addMemberToLaboratory(@PathVariable(name = "lab-id") String labId, @RequestBody AddMemberToLaboratoryRequest request);

    ResponseEntity<GeneralResponse<Object>> updateMember(@PathVariable(name = "member-id") String memberId);

    ResponseEntity<GeneralResponse<RemoveMemberFromProjectResponse>> removeMemberFromProject(@PathVariable(name = "project-id") String projectId, @PathVariable(name = "member-id") String memberId);

    ResponseEntity<GeneralResponse<RemoveMemberFromLaboratoryResponse>> removeMemberFromLaboratory(@PathVariable(name = "lab-id") String labId, @PathVariable(name = "member-id") String memberId);

}
