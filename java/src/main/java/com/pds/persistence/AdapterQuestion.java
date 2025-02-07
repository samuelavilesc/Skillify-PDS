package com.pds.persistence;

import com.pds.skillify.model.Question;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class AdapterQuestion implements QuestionDAO {
    private final EntityManagerFactory emf;

    public AdapterQuestion(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void registerQuestion(Question question) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(question);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteQuestion(Question question) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Question questionToDelete = em.find(Question.class, question.getId());
        if (questionToDelete != null) {
            em.remove(questionToDelete);
        }
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void modifyQuestion(Question question) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(question); // Actualiza los datos de la pregunta
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Question loadQuestion(Long codigo) {
        EntityManager em = emf.createEntityManager();
        Question question = em.find(Question.class, codigo);
        em.close();
        return question;
    }

    @Override
    public List<Question> loadAllQuestions() {
        EntityManager em = emf.createEntityManager();
        List<Question> questions = em.createQuery("SELECT q FROM Question q", Question.class).getResultList();
        em.close();
        return questions;
    }

    @Override
    public List<Question> loadQuestionsByCourse(int courseId) {
        EntityManager em = emf.createEntityManager();
        List<Question> questions = em.createQuery(
                "SELECT q FROM Question q WHERE q.course.id = :courseId", Question.class)
                .setParameter("courseId", courseId)
                .getResultList();
        em.close();
        return questions;
    }
}
