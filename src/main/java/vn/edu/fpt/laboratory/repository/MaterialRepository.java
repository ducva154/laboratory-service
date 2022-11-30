package vn.edu.fpt.laboratory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.laboratory.entity.Material;

import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:29
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface MaterialRepository extends MongoRepository<Material, String> {

    Optional<Material> findByMaterialName(String materialName);
}
