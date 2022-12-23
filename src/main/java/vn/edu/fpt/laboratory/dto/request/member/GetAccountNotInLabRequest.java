package vn.edu.fpt.laboratory.dto.request.member;

import lombok.*;
import vn.edu.fpt.laboratory.dto.common.PageableRequest;
import vn.edu.fpt.laboratory.dto.common.SortableRequest;

import java.util.List;

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
public class GetAccountNotInLabRequest extends PageableRequest {

    private static final long serialVersionUID = -6437585542255438403L;
    private String username;
    private List<String> accountIds;
    private Integer page;
    private Integer size;
    List<SortableRequest> sortBy;

}
