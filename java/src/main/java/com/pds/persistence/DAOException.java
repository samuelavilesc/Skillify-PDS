package com.pds.persistence;

/**
 * Excepción personalizada para manejar errores relacionados con las operaciones
 * de acceso a datos (DAO). Extiende {@link Exception}.
 */
@SuppressWarnings("serial")
public class DAOException extends Exception {

	/**
	 * Constructor que crea una nueva instancia de {@link DAOException} con un
	 * mensaje de error específico.
	 *
	 * @param mensaje El mensaje que describe el error.
	 */
	public DAOException(String mensaje) {
		super(mensaje);
	}
}