package com.ipartek.formacion.backoffice.service;

import java.util.List;

import com.ipartek.formacion.backoffice.pojo.Persona;

public interface PersonaService {

	/**
	 * Coleccion de Personas, limitado a las últimas 500
	 *
	 * @return @{code List<Persona>} listado personas, si no existe lista vacia
	 */
	List<Persona> listar();

	/**
	 *
	 * @param p
	 * @return
	 */
	boolean insertar(Persona p);

	/**
	 *
	 * @param p
	 * @return
	 */
	boolean eliminar(Persona p);

	/**
	 *
	 * @param p
	 * @return
	 */
	boolean modificar(Persona p);

	Persona detalle(int id);

	/**
	 * Busca persona por cualquier propiedad
	 *
	 * @param criterio
	 *            {@code String} cadena alfanumerica, no permite expresiones
	 *            regulares
	 * @return {@code List<Persona>} listado de personas que coincidan con el
	 *         criterio
	 */
	List<Persona> buscar(String criterio);

}
