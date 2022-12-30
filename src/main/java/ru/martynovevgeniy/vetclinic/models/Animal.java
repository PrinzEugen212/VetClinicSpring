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
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false)
    @NotEmpty(message = "Введите кличку питомца")
    @Size(max = 32, message = "Кличка не должна превышать 32 символа")
    private String name;

    @Column(length = 8, nullable = false)
    @NotEmpty(message = "Введите пол питомца")
    @Size(max = 8, message = "Пол не должен превышать 8 символов")
    private String gender;

    @Column(length = 64, nullable = false)
    @NotEmpty(message = "Введите вид питомца")
    @Size(max = 64, message = "Вид не должен превышать 64 символа")
    private String type;

    @Column(length = 64)
    @Size(max = 64, message = "Порода не должна превышать 64 символа")
    private String breed;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"})
    @NotNull(message = "Введите дату рождения питомца")
    private LocalDate birthday;

    @Column(length = 100)
    @Size(max = 100, message = "Путь для фото не должен превышать 100 символов")
    private String path;

    @ManyToOne
    @JsonBackReference
    private Client client;

    @OneToMany(mappedBy = "animal")
    @JsonManagedReference
    private List<Visit> visits;

}