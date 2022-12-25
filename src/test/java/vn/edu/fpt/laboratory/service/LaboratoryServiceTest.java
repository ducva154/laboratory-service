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
    @Test
    void createLaboratorySuccess() {
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
