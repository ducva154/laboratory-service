package vn.edu.fpt.laboratory.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.laboratory.constant.AppConstant;
import vn.edu.fpt.laboratory.entity.DisplayMessage;

import java.util.Objects;
import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 16:24
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
@RequiredArgsConstructor
@Slf4j
public class DisplayMessageRepositoryImpl implements DisplayMessageRepository{

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<DisplayMessage> findByCodeAndLanguage(String code, String language) {
        if (Objects.isNull(language)){
            language = AppConstant.DEFAULT_LANGUAGE;
        }

        String displayMessageStr = redisTemplate.opsForValue().get(String.format("%s:%s", code, language));
        try {
            return Optional.of(objectMapper.convertValue(displayMessageStr, DisplayMessage.class));
        }catch (Exception ex) {
            log.error("Can't get display message from: {}", ex.getMessage());
            return Optional.empty();
        }
    }
}
