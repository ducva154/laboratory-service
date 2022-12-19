package vn.edu.fpt.laboratory.config.batch.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import vn.edu.fpt.laboratory.entity.OrderHistory;

@Slf4j
public class OrderHistoryItemProcessor implements ItemProcessor<OrderHistory, OrderHistory> {

    @Override
    public OrderHistory process(OrderHistory orderHistory) throws Exception {
        return null;
    }
}
