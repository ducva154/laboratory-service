package vn.edu.fpt.laboratory.service;

import org.springframework.security.core.Authentication;

import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 20:20
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface _TokenService {

    Optional<Authentication> getAuthenticationFromToken(String token);

}
