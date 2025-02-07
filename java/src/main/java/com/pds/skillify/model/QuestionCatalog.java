package com.pds.skillify.model;

import java.util.*;
import com.pds.persistence.DAOException;
import com.pds.persistence.FactoryDAO;
import com.pds.persistence.QuestionDAO;

public class QuestionCatalog {

    private Map<Long, Question> questions; // Mapa que asocia IDs con preguntas
    private static QuestionCatalog instance;
    private FactoryDAO factory;
    private QuestionDAO questionDAO;

    /**
     * Constructor privado que carga las preguntas desde la base de datos.
     */
    private QuestionCatalog() {
        try {
            factory = FactoryDAO.getInstance();
            questionDAO = factory.getQuestionDAO();
            questions = new HashMap<>();
            this.loadCatalog();
        } catch (DAOException e) {
            e.printStackTrace();
            questions = new HashMap<>();
        }
    }

    /**
     * Devuelve la instancia única del `QuestionCatalog`.
     *
     * @return Instancia única del catálogo de preguntas.
     */
    public static QuestionCatalog getInstance() {
        if (instance == null)
            instance = new QuestionCatalog();
        return instance;
    }

    /**
     * Obtiene todas las preguntas del catálogo.
     *
     * @return Lista de preguntas disponibles.
     */
    public List<Question> getQuestions() {
        return new ArrayList<>(questions.values());
    }

    /**
     * Obtiene una pregunta por ID.
     *
     * @param id Identificador único de la pregunta.
     * @return La pregunta correspondiente, o null si no existe.
     */
    public Question getQuestion(Long id) {
        return questions.get(id);
    }

    /**
     * Obtiene todas las preguntas de un curso específico.
     *
     * @param courseId Identificador del curso.
     * @return Lista de preguntas del curso.
     */
    public List<Question> getQuestionsByCourse(int courseId) {
        List<Question> courseQuestions = new ArrayList<>();
        for (Question q : questions.values()) {
            if (q.getCourse().getId() == courseId) {
                courseQuestions.add(q);
            }
        }
        return courseQuestions;
    }

    /**
     * Añade una pregunta al catálogo y la guarda en la base de datos.
     *
     * @param question Pregunta a añadir.
     */
    public void addQuestion(Question question) {
        questions.put(question.getId(), question);
        try {
            questionDAO.registerQuestion(question);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una pregunta del catálogo y de la base de datos.
     *
     * @param question Pregunta a eliminar.
     */
    public void removeQuestion(Question question) {
        questions.remove(question.getId());
        try {
            questionDAO.deleteQuestion(question);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga todas las preguntas desde la base de datos y las almacena en el catálogo.
     */
    private void loadCatalog() {
        List<Question> questionsFromDB = questionDAO.loadAllQuestions();
		for (Question question : questionsFromDB) {
		    questions.put(question.getId(), question);
		}
    }
}
