package vn.edu.fpt.laboratory.dto.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetStatisticsResponse implements Serializable {

    private static final long serialVersionUID = 2338342277772713621L;

    private Integer numOfLab;
    private Integer avgProjectInLab;
    private Integer avgMemberInLab;

    private Integer numOfProject;
    private Integer avgMemberInProject;
    private Long avgTimeOfProject;

    private Integer numOfMaterial;
    private Integer numOfItemBorrowed;
    private Integer numOfMemberBorrowed;
}
