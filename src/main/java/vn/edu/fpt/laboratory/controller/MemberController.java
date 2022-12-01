package vn.edu.fpt.laboratory.controller;

import com.amazonaws.services.managedblockchain.model.UpdateMemberRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/{project-id}/member")
    ResponseEntity<GeneralResponse<AddMemberToProjectResponse>> addMemberToProject(@PathVariable(name = "project-id") String projectId, @RequestBody AddMemberToProjectRequest request);

    @PostMapping("/{lab-id}/member")
    ResponseEntity<GeneralResponse<AddMemberToLaboratoryResponse>> addMemberToLaboratory(@PathVariable(name = "lab-id") String labId, @RequestBody AddMemberToLaboratoryRequest request);

    @PutMapping("/{member-id}")
    ResponseEntity<GeneralResponse<Object>> updateMember(@PathVariable(name = "member-id") String memberId, @RequestBody UpdateMemberRequest request);

}
