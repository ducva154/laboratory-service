package vn.edu.fpt.laboratory.dto.request.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * vn.edu.fpt.laboratory.dto.request.member
 *
 * @author : Portgas.D.Ace
 * @created : 02/12/2022
 * @contact : 0339850697- congdung2510@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateMemberInfoRequest implements Serializable {

    private static final long serialVersionUID = -7756317149874311441L;
    private String role;
}
