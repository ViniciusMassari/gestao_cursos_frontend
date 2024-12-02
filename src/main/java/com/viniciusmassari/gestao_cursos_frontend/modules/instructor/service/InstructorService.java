package com.viniciusmassari.gestao_cursos_frontend.modules.instructor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.Authentication;

import com.viniciusmassari.gestao_cursos_frontend.modules.instructor.dto.AuthInstructorDTO;
import com.viniciusmassari.gestao_cursos_frontend.modules.instructor.dto.AuthInstructorResponseDTO;
import com.viniciusmassari.gestao_cursos_frontend.modules.instructor.dto.CreateInstructorDTO;
import com.viniciusmassari.gestao_cursos_frontend.modules.instructor.dto.InstructorProfileDTO;

@Service
public class InstructorService {

    @Value("${host.api.gestao.cursos}")
    private String hostAPIGestaoCursos;

    public void createInstructor(CreateInstructorDTO createInstructorDTO) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateInstructorDTO> request = new HttpEntity<>(createInstructorDTO, headers);
        var url = hostAPIGestaoCursos.concat("/instructor/create");

        rt.postForObject(url, request, String.class);
    }

    public AuthInstructorResponseDTO loginInstructor(AuthInstructorDTO authInstructorDTO) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var request = new HttpEntity<>(authInstructorDTO, headers);

        var url = hostAPIGestaoCursos.concat("/instructor/auth/");

        return rt.postForObject(url, request, AuthInstructorResponseDTO.class);
    }

    public InstructorProfileDTO get_instructor_profile(String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        var request = new HttpEntity<>(headers);

        var url = hostAPIGestaoCursos.concat("/instructor/");

        try {
            var result = rt.exchange(
                    url, HttpMethod.GET, request,
                    InstructorProfileDTO.class);
            return result.getBody();
        } catch (Unauthorized ex) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

    public String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getDetails().toString();
    }

}
