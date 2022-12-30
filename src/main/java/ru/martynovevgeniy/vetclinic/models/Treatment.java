package ru.martynovevgeniy.vetclinic.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, unique = true, nullable = false)
    @NotEmpty(message = "Введите название процедуры")
    @Size(max = 64, message = "Название процедуры не должна превышать 64 символа")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Введите стоимость")
    private Integer cost;

    @ManyToMany(mappedBy = "treatments")
    @JsonManagedReference
    private List<Visit> visits;

}