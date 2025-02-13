package com.pds.skillify.persistence;

import java.util.List;
import com.pds.skillify.model.Course;

/**
 * Interfaz para la gestión de cursos en el sistema mediante el patrón DAO (Data
 * Access Object). Proporciona operaciones CRUD (Crear, Leer, Actualizar,
 * Eliminar) para objetos de tipo {@link Course}.
 */
public interface CourseDAO {

    /**
     * Registra un curso en el sistema.
     *
     * @param course El curso a registrar.
     */
    void registerCourse(Course course);

    /**
     * Elimina un curso del sistema.
     *
     * @param course El curso a eliminar.
     */
    void deleteCourse(Course course);

    /**
     * Modifica la información de un curso en el sistema.
     *
     * @param course El curso con la información actualizada.
     */
    void updateCourse(Course course);

    /**
     * Recupera un curso del sistema a partir de su código único.
     *
     * @param id El código único del curso.
     * @return El curso correspondiente al código proporcionado.
     */
    Course getCourseById(Long id);
    Course getCourseByName(String name);
    /**
     * Recupera todos los cursos registrados en el sistema.
     *
     * @return Una lista de todos los cursos registrados.
     */
    List<Course> getAllCourses();
}
