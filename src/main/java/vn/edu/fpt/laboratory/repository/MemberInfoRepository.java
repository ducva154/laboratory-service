package vn.edu.fpt.laboratory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.laboratory.entity.Material;
import vn.edu.fpt.laboratory.entity.MemberInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:29
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface MemberInfoRepository extends MongoRepository<MemberInfo, String> {

    List<MemberInfo> findAllByAccountId(String accountId);
}
