package vn.edu.fpt.laboratory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.laboratory.constant.ApplicationStatusEnum;
import vn.edu.fpt.laboratory.entity.AppConfig;
import vn.edu.fpt.laboratory.entity.Application;

import java.util.List;
import java.util.Optional;

@Repository

public interface ApplicationRepository extends MongoRepository<Application, String> {

    List<Application> findAllByAccountIdAndStatus(String accountId, ApplicationStatusEnum status);
}
