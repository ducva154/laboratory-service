package vn.edu.fpt.laboratory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.laboratory.entity.Project;

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
public interface ProjectRepository extends MongoRepository<Project, String> {

    Optional<Project> findByProjectName(String projectName);

    Integer countByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

    List<Project> findByCreatedDateBetween(LocalDateTime from, LocalDateTime to);
}
