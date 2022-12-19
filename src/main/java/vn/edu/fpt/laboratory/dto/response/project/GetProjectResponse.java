package vn.edu.fpt.laboratory.dto.response.project;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 00:02
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"projectId", "projectName", "description", "members"})
public class GetProjectResponse implements Serializable {
    private static final long serialVersionUID = 5136630163476760601L;
    private String projectId;
    private String projectName;
    private String description;
    private Integer members;
}
