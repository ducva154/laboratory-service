package vn.edu.fpt.laboratory.dto.response.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 24/12/2022 - 02:48
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetMemberNotInProjectResponse implements Serializable {

    private static final long serialVersionUID = 1078570577988608371L;
    private String memberId;
    private String username;
    private String fullName;
    private String email;
}
