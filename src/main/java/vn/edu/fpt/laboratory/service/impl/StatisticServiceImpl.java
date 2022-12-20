package vn.edu.fpt.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.fpt.laboratory.constant.OrderStatusEnum;
import vn.edu.fpt.laboratory.dto.response.dashboard.GetStatisticsResponse;
import vn.edu.fpt.laboratory.entity.*;
import vn.edu.fpt.laboratory.repository.*;
import vn.edu.fpt.laboratory.service.StatisticService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final ProjectRepository projectRepository;
    private final MaterialRepository materialRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    @Override
    public GetStatisticsResponse getStatistic() {
        LocalDate date = LocalDate.now();
        Statistic statistic = statisticRepository.findByDate(date);

        Integer numOfLab = statistic.getNumOfLab();
        Integer numOfLabOnDay = laboratoryRepository.countByCreatedDateBetween(date.atStartOfDay(), LocalDateTime.now());
        Integer totalLab = numOfLab + numOfLabOnDay;

        Integer numOfProject = statistic.getNumOfProject();
        Integer numOfProjectOnDay = projectRepository.countByCreatedDateBetween(date.atStartOfDay(), LocalDateTime.now());
        Integer totalProject = numOfProject + numOfProjectOnDay;

        Integer numOfMaterial = statistic.getNumOfMaterial();
        Integer numOfMaterialOnDay = materialRepository.countByCreatedDateBetween(date.atStartOfDay(), LocalDateTime.now());
        Integer totalMaterial = numOfMaterial + numOfMaterialOnDay;

        Integer numOfMember = statistic.getNumOfMember();
        Integer numOfMemberOnDay = memberInfoRepository.countByCreatedDateBetween(date.atStartOfDay(), LocalDateTime.now());
        Integer totalMember = numOfMember + numOfMemberOnDay;

        Long timeOfProject = statistic.getTotalTimeOfProject();
        Long timeOfProjectOnDay = Long.valueOf(0);
        List<Project> projects = projectRepository.findByCreatedDateBetween(date.atStartOfDay(), LocalDateTime.now());
        for (Project p : projects) {
            timeOfProjectOnDay = timeOfProjectOnDay + ChronoUnit.HOURS.between(date.atStartOfDay(), LocalDateTime.now());
        }
        Long totalTimeOfProject = timeOfProject + timeOfProjectOnDay;

        Integer numOfItemBorrowed = statistic.getNumOfMaterialBorrowed();
        List<OrderHistory> orderHistories = orderHistoryRepository.findByStatusAndActuallyReturnBetween(OrderStatusEnum.COMPLETED, date.atStartOfDay(), LocalDateTime.now());
        Integer numOfItemBorrowedOnDay = 0;
        for (OrderHistory o : orderHistories) {
            numOfItemBorrowedOnDay = numOfItemBorrowedOnDay + o.getAmount();
        }
        Integer totalItemBorrowed = numOfItemBorrowed + numOfItemBorrowedOnDay;

        //Integer numOfMemberBorrowed = statistic.getNumOfMemberBorrowed();
        Integer totalMemberBorrowed = orderHistoryRepository.countCreateByInOrderHistoriesByStatusAndActuallyReturnBetween(date.atStartOfDay(), LocalDateTime.now()).size();
        //Integer totalMemberBorrowed = numOfMemberBorrowed + numOfMemberBorrowedOnDay;


        GetStatisticsResponse response = GetStatisticsResponse.builder()
                .numOfLab(totalLab)
                .numOfProject(totalProject)
                .numOfMaterial(totalMaterial)
                .avgProjectInLab(totalProject/totalLab)
                .avgMemberInLab(totalMember/totalLab)
                .avgMemberInProject(totalMember/totalProject)
                .avgTimeOfProject(totalTimeOfProject/totalProject)
                .numOfItemBorrowed(totalItemBorrowed)
                .numOfMemberBorrowed(totalMemberBorrowed)
                .build();
        return response;
    }
}
