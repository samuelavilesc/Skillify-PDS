package com.pds.persistence;

import com.pds.skillify.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class AdapterCourse implements CourseDAO {
    private final EntityManagerFactory emf;

    public AdapterCourse(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void registrarGrupo(Course course) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(course);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void borrarGrupo(Course course) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Course courseToDelete = em.find(Course.class, course.getId());
        if (courseToDelete != null) {
            em.remove(courseToDelete);
        }
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void modificarGrupo(Course course) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(course); // Actualiza los datos del curso
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Course recuperarGrupo(Long id) {
        EntityManager em = emf.createEntityManager();
        Course course = em.find(Course.class, id);
        em.close();
        return course;
    }

    @Override
    public List<Course> recuperarTodosGrupos() {
        EntityManager em = emf.createEntityManager();
        List<Course> courses = em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
        em.close();
        return courses;
    }
}
