package vn.edu.fpt.laboratory.config.datetime;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static vn.edu.fpt.laboratory.utils.CustomDateTimeFormatter.DATE_FORMATTER;
import static vn.edu.fpt.laboratory.utils.CustomDateTimeFormatter.DATE_TIME_FORMATTER;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 07:32
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public class CustomDateDeserializer extends StdDeserializer<LocalDate> {

        public CustomDateDeserializer(){
            this(null);
        }

        protected CustomDateDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            return LocalDate.parse(jsonParser.getText(), DATE_FORMATTER);
        }
}
