package vn.edu.fpt.laboratory.dto.request.project;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.laboratory.config.datetime.CustomDateDeserializer;
import vn.edu.fpt.laboratory.config.datetime.CustomDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 00:01
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class _CreateProjectRequest implements Serializable {

    private static final long serialVersionUID = 2355846876115219324L;
    private String projectName;
    private String description;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate startDate;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate toDate;
}
