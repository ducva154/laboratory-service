package vn.edu.fpt.laboratory.config.datetime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static vn.edu.fpt.laboratory.utils.CustomDateTimeFormatter.DATE_FORMATTER;
import static vn.edu.fpt.laboratory.utils.CustomDateTimeFormatter.DATE_TIME_FORMATTER;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 07:33
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public class CustomDateSerializer extends StdSerializer<LocalDate> {

        public CustomDateSerializer(){
            this(null);
        }

        public CustomDateSerializer(Class<LocalDate> t) {
            super(t);
        }

        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

            jsonGenerator.writeString(localDate.format(DATE_FORMATTER));
        }
    }
