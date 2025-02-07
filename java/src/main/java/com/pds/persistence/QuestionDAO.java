package com.pds.persistence;

import com.pds.skillify.model.Question;
import java.util.List;

/**
 * Interfaz DAO para la gestión de objetos {@link Question}. 
 * Proporciona métodos para realizar operaciones CRUD 
 * (crear, leer, actualizar, eliminar) y recuperar preguntas almacenadas.
 */
public interface QuestionDAO {

    /**
     * Registra una nueva pregunta en la base de datos.
     *
     * @param question La pregunta que se va a registrar.
     */
    void registerQuestion(Question question);

    /**
     * Elimina una pregunta existente de la base de datos.
     *
     * @param question La pregunta que se va a eliminar.
     */
    void deleteQuestion(Question question);

    /**
     * Modifica los datos de una pregunta existente en la base de datos.
     *
     * @param question La pregunta con los datos actualizados.
     */
    void modifyQuestion(Question question);

    /**
     * Recupera una pregunta de la base de datos a partir de su id.
     *
     * @param id El código único de la pregunta.
     * @return La pregunta correspondiente al id, o {@code null} si no se encuentra.
     */
    Question loadQuestion(Long id);

    /**
     * Recupera todas las preguntas almacenadas en la base de datos.
     *
     * @return Una lista de todas las preguntas registradas.
     */
    List<Question> loadAllQuestions();

    /**
     * Recupera todas las preguntas asociadas a un curso específico.
     *
     * @param courseId El ID del curso.
     * @return Una lista de preguntas pertenecientes al curso.
     */
    List<Question> loadQuestionsByCourse(int courseId);
}
