package ru.martynovevgeniy.vetclinic.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = {"dd.MM.yyyy HH:mm:ss"})
    @NotNull(message = "Введите дату приёма")
    private LocalDateTime date;

    @Column(length = 500, nullable = false)
    @NotEmpty(message = "Введите диагноз")
    @Size(max = 500, message = "Диагноз не должен превышать 500 символов")
    private String diagnosis;

    @Column(length = 500, nullable = false)
    @NotEmpty(message = "Введите назначение")
    @Size(max = 500, message = "Назначение не должно превышать 500 символов")
    private String assignment;

    @Column(nullable = false)
    @NotNull(message = "Введите общую стоимость")
    private Integer totalCost;

    @ManyToOne
    @JsonBackReference
    private Client client;

    @ManyToOne
    @JsonBackReference
    private Animal animal;

    @ManyToOne
    @JsonBackReference
    private Employee employee;

    @ManyToOne
    @JsonBackReference
    private Employee helperEmployee;

    @ManyToMany()
    @JsonManagedReference
    private List<Treatment> treatments;

}
