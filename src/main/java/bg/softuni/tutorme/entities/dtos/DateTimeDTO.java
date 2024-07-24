package bg.softuni.tutorme.entities.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeDTO {

    @NotNull
    private String dateTime;

    public DateTimeDTO() {
    }

    public String getDateTime() {
        return dateTime;
    }

    public DateTimeDTO setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }
}
