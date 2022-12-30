package ru.martynovevgeniy.vetclinic.modelsForLayout;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.martynovevgeniy.vetclinic.models.Employee;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Report {

    Employee employee;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = {"dd.MM.yyyy HH:mm:ss"})
    @NotNull(message = "Введите Дату начала")
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = {"dd.MM.yyyy HH:mm:ss"})
    @NotNull(message = "Введите Дату окончания")
    private LocalDateTime endDate;

}