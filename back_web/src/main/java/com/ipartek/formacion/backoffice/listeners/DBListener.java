package com.ipartek.formacion.backoffice.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.ipartek.formacion.backoffice.modelo.DbConnection;

/**
 * Application Lifecycle Listener implementation class DBListener
 *
 */
public class DBListener implements ServletContextListener {

	private final static Logger log = Logger.getLogger(DBListener.class);
	public static boolean error = false;

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		error = false;
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		
		try {
			new DbConnection();			
		} catch (Exception e) {
			log.error("No hay conexión con la base de datos: " + e.getMessage());
			error = true;
		} 
	}
}