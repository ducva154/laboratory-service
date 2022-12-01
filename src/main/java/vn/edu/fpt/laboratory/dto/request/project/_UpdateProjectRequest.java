package vn.edu.fpt.laboratory.dto.request.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 00:02
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@Data
@Builder
public class _UpdateProjectRequest implements Serializable {

    private static final long serialVersionUID = -2000589070638322332L;
    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate toDate;
}
