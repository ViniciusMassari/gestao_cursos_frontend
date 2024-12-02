package com.viniciusmassari.gestao_cursos_frontend.modules.course.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.viniciusmassari.gestao_cursos_frontend.modules.course.dto.CreateCourseDTO;
import com.viniciusmassari.gestao_cursos_frontend.modules.course.dto.ShowAllCourseResponseDTO;

@Service
public class CourseService {
    @Value("${host.api.gestao.cursos}")
    private String hostAPIGestaoCursos;

    public void create_course(CreateCourseDTO createCourseDTO) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());

        HttpEntity<CreateCourseDTO> request = new HttpEntity<>(createCourseDTO, headers);
        var url = hostAPIGestaoCursos.concat("/courses/");

        rt.postForObject(url, request, CreateCourseDTO.class);
    }

    public ShowAllCourseResponseDTO showAllCourses() {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(null, headers);
        var url = hostAPIGestaoCursos.concat("/courses/show/");

        return rt.exchange(url, HttpMethod.GET, request, ShowAllCourseResponseDTO.class).getBody();
    }

    public String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getDetails().toString();
    }
}
