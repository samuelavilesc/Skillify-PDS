package com.pds.skillify.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pds.persistence.DAOException;
import com.pds.persistence.FactoriaDAO;
import com.pds.persistence.UserDAO;

public class UserCatalog {

	private Map<String, User> users; // Mapa que relaciona números de teléfono con usuarios.
	private static UserCatalog unicaInstancia;
	private FactoriaDAO factoria;
	private UserDAO adaptadorUsuario;

	/**
	 * Constructor privado que inicializa la instancia única del catálogo. Carga los
	 * usuarios desde la base de datos utilizando el adaptador DAO.
	 */
	private UserCatalog() {
		try {
			factoria = FactoriaDAO.getInstance(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = factoria.getUserDAO();
			users = new HashMap<>();
			this.loadCatalog();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}

	/**
	 * Devuelve la instancia única del catálogo de usuarios.
	 *
	 * @return Instancia única de `UserCatalog`.
	 */
	public static UserCatalog getInstance() {
		if (unicaInstancia == null)
			unicaInstancia = new UserCatalog();
		return unicaInstancia;
	}

	/**
	 * Obtiene una lista de todos los usuarios en el catálogo.
	 *
	 * @return Lista de usuarios.
	 */
	public List<User> getUsers() {
		return new ArrayList<User>(users.values());
	}

	/**
	 * Busca un usuario por su número de teléfono.
	 *
	 * @param telefono Número de teléfono del usuario.
	 * @return El usuario correspondiente al número de teléfono, o null si no
	 *         existe.
	 */
	public User getUser(String username) {
		return users.values().stream().filter(u -> u.getUsername().equals(username)).findAny().orElse(null);
	}

	/**
	 * Busca un usuario por su código único.
	 *
	 * @param codigo Código único del usuario.
	 * @return El usuario correspondiente al código, o null si no existe.
	 */
	public User getUser(int codigo) {
		return users.values().stream().filter(u -> u.getCodigo() == codigo).findAny().orElse(null);
	}

	/**
	 * Busca un usuario por su número de teléfono, devolviendo un objeto `Optional`.
	 *
	 * @param tlf Número de teléfono del usuario.
	 * @return Un `Optional` que contiene el usuario si existe, o vacío si no.
	 */
	public Optional<User> findUser(String username) {
		return users.values().stream().filter(u -> u.getUsername().equals(username)).findAny();
	}

	/**
	 * Añade un usuario al catálogo.
	 *
	 * @param user Usuario a añadir.
	 */
	public void addUser(User user) {
		users.put(user.getEmail(), user);
	}

	/**
	 * Elimina un usuario del catálogo.
	 *
	 * @param user Usuario a eliminar.
	 */
	public void removeUser(User user) {
		users.remove(user.getEmail());
	}

	/**
	 * Verifica si un usuario existe en el catálogo.
	 *
	 * @param user Usuario a verificar.
	 * @return true si el usuario existe, false de lo contrario.
	 */
	public boolean existsUser(User user) {
		return users.containsValue(user);
	}

	/**
	 * Carga todos los usuarios desde la base de datos y los almacena en el
	 * catálogo.
	 *
	 * @throws DAOException Si ocurre un error al interactuar con la capa de
	 *                      persistencia.
	 */
	private void loadCatalog() throws DAOException {
		List<User> usuariosBD = adaptadorUsuario.loadAllUsers();
		for (User user : usuariosBD) {
			users.put(user.getEmail(), user);
		}
	}
}