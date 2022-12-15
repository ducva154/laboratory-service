package vn.edu.fpt.laboratory.config.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vn.edu.fpt.laboratory.dto.event.ModifyMemberInProjectEvent;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 14/12/2022 - 17:15
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
public class ModifyMemberInProjectProducer extends Producer{

    private final ObjectMapper objectMapper;

    @Autowired
    public ModifyMemberInProjectProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        super(kafkaTemplate);
        this.objectMapper = objectMapper;
    }

    @Override
    protected void sendMessage(String topic, String key, String value) {
        super.sendMessage(topic, key, value);
    }

    public void sendMessage(ModifyMemberInProjectEvent event){

    }
}
