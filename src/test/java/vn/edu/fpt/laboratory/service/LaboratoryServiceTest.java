package vn.edu.fpt.laboratory.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.mapping.MongoId;
import vn.edu.fpt.laboratory.dto.request.laboratory.CreateLaboratoryRequest;
import vn.edu.fpt.laboratory.entity.Laboratory;
import vn.edu.fpt.laboratory.repository.LaboratoryRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LaboratoryServiceTest {

    @Mock
    private LaboratoryRepository laboratoryRepository;
    @Mock
    private LaboratoryService laboratoryService;

    @Test
    void createLaboratorySuccess() {
        CreateLaboratoryRequest request = CreateLaboratoryRequest.builder()
                .labName("Lab for unit test")
                .description("This lab room is created to test")
                .major("SE")
                .build();
        laboratoryService.createLaboratory(request);
        ArgumentCaptor<Laboratory> laboratoryArgumentCaptor = ArgumentCaptor.forClass(Laboratory.class);
        verify(laboratoryRepository).save(laboratoryArgumentCaptor.capture());
        Laboratory laboratory = laboratoryArgumentCaptor.getValue();
        assertEquals(request.getLabName(), laboratory.getLaboratoryName());
        assertEquals(request.getDescription(), laboratory.getDescription());
        assertEquals(request.getMajor(), laboratory.getMajor());
    }

    @Test
    void updateLaboratory() {
    }

    @Test
    void deleteLaboratory() {
    }

    @Test
    void getLaboratoryDetail() {
    }

    @Test
    void getLaboratory() {
    }

    @Test
    void getLaboratorySuggestion() {
    }

    @Test
    void getMemberInLab() {
    }

    @Test
    void removeMemberFromLaboratory() {
    }

    @Test
    void applyToLaboratory() {
    }

    @Test
    void getApplicationByApplicationId() {
    }

    @Test
    void getApplicationByLabId() {
    }

    @Test
    void reviewApplication() {
    }

    @Test
    void getMemberNotInLab() {
    }
}