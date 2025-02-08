package com.pds.skillify.model;

import java.util.*;
import com.pds.persistence.CourseDAO;
import com.pds.persistence.DAOException;
import com.pds.persistence.FactoryDAO;

public class CourseCatalog {

    private Map<String, Course> courses; // Mapa que asocia nombres de cursos con objetos Course
    private static CourseCatalog instance;
    private FactoryDAO factory;
    private CourseDAO courseDAO;

    /**
     * Constructor privado para el patrón Singleton.
     * Carga los cursos desde la base de datos.
     */
    private CourseCatalog() {
        try {
            factory = FactoryDAO.getInstance();
            courseDAO = factory.getCourseDAO();
            courses = new HashMap<>();
            this.loadCatalog();
        } catch (DAOException e) {
            e.printStackTrace();
            courses = new HashMap<>();
        }
    }

    /**
     * Devuelve la única instancia del catálogo de cursos.
     *
     * @return Instancia única de `CourseCatalog`.
     */
    public static CourseCatalog getInstance() {
        if (instance == null)
            instance = new CourseCatalog();
        return instance;
    }

    /**
     * Obtiene todos los cursos del catálogo.
     *
     * @return Lista de cursos disponibles.
     */
    public List<Course> getCourses() {
        return new ArrayList<>(courses.values());
    }

    /**
     * Busca un curso por nombre.
     *
     * @param name Nombre del curso.
     * @return El curso correspondiente, o null si no existe.
     */
    public Course getCourse(String name) {
        return courses.get(name);
    }

    /**
     * Busca un curso por ID.
     *
     * @param id Identificador único del curso.
     * @return El curso correspondiente, o null si no existe.
     */
    public Course getCourse(Long id) {
        return courses.values().stream()
                .filter(c -> c.getId() == id)
                .findAny()
                .orElse(null);
    }

    /**
     * Añade un curso al catálogo y lo guarda en la base de datos.
     *
     * @param course Curso a añadir.
     */
    public void addCourse(Course course) {
        // Verificar si el curso ya existe en la BD por nombre (o ID si aplica)
        Course existingCourse = courseDAO.getCourseByName(course.getName());

        if (existingCourse == null) { 
            // Si el curso no existe, lo guardamos en la BD y en el catálogo
            courses.put(course.getName(), course);
            try {
                courseDAO.registerCourse(course);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Si el curso ya existe, lo añadimos al catálogo sin registrarlo nuevamente
            courses.put(existingCourse.getName(), existingCourse);
        }
    }


    /**
     * Elimina un curso del catálogo y de la base de datos.
     *
     * @param course Curso a eliminar.
     */
    public void removeCourse(Course course) {
        courses.remove(course.getName());
        try {
            courseDAO.deleteCourse(course);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga todos los cursos desde la base de datos y los almacena en el catálogo.
     */
    private void loadCatalog() {
        List<Course> coursesFromDB = courseDAO.getAllCourses();
		for (Course course : coursesFromDB) {
		    courses.put(course.getName(), course);
		}
    }
}
