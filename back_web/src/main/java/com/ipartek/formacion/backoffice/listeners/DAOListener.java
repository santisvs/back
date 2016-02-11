package com.ipartek.formacion.backoffice.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ipartek.formacion.backoffice.modelo.PersonaDAO;
import com.ipartek.formacion.backoffice.modelo.TablaAuxiliarDAO;

/**
 * Application Lifecycle Listener implementation class DAOListener
 *
 */
public class DAOListener implements ServletContextListener {

	public static PersonaDAO daoPersona;
	public static TablaAuxiliarDAO daoRol;

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		if (DBListener.error == false) {
			daoPersona = new PersonaDAO();
			daoRol = new TablaAuxiliarDAO();
		}
	}

}
