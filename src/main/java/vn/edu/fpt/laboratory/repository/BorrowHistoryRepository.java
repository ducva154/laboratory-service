package vn.edu.fpt.laboratory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.laboratory.entity.OrderHistory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:32
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface BorrowHistoryRepository extends MongoRepository<OrderHistory, String> {
}
