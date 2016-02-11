package com.ipartek.formacion.backoffice.controladores;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.backoffice.Constantes;
import com.ipartek.formacion.backoffice.pojo.Persona;
import com.ipartek.formacion.backoffice.pojo.Rol;
import com.ipartek.formacion.backoffice.service.PersonaService;
import com.ipartek.formacion.backoffice.service.impl.PersonaServiceImpl;

/**
 * Servlet implementation class UsuarioServlet
 */
public class UsuarioServlet extends MasterServlet {
	private static String pId; // Parámetro identificador del usuario, aunque
	// sea un id, es un string, luego se parsea
	private static int operacion;
	private static PersonaService servicioPersona;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		servicioPersona = PersonaServiceImpl.getSingleton();
	}

	@Override
	public void destroy() {
		super.destroy();
		servicioPersona = null;
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			// recoger parámetros a realizar
			if (request.getParameter("op") != null) {
				operacion = Integer.parseInt(request.getParameter("op"));
			} else {
				operacion = Constantes.OP_LISTAR;
			}

			// Realizar accion
			switch (operacion) {
			case Constantes.OP_LISTAR:
				listar(request);
				break;
			case Constantes.OP_DETALLE:
				detalle(request);
				break;
			case Constantes.OP_NUEVO:
				nuevo(request);
				break;
			case Constantes.OP_ELIMINAR:
				eliminar(request);
				break;
			case Constantes.OP_MODIFICAR:
				modificarCrear(request);
				break;
			}
			request.setAttribute("msj", msj);
			/*
			 * forward para servir la jsp (lanzarlo). en forward hay que poner
			 * dos argumentos. doGet tiene dos request y response, al ser una
			 * petición interna, hay que poner los mismos
			 */
			dispatch.forward(request, response);
		} catch (Exception e) {
			// TODO mejor en un LOG
			e.printStackTrace();

			// TODO ir a página error 404.jsp o 500.jsp
		}
	}

	/**
	 * Modifica o crea una nueva persona
	 *
	 * @param request
	 * @throws ParseException
	 * @throws SQLException
	 */
	private void modificarCrear(HttpServletRequest request)
			throws ParseException, SQLException {

		// recoger parámetros formulario
		int id = Integer.parseInt(request.getParameter("id")), rolId = Integer
				.parseInt(request.getParameter("rol"));
		String pNombre = request.getParameter("nombre"), pDni = request
				.getParameter("dni"), pPass = request.getParameter("pass"), pEmail = request
				.getParameter("email"), pObservaciones = request
				.getParameter("observaciones"), pFecha = request
				.getParameter("fecha");

		Rol rol = daoRol.getById(rolId);
		// construir persona
		Persona per = new Persona();
		per.setId(id);
		per.setNombre(pNombre);
		per.setDni(pDni);
		per.setPass(pPass);
		per.setEmail(pEmail);
		per.setObservaciones(pObservaciones);
		per.setRol(rol);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsed = format.parse(pFecha);
		per.setFechaNacimiento(new java.sql.Date(parsed.getTime()));
		// persistir en la bbdd
		if (per.getId() == -1) {
			if (daoPersona.insert(per) != -1) {
				msj = new Mensaje("Usuario insertado con éxito",
						Mensaje.TIPO_SUCCESS);
			} else {
				msj = new Mensaje("No se ha insertado el usuario",
						Mensaje.TIPO_WARNING);
			}
		} else if (daoPersona.update(per)) {
			msj = new Mensaje("Registro modificado con éxito",
					Mensaje.TIPO_SUCCESS);
		} else {
			msj = new Mensaje("No se ha modificado el registro",
					Mensaje.TIPO_WARNING);
		}
		// listar
		listar(request);
	}

	private void eliminar(HttpServletRequest request) throws SQLException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			if (daoPersona.delete(id)) {
				msj = new Mensaje("Registro eliminado con éxito",
						Mensaje.TIPO_SUCCESS);
			} else {
				msj = new Mensaje("No se ha eliminado el registro",
						Mensaje.TIPO_DANGER);
			}
		} catch (Exception e) {
			msj = new Mensaje("No se ha eliminado el registro",
					Mensaje.TIPO_DANGER);
		}
		listar(request);
	}

	/**
	 * Nos lleva a la vista del formulario para crear una persona
	 *
	 * @param request
	 * @throws SQLException
	 */
	private void nuevo(HttpServletRequest request) throws SQLException {
		Persona p = new Persona();
		request.setAttribute("persona", p);
		ArrayList<Rol> roles = (ArrayList<Rol>) daoRol.getAll();
		request.setAttribute("roles", roles);
		dispatch = request.getRequestDispatcher(Constantes.VIEW_USER_FORM);
	}

	private void listar(HttpServletRequest request) throws SQLException {

		// Guardar listado como atributo en request
		request.setAttribute("listaUsuarios", servicioPersona.listar());

		// Petición interna a la jsp (RequestDistapecher es para decirle a donde
		// tiene que ir, se carga el dispatcher)
		dispatch = request.getRequestDispatcher(Constantes.VIEW_USER_LIST);
	}

	private void detalle(HttpServletRequest request)
			throws NumberFormatException, SQLException {
		pId = request.getParameter("id");
		request.setAttribute("persona",
				servicioPersona.detalle(Integer.parseInt(pId)));

		// TODO Servicio para los Roles
		ArrayList<Rol> roles = (ArrayList<Rol>) daoRol.getAll();
		request.setAttribute("roles", roles);
		dispatch = request.getRequestDispatcher(Constantes.VIEW_USER_FORM);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}