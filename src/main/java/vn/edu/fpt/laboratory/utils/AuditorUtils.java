package vn.edu.fpt.laboratory.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 01/09/2022 - 16:32
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public class AuditorUtils {

    public String getAccountId(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }
}
