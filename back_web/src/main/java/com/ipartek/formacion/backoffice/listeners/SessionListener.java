package com.ipartek.formacion.backoffice.listeners;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ipartek.formacion.backoffice.pojo.Persona;
 
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
	
    private int sessionCount = 0;
    private int userLoggedCount = 0;
    public static ArrayList<Persona> listaUsariosLogeados = new ArrayList<Persona>();
    private ServletContext sc;
 
    @Override
    public void sessionCreated(HttpSessionEvent event) {
    	    	
        synchronized (this) {        	
            sessionCount++;
            sc = event.getSession().getServletContext();
            sc.setAttribute("visitantes", sessionCount);
        }
 
        System.out.println("Session Created: " + event.getSession().getId());
        System.out.println("Total Sessions: " + sessionCount);
    }
 
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        synchronized (this) {
            sessionCount--;
            sc = event.getSession().getServletContext();
            sc.setAttribute("visitantes", sessionCount);
        }
        System.out.println("Session Destroyed: " + event.getSession().getId());
        System.out.println("Total Sessions: " + sessionCount);
    }

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		
		System.out.println("session attributeAdded " + se.getName() );
		
		if ( "userlogged".equals( se.getName())){
			synchronized (this) {
				userLoggedCount++;
				listaUsariosLogeados.add( (Persona)se.getValue() ); 				
			}
		}
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		
		System.out.println("session attributeRemoved " + se.getName() );
		if ( "userlogged".equals( se.getName())){
			synchronized (this) {
				userLoggedCount++;
				listaUsariosLogeados.remove( (Persona)se.getValue() ); 				
			}
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		
		System.out.println("session attributeReplaced " + se.getName() );
		
	}
}