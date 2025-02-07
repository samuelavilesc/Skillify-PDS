package com.pds.persistence;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Implementación concreta de la factoría DAO.
 */
public class FactoryDAOImp extends FactoryDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("SkillifyPU");

    @Override
    public UserDAO getUserDAO() {
        return new AdapterUser(emf);
    }

    @Override
    public CourseDAO getCourseDAO() {
        return new AdapterCourse(emf);
    }

    @Override
    public QuestionDAO getQuestionDAO() {
        return new AdapterQuestion(emf);
    }
}
