package vn.edu.fpt.laboratory.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.laboratory.entity.Laboratory;
import vn.edu.fpt.laboratory.entity.MemberInfo;
import vn.edu.fpt.laboratory.entity.Statistic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:28
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface LaboratoryRepository extends MongoRepository<Laboratory, String> {

    Optional<Laboratory> findByLaboratoryName(String labName);

    Integer countByCreatedDateBetween(LocalDateTime from, LocalDateTime to);
}
