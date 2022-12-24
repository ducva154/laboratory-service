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
 * @created : 23/12/2022 - 21:10
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetMemberNotInLabResponse implements Serializable {

    private static final long serialVersionUID = 4825908444718727441L;
    private String accountId;
    private String username;
    private String fullName;
    private String email;
}
