package vn.edu.fpt.laboratory.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.laboratory.constant.OrderStatusEnum;
import vn.edu.fpt.laboratory.entity.OrderHistory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 22:56
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface OrderHistoryRepository extends MongoRepository<OrderHistory, String> {
    List<OrderHistory> findByStatusAndActuallyReturnBetween(OrderStatusEnum status, LocalDateTime from, LocalDateTime to);

    @Aggregation(pipeline = {
            "{'$match':  {'status':  'COMPLETED'}}",
            "{'$group':{'_id': {'created_by': '$created_by'}} }"
    })
    List<OrderHistory> countCreateByInOrderHistoriesByActuallyReturnBetween(LocalDateTime from, LocalDateTime to);
    List<OrderHistory> getOrderHistoriesByMaterialIdAndStatus(String materialId, String status);

    List<OrderHistory> findByMaterialIdAndStatus(String materialId, String status);
}
