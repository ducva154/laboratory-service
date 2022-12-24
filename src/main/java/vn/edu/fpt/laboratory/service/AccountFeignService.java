package vn.edu.fpt.laboratory.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.fpt.laboratory.config.clould.feign.AccountFeignConfig;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.member.GetAccountNotInLabRequest;
import vn.edu.fpt.laboratory.dto.response.member.GetMemberNotInLabResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 23/12/2022 - 21:44
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@FeignClient(
        name = "feign.account-service",
        url = "${feign.account-service.url}",
        configuration = {AccountFeignConfig.class}
)
public interface AccountFeignService {

    @PostMapping(value = "/public/api/v1/accounts/lab", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GeneralResponse<PageableResponse<GetMemberNotInLabResponse>>> getAccountNotInLab(
            @RequestBody GetAccountNotInLabRequest request);

}
