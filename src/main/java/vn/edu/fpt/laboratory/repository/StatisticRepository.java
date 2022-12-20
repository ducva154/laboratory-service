package vn.edu.fpt.laboratory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.laboratory.entity.Statistic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticRepository extends MongoRepository<Statistic, String> {
    Statistic findByDate(LocalDate date);
}
