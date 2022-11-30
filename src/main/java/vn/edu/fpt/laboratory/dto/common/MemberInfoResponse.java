package vn.edu.fpt.laboratory.dto.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.laboratory.entity.common.UserInfo;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 08:09
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"memberId", "accountId", "role", "userInfo"})
public class MemberInfoResponse implements Serializable {

    private static final long serialVersionUID = -6809375447228144572L;
    private String memberId;
    private String accountId;
    private String role;
    private UserInfo userInfo;
}
