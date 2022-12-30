package ru.martynovevgeniy.vetclinic.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, nullable = false)
    @NotEmpty(message = "Введите ФИО")
    @Size(max = 64, message = "ФИО не должно превышать 64 символа")
    private String fullName;

    @Column(length = 15, unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]*$", message = "Номер телефона может включать только цифры")
    @Size(max = 15, message = "Номер телефона не должен превышать 15 цифр")
    @NotEmpty(message = "Введите номер телефона")
    private String phone;

    @Column(length = 64, unique = true, nullable = false)
    @Email(message = "Некорректный ввод для Email")
    @Size(max = 64, message = "Email не должен превышать 64 символа")
    @NotEmpty(message = "Введите Email")
    private String email;

    @Column(length = 32, nullable = false)
    @NotEmpty(message = "Введите должность сотрудника")
    @Size(max = 32, message = "Должность не должна превышать 32 символа")
    private String post;

    @Column(length = 32)
    @Size(max = 32, message = "Специальность не должна превышать 32 символа")
    private String speciality;

    private boolean canHelp;

    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
    private List<Visit> visits;

    @OneToMany(mappedBy = "helperEmployee")
    @JsonManagedReference
    private List<Visit> visitsHelp;
}