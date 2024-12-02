package com.viniciusmassari.gestao_cursos_frontend.modules.instructor.dto;

import java.util.List;

import lombok.Data;

@Data
public class AuthInstructorResponseDTO {
    String token;
    private List<String> roles;
}
