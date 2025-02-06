package com.pds.persistence;

/**
 * Clase abstracta que implementa el patrón Factory para proporcionar instancias
 * de adaptadores DAO (Data Access Object) específicos.
 */
public abstract class FactoriaDAO {
	private static FactoriaDAO unicaInstancia = null;

	/** Nombre del tipo de fábrica para la implementación TDS. */
	public static final String DAO_TDS = "com.pds.persistence.FactoriaDAO";

	/**
	 * Crea una instancia de la fábrica DAO del tipo especificado.
	 * 
	 * @param tipo El nombre completo de la clase de la fábrica DAO a instanciar.
	 * @return Una instancia única de {@link FactoriaDAO}.
	 * @throws DAOException Si ocurre un error al intentar instanciar la fábrica.
	 */
	public static FactoriaDAO getInstance(String tipo) throws DAOException {
		if (unicaInstancia == null) {
			try {
				unicaInstancia = (FactoriaDAO) Class.forName(tipo).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new DAOException(e.getMessage());
			}
		}
		return unicaInstancia;
	}

	/**
	 * Obtiene la instancia única de la fábrica DAO. Si no existe, se crea
	 * utilizando la implementación predeterminada {@link #DAO_TDS}.
	 * 
	 * @return Una instancia única de {@link FactoriaDAO}.
	 * @throws DAOException Si ocurre un error al intentar crear la instancia.
	 */
	public static FactoriaDAO getInstance() throws DAOException {
		if (unicaInstancia == null) {
			return getInstance(FactoriaDAO.DAO_TDS);
		} else {
			return unicaInstancia;
		}
	}

	/**
	 * Constructor protegido para evitar instanciación directa.
	 */
	protected FactoriaDAO() {
	}

	// Métodos abstractos que deben ser implementados por las subclases concretas.


	/**
	 * Obtiene un adaptador DAO para la gestión de usuarios.
	 * 
	 * @return Una implementación de {@link UserDAO}.
	 */
	public abstract UserDAO getUserDAO();


	/**
	 * Obtiene un adaptador DAO para la gestión de cursos.
	 * 
	 * @return Una implementación de {@link GroupDAO}.
	 */
	public abstract CourseDAO getCourseDAO();
}