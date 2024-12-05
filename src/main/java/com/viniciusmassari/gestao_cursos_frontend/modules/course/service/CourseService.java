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
import com.viniciusmassari.gestao_cursos_frontend.modules.course.dto.DeleteCourseDTO;
import com.viniciusmassari.gestao_cursos_frontend.modules.course.dto.ShowAllCourseResponseDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseService {

    private Logger logger = LoggerFactory.getLogger(getClass());

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

    public void delete_course(DeleteCourseDTO deleteCourseDTO) {
        logger.info("METODO delete_course chamado");
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());

        HttpEntity<DeleteCourseDTO> request = new HttpEntity<>(null, headers);
        var url = hostAPIGestaoCursos.concat("/courses/" + deleteCourseDTO.getCourseID());

        rt.exchange(url, HttpMethod.DELETE, request, DeleteCourseDTO.class);
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
