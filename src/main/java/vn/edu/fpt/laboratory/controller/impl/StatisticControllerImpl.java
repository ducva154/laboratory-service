package vn.edu.fpt.laboratory.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.laboratory.controller.StatisticController;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.response.dashboard.GetStatisticsResponse;
import vn.edu.fpt.laboratory.factory.ResponseFactory;
import vn.edu.fpt.laboratory.repository.StatisticRepository;
import vn.edu.fpt.laboratory.service.StatisticService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatisticControllerImpl implements StatisticController {

    private final ResponseFactory responseFactory;
    private final StatisticService statisticService;

    @Override
    public ResponseEntity<GeneralResponse<GetStatisticsResponse>> getLabStatistic() {

        return responseFactory.response(statisticService.getStatistic());
    }
}
