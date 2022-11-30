package vn.edu.fpt.laboratory.repository;

import vn.edu.fpt.laboratory.entity.DisplayMessage;

import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:45
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface DisplayMessageRepository {

    Optional<DisplayMessage> findByCodeAndLanguage(String code, String language);

}
