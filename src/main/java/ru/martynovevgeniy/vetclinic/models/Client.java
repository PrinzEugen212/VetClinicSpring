package ru.martynovevgeniy.vetclinic.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id;

    @Column(length = 64, nullable = false)
    @NotEmpty(message = "Введите ФИО")
    @Size(max = 64, message = "ФИО не должно превышать 64 символа")
    private String fullName;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"})
    @NotNull(message = "Введите дату рождения клиента")
    private LocalDate birthday;

    @Column(length = 15, unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]*$", message = "Номер телефона может включать только цифры")
    @Size(max = 15, message = "Номер телефона не должен превышать 15 цифр")
    @NotEmpty(message = "Введите номер телефона")
    private String phone;

    @Column(length = 64, unique = true)
    @Email(message = "Некорректный ввод для Email")
    @Size(max = 64, message = "Email не должен превышать 64 символа")
    private String email;

    @OneToMany(mappedBy = "client")
    @JsonManagedReference
    private List<Animal> animals;

    @OneToMany(mappedBy = "client")
    @JsonManagedReference
    private List<Visit> visits;

}