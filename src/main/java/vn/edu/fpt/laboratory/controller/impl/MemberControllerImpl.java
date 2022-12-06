package vn.edu.fpt.laboratory.controller.impl;

import com.amazonaws.services.managedblockchain.model.UpdateMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.controller.MemberController;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToProjectRequest;
import vn.edu.fpt.laboratory.dto.request.member.UpdateMemberInfoRequest;
import vn.edu.fpt.laboratory.factory.ResponseFactory;
import vn.edu.fpt.laboratory.service.LaboratoryService;
import vn.edu.fpt.laboratory.service.MemberInfoService;
import vn.edu.fpt.laboratory.service.ProjectService;

@RestController
@RequiredArgsConstructor
@Slf4j
/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:18
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/

public class MemberControllerImpl implements MemberController {
    private final ResponseFactory responseFactory;
    private final MemberInfoService memberInfoService;
    @Override
    public ResponseEntity<GeneralResponse<Object>> addMemberToProject(String projectId, AddMemberToProjectRequest request) {
        memberInfoService.addMemberToProject(projectId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> addMemberToLaboratory(String labId, AddMemberToLaboratoryRequest request) {
        memberInfoService.addMemberToLaboratory(labId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateMember(String memberId, UpdateMemberInfoRequest request) {
        memberInfoService.updateMember(memberId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> removeMemberFromProject(String projectId, String memberId) {
        memberInfoService.removeMemberFromProject(projectId, memberId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> removeMemberFromlabotory(String labId, String memberId) {
        memberInfoService.removeMemberFromlabotory(labId, memberId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }


}
