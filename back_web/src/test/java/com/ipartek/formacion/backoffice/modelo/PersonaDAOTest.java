package com.ipartek.formacion.backoffice.modelo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import org.junit.Ignore;

import com.ipartek.formacion.backoffice.pojo.Persona;

public class PersonaDAOTest {

	static DbConnection db;
	static Connection conn;
	static PersonaDAO dao;
	static Persona pMock1, pMock2;
	static int id1, id2; // identificador de la última operación realizada en el DAO

	@Ignore
	public static void setUpBeforeClass() throws Exception {

		db = new DbConnection();
		conn = db.getConnection();
		conn.setAutoCommit(false);
		dao = new PersonaDAO();
	}

	@Ignore
	public static void tearDownAfterClass() throws Exception {
		conn.rollback();
		db.desconectar();
		db = null;
		dao = null;
	}

	@Ignore
	public void setUp() throws Exception {
		GregorianCalendar calendar2 = new GregorianCalendar(1983, 9, 11),
				calendar = new GregorianCalendar(1980, 10, 15);
		pMock1 = new Persona("Mock", "44444444D", "123456", "Mock hace referencia a un objeto vacío", "mock1@gmail.com",
				new Date(calendar.getTimeInMillis()));
		pMock2 = new Persona("Mock2", "55555555E", "123456", "Mock hace referencia a un objeto vacío",
				"mock2@gmail.com", new Date(calendar2.getTimeInMillis()));

		id1 = dao.insert(pMock1);
		assertTrue("No se ha realizado la inserción", id1 > 0);
	}

	@Ignore
	public void tearDown() {
		try {
			assertTrue("No se pudo eliminar", dao.delete(id1));
		} catch (SQLException e) {
			fail("Hay algún problema en el test: " + e.getMessage());
		}
	}

	@Ignore
	public void testGetAll() {
		try {
			int personasSize = dao.getAll().size();
			assertTrue("debería al menos recuperar una persona", personasSize > 0);
			id2 = dao.insert(pMock2);
			assertTrue("No se ha realizado la inserción", id2 > 0);
			//assertTrue("debería al menos recuperar dos personas", (personasSize + 1) == dao.getAll().size());//No funciona dado que getAll recupera solo 500 y hay m�s de 500
			assertTrue("No se pudo eliminar", dao.delete(id2));
		} catch (SQLException e) {
			fail("Hay algún problema en el test: " + e.getMessage());
		}
	}

	@Ignore
	public void testGetById() {
		Persona p1;
		try {
			p1 = dao.getById(id1);
			System.out.println(pMock1 + "\n" + p1);
			assertTrue("No tienen los mismos atributos", pMock1.equals(p1));
		} catch (SQLException e) {
			fail("Hay algún problema en el test: " + e.getMessage());
		}
	}

	// Comprobar caso de id inexistente
	@Ignore
	public void testDelete() {
		try {
			assertFalse("No puede eliminar lo que no existe", dao.delete(-1));
		} catch (SQLException e) {
			fail("Hay algún problema en el test: " + e.getMessage());
		}
	}

	@Ignore
	public void testUpdate() {
		try {
			Persona p2 = dao.getById(id1);
			p2.setDni("12345678E");		
			p2.setNombre("Aitor");
			GregorianCalendar calendar = new GregorianCalendar(2010, 10, 11);
			p2.setFechaNacimiento(new Date(calendar.getTimeInMillis()));			
			assertTrue(dao.update(p2));
			assertTrue("No tienen los mismos atributos", p2.equals(dao.getById(id1)));
			
			//Test null
			assertFalse("no se puede modifica la base una persona que no existe", dao.update(null));
			
			//Test persona vac�a
			Persona p3 = new Persona();
			assertFalse("no se puede modifica la base una persona que no existe", dao.update(p3));
			
		} catch (SQLException e) {
			fail("Hay algún problema en el test: " + e.getMessage());
		}

		
		//Test persona que no existe
	}

	@Ignore
	public void testInsert() {
		//assertTrue("No ha dado error al insertar una persona null", dao.insert(null) == -1);
	}
}