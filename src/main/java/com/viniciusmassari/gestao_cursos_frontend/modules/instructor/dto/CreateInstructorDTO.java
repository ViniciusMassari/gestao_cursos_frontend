package com.viniciusmassari.gestao_cursos_frontend.modules.instructor.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateInstructorDTO {

    private String name;

    private String email;

    private String password;
}