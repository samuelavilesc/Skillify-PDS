package com.pds.skillify.persistence;

import com.pds.skillify.model.Course;
import com.pds.skillify.model.Question;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;

import java.util.List;

public class AdapterCourse implements CourseDAO {
	private final EntityManagerFactory emf;

	public AdapterCourse(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	@Transactional
	public void registerCourse(Course course) {
		EntityManager em = emf.createEntityManager();
		for (Question question : course.getQuestions()) {
			question.setCourse(course);
		}
		em.getTransaction().begin();
		// 🔹 Asegurar que las preguntas tienen el curso asignado antes de persistirlas

		// 🔹 Guardamos primero el curso
		em.persist(course);

		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void deleteCourse(Course course) {
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
	public void updateCourse(Course course) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(course); // 🔹 Hibernate se encarga de actualizar también las preguntas
		em.getTransaction().commit();
		em.close();
	}

	@Override
	@Transactional
	public Course getCourseById(Long id) {
		EntityManager em = emf.createEntityManager();
		Course course = em
				.createQuery("SELECT c FROM Course c LEFT JOIN FETCH c.questions WHERE c.id = :id", Course.class)
				.setParameter("id", id).getSingleResult();
		em.close();
		return course;
	}

	@Override
	public List<Course> getAllCourses() {
		EntityManager em = emf.createEntityManager();
		List<Course> courses = em.createQuery("SELECT c FROM Course c LEFT JOIN FETCH c.questions", Course.class)
				.getResultList();
		em.close();
		return courses;
	}

	@Override
	public Course getCourseByName(String name) {
		EntityManager em = emf.createEntityManager();
		Course course = null;

		try {
			course = em.createQuery("SELECT c FROM Course c LEFT JOIN FETCH c.questions WHERE c.name = :name",
					Course.class).setParameter("name", name).getSingleResult();
		} catch (jakarta.persistence.NoResultException e) {
			// Si el curso no existe en la BD, devolvemos null
			course = null;
		} finally {
			em.close();
		}

		return course;
	}

}
