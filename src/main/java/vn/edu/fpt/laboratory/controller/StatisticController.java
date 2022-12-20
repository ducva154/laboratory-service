package vn.edu.fpt.laboratory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.response.dashboard.GetStatisticsResponse;
@RequestMapping("${app.application-context}/public/api/v1/statistics")
public interface StatisticController {

    @GetMapping
    ResponseEntity<GeneralResponse<GetStatisticsResponse>> getLabStatistic();
}
