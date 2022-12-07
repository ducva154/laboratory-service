package vn.edu.fpt.laboratory.config.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vn.edu.fpt.laboratory.dto.event.CreateRepositoryEvent;
import vn.edu.fpt.laboratory.dto.event.CreateWorkspaceEvent;
import vn.edu.fpt.laboratory.exception.BusinessException;

import java.util.UUID;

/**
 * vn.edu.fpt.laboratory.config.kafka.producer
 *
 * @author : Portgas.D.Ace
 * @created : 06/12/2022
 * @contact : 0339850697- congdung2510@gmail.com
 **/
@Service
public class CreateRepositoryProducer extends Producer{
    private ObjectMapper objectMapper;

    @Autowired
    public CreateRepositoryProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        super(kafkaTemplate);
        this.objectMapper = objectMapper;
    }

    public void sendMessage(CreateRepositoryEvent event) {
        try {
            String value = objectMapper.writeValueAsString(event);
            super.sendMessage("flab.repository.create-repository", UUID.randomUUID().toString(), value);
        } catch (JsonProcessingException e) {
            throw new BusinessException("Can't convert Create Repository event to String: "+ e.getMessage());
        }
    }
}
