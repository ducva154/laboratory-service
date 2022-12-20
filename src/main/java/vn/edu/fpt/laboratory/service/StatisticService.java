package vn.edu.fpt.laboratory.service;

import vn.edu.fpt.laboratory.dto.response.dashboard.GetStatisticsResponse;
import vn.edu.fpt.laboratory.repository.LaboratoryRepository;

import java.time.LocalDate;

public interface StatisticService {
    GetStatisticsResponse getStatistic();
}
