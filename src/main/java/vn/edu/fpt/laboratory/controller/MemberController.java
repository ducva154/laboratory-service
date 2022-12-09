package vn.edu.fpt.laboratory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToProjectRequest;
import vn.edu.fpt.laboratory.dto.request.member.UpdateMemberInfoRequest;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 21:48
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/public/api/v1/members")
public interface MemberController {

    @PostMapping("/{project-id}/member-to-project")
    ResponseEntity<GeneralResponse<Object>> addMemberToProject(@PathVariable(name = "project-id") String projectId, @RequestBody AddMemberToProjectRequest request);

    @PostMapping("/{lab-id}/member-to-lab")
    ResponseEntity<GeneralResponse<Object>> addMemberToLaboratory(@PathVariable(name = "lab-id") String labId, @RequestBody AddMemberToLaboratoryRequest request);

    @PutMapping("/{member-id}")
    ResponseEntity<GeneralResponse<Object>> updateMember(@PathVariable(name = "member-id") String memberId, @RequestBody UpdateMemberInfoRequest request);


}
