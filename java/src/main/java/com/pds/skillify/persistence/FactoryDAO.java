package com.pds.skillify.persistence;

/**
 * Clase abstracta que implementa el patrón Factory para proporcionar instancias
 * de adaptadores DAO (Data Access Object) específicos.
 */
public abstract class FactoryDAO {
	private static FactoryDAO unicaInstancia = null;

	/** Nombre del tipo de fábrica para la implementación. */
	public static final String DAO_TDS = "com.pds.skillify.persistence.FactoryDAOImp";

	/**
	 * Crea una instancia de la fábrica DAO del tipo especificado.
	 *
	 * @param tipo El nombre completo de la clase de la fábrica DAO a instanciar.
	 * @return Una instancia única de {@link FactoryDAO}.
	 * @throws DAOException Si ocurre un error al intentar instanciar la fábrica.
	 */
	public static FactoryDAO getInstance(String tipo) throws DAOException {
		if (unicaInstancia == null) {
			try {
				unicaInstancia = (FactoryDAO) Class.forName(tipo).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new DAOException(e.getMessage());
			}
		}
		return unicaInstancia;
	}

	/**
	 * Obtiene la instancia única de la fábrica DAO.
	 *
	 * @return Una instancia única de {@link FactoryDAO}.
	 * @throws DAOException Si ocurre un error al intentar crear la instancia.
	 */
	public static FactoryDAO getInstance() throws DAOException {
		if (unicaInstancia == null) {
			return getInstance(FactoryDAO.DAO_TDS);
		} else {
			return unicaInstancia;
		}
	}

	/**
	 * Constructor protegido para evitar instanciación directa.
	 */
	protected FactoryDAO() {
	}

	// Métodos abstractos que deben ser implementados por las subclases concretas.

	public abstract UserDAO getUserDAO();

	public abstract CourseDAO getCourseDAO();

}
