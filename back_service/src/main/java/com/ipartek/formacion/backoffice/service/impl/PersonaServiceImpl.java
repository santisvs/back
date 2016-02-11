package com.ipartek.formacion.backoffice.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ipartek.formacion.backoffice.modelo.PersonaDAO;
import com.ipartek.formacion.backoffice.pojo.Persona;
import com.ipartek.formacion.backoffice.service.PersonaService;

public class PersonaServiceImpl implements PersonaService {

	private static PersonaService singleton = null;
	private PersonaDAO daoPersona;

	/*
	 * COnstructor privado para patrón singleton
	 */
	private PersonaServiceImpl() {
		super();
		daoPersona = new PersonaDAO();
	}

	/*
	 * Patron de diseño SINGLETON Para que solo se pueda crear un objeto de esta
	 * clase
	 */
	public static PersonaService getSingleton() {
		if (singleton == null) {
			singleton = new PersonaServiceImpl();
		}
		return singleton;
	}

	@Override
	public List<Persona> listar() {
		ArrayList<Persona> list;
		try {
			list = (ArrayList<Persona>) daoPersona.getAll();
		} catch (Exception e) {
			// TODO Log
			e.printStackTrace();
			list = new ArrayList<Persona>();
		}
		return list;
	}

	@Override
	public boolean insertar(Persona p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminar(Persona p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificar(Persona p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Persona> buscar(String criterio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Persona detalle(int id) {
		Persona p;
		try {
			p = daoPersona.getById(id);
		} catch (Exception e) {
			// TODO Log
			e.printStackTrace();
			p = new Persona();
		}
		return p;
	}

}
