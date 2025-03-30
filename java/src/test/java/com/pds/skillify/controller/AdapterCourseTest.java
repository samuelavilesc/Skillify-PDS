package com.pds.skillify.controller;

import com.pds.skillify.model.Course;
import com.pds.skillify.model.Question;
import com.pds.skillify.persistence.AdapterCourse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class AdapterCourseTest extends AdapterCourse {

    public AdapterCourseTest(EntityManagerFactory emf) {
        super(emf);
    }
@Override
    // Sobrescribimos registerCourse para usar merge() en lugar de persist()

public void registerCourse(Course course) {
    EntityManager em = getEmf().createEntityManager();
    try {
        for (Question question : course.getQuestions()) {
            question.setCourse(course);
        }
        em.getTransaction().begin();
        Course managedCourse = em.merge(course);
        em.getTransaction().commit();
        course.setId(managedCourse.getId()); // 💡 Aquí actualizamos el ID
    } finally {
        em.close();
    }
}



}
