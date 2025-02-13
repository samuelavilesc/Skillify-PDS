package com.pds.skillify.persistence;

import java.util.List;

import com.pds.skillify.model.User;

/**
 * Interfaz DAO para la gestión de objetos {@link User}. Proporciona métodos
 * para realizar operaciones CRUD (crear, leer, actualizar, eliminar) y
 * recuperar usuarios almacenados.
 */
public interface UserDAO {

	/**
	 * Registra un nuevo usuario en la base de datos.
	 * 
	 * @param user El usuario que se va a registrar.
	 */
	public void registerUser(User user);

	/**
	 * Elimina un usuario existente de la base de datos.
	 * 
	 * @param user El usuario que se va a eliminar.
	 */
	public void deleteUser(User user);

	/**
	 * Modifica los datos de un usuario existente en la base de datos.
	 * 
	 * @param user El usuario con los datos actualizados.
	 */
	public void modifyUser(User user);

	/**
	 * Recupera un usuario de la base de datos a partir de su código.
	 * 
	 * @param codigo El código único del usuario.
	 * @return El usuario correspondiente al código, o {@code null} si no se
	 *         encuentra.
	 */
	public User loadUser(Long id);

	/**
	 * Recupera todos los usuarios almacenados en la base de datos.
	 * 
	 * @return Una lista de todos los usuarios registrados.
	 */
	public List<User> loadAllUsers();
}