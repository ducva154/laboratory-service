package vn.edu.fpt.laboratory.dto.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.laboratory.dto.cache.UserInfo;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 23:48
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"accountId", "userInfo"})
public class UserInfoResponse implements Serializable {

    private static final long serialVersionUID = 2360096410069646348L;
    private String accountId;
    private UserInfo userInfo;
}
