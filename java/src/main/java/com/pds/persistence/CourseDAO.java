package com.pds.persistence;

import java.util.List;

import com.pds.skillify.model.Course;

/**
 * Interfaz para la gestión de cursos en el sistema mediante el patrón DAO (Data
 * Access Object). Proporciona operaciones CRUD (Crear, Leer, Actualizar,
 * Eliminar) para objetos de tipo {@link Group}.
 */
public interface CourseDAO {

	/**
	 * Registra un curso en el sistema.
	 * 
	 * @param curso El curso a registrar.
	 */
	public void registrarGrupo(Course course);

	/**
	 * Elimina un curso del sistema.
	 * 
	 * @param curso El curso a eliminar.
	 */
	public void borrarGrupo(Course course);

	/**
	 * Modifica la información de un curso en el sistema.
	 * 
	 * @param curso El curso con la información actualizada.
	 */
	public void modificarGrupo(Course course);

	/**
	 * Recupera un curso del sistema a partir de su código único.
	 * 
	 * @param codigo El código único del curso.
	 * @return El curso correspondiente al código proporcionado.
	 */
	public Course recuperarGrupo(int codigo);

	/**
	 * Recupera todos los cursos registrados en el sistema.
	 * 
	 * @return Una lista de todos los cursos registrados.
	 */
	public List<Course> recuperarTodosGrupos();
}