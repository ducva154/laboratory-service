package vn.edu.fpt.laboratory.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 14/12/2022 - 17:18
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ModifyMemberInProjectEvent implements Serializable {

    private static final long serialVersionUID = 3919464127447944970L;
    private String projectId;

}
