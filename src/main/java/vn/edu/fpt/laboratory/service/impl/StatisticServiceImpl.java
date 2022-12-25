package vn.edu.fpt.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.fpt.laboratory.constant.OrderStatusEnum;
import vn.edu.fpt.laboratory.dto.response.dashboard.GetStatisticsResponse;
import vn.edu.fpt.laboratory.entity.*;
import vn.edu.fpt.laboratory.exception.BusinessException;
import vn.edu.fpt.laboratory.repository.*;
import vn.edu.fpt.laboratory.service.StatisticService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;


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
        Statistic statistic = statisticRepository.findTopByOrderByLastModifiedDateDesc();
        Integer numOfLab = 0;
        Integer numOfProject = 0;
        Integer numOfMaterial = 0;
        Integer numOfMember = 0;
        Long timeOfProject = Long.valueOf(0);
        Integer numOfItemBorrowed = 0;
        LocalDateTime dateTime = LocalDateTime.parse("2022-01-01T00:00:00");

        if (Objects.nonNull(statistic)) {
            numOfLab = statistic.getNumOfLab();
            numOfProject = statistic.getNumOfProject();
            numOfMaterial = statistic.getNumOfMaterial();
            numOfMember = statistic.getNumOfMember();
            timeOfProject = statistic.getTotalTimeOfProject();
            numOfItemBorrowed = statistic.getNumOfMaterialBorrowed();
            dateTime = statistic.getLastModifiedDate();
        }

        Integer numOfLabOnDay = laboratoryRepository.countByCreatedDateBetween(dateTime, LocalDateTime.now());
        Integer totalLab = numOfLab + numOfLabOnDay;

        Integer numOfProjectOnDay = projectRepository.countByCreatedDateBetween(dateTime, LocalDateTime.now());
        Integer totalProject = numOfProject + numOfProjectOnDay;


        Integer numOfMaterialOnDay = materialRepository.countByCreatedDateBetween(dateTime, LocalDateTime.now());
        Integer totalMaterial = numOfMaterial + numOfMaterialOnDay;


        Integer numOfMemberOnDay = memberInfoRepository.countByCreatedDateBetween(dateTime, LocalDateTime.now());
        Integer totalMember = numOfMember + numOfMemberOnDay;

        Long timeOfProjectOnDay = Long.valueOf(0);
        List<Project> projects = projectRepository.findByCreatedDateBetween(dateTime, LocalDateTime.now());
        for (Project p : projects) {
            timeOfProjectOnDay = timeOfProjectOnDay + ChronoUnit.HOURS.between(dateTime, LocalDateTime.now());
        }
        Long totalTimeOfProject = timeOfProject + timeOfProjectOnDay;


        List<OrderHistory> orderHistories = orderHistoryRepository.findByStatusAndActuallyReturnBetween(OrderStatusEnum.COMPLETED, dateTime, LocalDateTime.now());
        Integer numOfItemBorrowedOnDay = 0;
        for (OrderHistory o : orderHistories) {
            numOfItemBorrowedOnDay = numOfItemBorrowedOnDay + o.getAmount();
        }
        Integer totalItemBorrowed = numOfItemBorrowed + numOfItemBorrowedOnDay;

        //Integer numOfMemberBorrowed = statistic.getNumOfMemberBorrowed();
        Integer totalMemberBorrowed = orderHistoryRepository.countCreateByInOrderHistoriesByActuallyReturnBetween(dateTime, LocalDateTime.now()).size();
        //Integer totalMemberBorrowed = numOfMemberBorrowed + numOfMemberBorrowedOnDay;

        if (Objects.isNull(statistic)) statistic = new Statistic();
        statistic.setNumOfLab(totalLab);
        statistic.setNumOfMember(totalMember);
        statistic.setNumOfProject(totalProject);
        statistic.setTotalTimeOfProject(totalTimeOfProject);
        statistic.setNumOfMaterial(totalMaterial);
        statistic.setNumOfMaterialBorrowed(totalItemBorrowed);
        statistic.setNumOfMemberBorrowed(totalMemberBorrowed);

        try {
            statisticRepository.save(statistic);
            log.info("Update statistic to database success");
        } catch (Exception ex) {
            throw new BusinessException("Can't Update statistic in database: " + ex.getMessage());
        }

        GetStatisticsResponse response = GetStatisticsResponse.builder()
                .numOfLab(totalLab)
                .numOfProject(totalProject)
                .numOfMaterial(totalMaterial)
                .avgProjectInLab(totalProject*1.0/totalLab)
                .avgMemberInLab(totalMember*1.0/totalLab)
                .avgMemberInProject(totalMember*1.0/totalProject)
                .avgTimeOfProject(totalTimeOfProject*1.0/totalProject)
                .numOfItemBorrowed(totalItemBorrowed)
                .numOfMemberBorrowed(totalMemberBorrowed)
                .build();
        return response;
    }
}
