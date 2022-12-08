package vn.edu.fpt.laboratory.config.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vn.edu.fpt.laboratory.dto.event.ModifyMembersToWorkspaceEvent;
import vn.edu.fpt.laboratory.exception.BusinessException;

import java.util.UUID;

@Service
public class ModifyMembersToWorkspaceProducer extends Producer {
    private ObjectMapper objectMapper;

    @Autowired
    public ModifyMembersToWorkspaceProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        super(kafkaTemplate);
        this.objectMapper = objectMapper;
    }

    public void sendMessage(ModifyMembersToWorkspaceEvent event) {
        try {
            String value = objectMapper.writeValueAsString(event);
            super.sendMessage("flab.workspace.add-member-to-workspace", UUID.randomUUID().toString(), value);
        } catch (JsonProcessingException e) {
            throw new BusinessException("Can't convert Add Member To Workspace event to String: " + e.getMessage());
        }
    }
}
