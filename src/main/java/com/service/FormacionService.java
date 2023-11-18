package com.service;

import java.util.List;

import com.model.Formacion;

public interface FormacionService {
	List<Formacion> getAllFormaciones();
	String createFormacion(Formacion formacion);
}
