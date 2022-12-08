package vn.edu.fpt.laboratory.service;

import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToProjectRequest;
import vn.edu.fpt.laboratory.dto.request.member.UpdateMemberInfoRequest;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:38
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface MemberInfoService {

    void addMemberToProject(String projectId, AddMemberToProjectRequest request);

    void addMemberToLaboratory(String labId, @RequestBody AddMemberToLaboratoryRequest request);

    void updateMember(String memberId, UpdateMemberInfoRequest request);

    void removeMemberFromProject(String projectId, String memberId);

    void removeMemberFromLaboratory(String labId, String memberId);

}
