package vn.edu.fpt.laboratory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.laboratory.entity._Image;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 21:47
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface _ImageRepository extends MongoRepository<_Image, String> {

}
