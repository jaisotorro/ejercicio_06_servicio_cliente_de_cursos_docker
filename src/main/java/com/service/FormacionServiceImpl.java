package com.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.model.Curso;
import com.model.Formacion;

@Service
public class FormacionServiceImpl implements FormacionService {

	@Autowired
	RestTemplate restTemplate;

//	String url = "http://localhost:8082/"; -> error
//	String url = "http://localhost:9100/"; -> error
	String url = "http://192.168.0.156:9100/";
	
	@Override
	public List<Formacion> getAllFormaciones() {
		List<Formacion> formaciones = new ArrayList<>(); 
		Arrays.asList(restTemplate.getForObject(url+"cursos", Curso[].class)).forEach(c -> {
			Formacion formacon = new Formacion(c.getNombre(), c.getDuracion() < 50 ? 5 : 10, c.getPrecio());
			formaciones.add(formacon);
		});
		return formaciones;
	}

	@Override
	public String createFormacion(Formacion formacion) {
		List<Curso> cursos = Arrays.asList(restTemplate.getForObject(url+"cursos", Curso[].class));
		if (cursos.stream().anyMatch(c -> c.getNombre().equals(formacion.getCurso()))) {
			return "Ya existe una formación con este nombre";
		}
//Intento 1 fallido 		
//		Curso c = restTemplate.postForObject(url + "curso", new Curso(formacion.getCurso().substring(0, 2),
//				formacion.getCurso(), formacion.getAsignaturas() + 10, formacion.getPrecio()), Curso.class);

//Intento 2 fallido	
//		restTemplate.postForObject(url + "curso", new Curso(formacion.getCurso().substring(0, 2),
//		formacion.getCurso(), formacion.getAsignaturas() + 10, formacion.getPrecio()), Curso.class);
		
		restTemplate.postForLocation(url + "curso", new Curso(formacion.getCurso().substring(0, 3).toUpperCase(),
				formacion.getCurso(), formacion.getAsignaturas() * 10, formacion.getPrecio()));
		
		return "Formacion creada correctamente";
	}

}
