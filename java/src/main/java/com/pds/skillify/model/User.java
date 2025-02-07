package com.pds.skillify.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;

import javax.swing.ImageIcon;
import com.pds.skillify.utils.ImageUtils;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // Guardamos la imagen como bytes en la BD
    private byte[] profilePic;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Mapa que almacena el conjunto de preguntas respondidas por curso.
     */
    @ElementCollection
    @CollectionTable(name = "user_answered_questions", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyJoinColumn(name = "course_id")
    @Column(name = "question_id")
    private Map<Course, Set<Question>> answeredQuestions = new HashMap<>();

    // Constructor vacío requerido por JPA
    public User() {}

    // Constructor para registro
    public User(String username, String password, String email, ImageIcon profilePic) {
        this.username = username;
        this.password = password;
        this.email = email;
        setProfilePic(profilePic); // Convierte la imagen a bytes
    }

    // 🔹 Métodos para la gestión de cursos y progreso

    /**
     * Añade un curso al usuario si aún no está registrado.
     * @param course Curso a agregar.
     */
    public void addCourse(Course course) {
        answeredQuestions.putIfAbsent(course, new HashSet<>());
    }

    /**
     * Verifica si el usuario ya tiene un curso registrado.
     * @param course Curso a verificar.
     * @return true si el usuario ya tiene el curso, false en caso contrario.
     */
    public boolean alreadyHasCourse(Course course) {
        return answeredQuestions.containsKey(course);
    }

    /**
     * Calcula el progreso del usuario en un curso basado en las preguntas respondidas.
     * @param course Curso en el que se quiere conocer el progreso.
     * @return Progreso en porcentaje (0-100).
     */
    public int getCourseProgress(Course course) {
        if (!answeredQuestions.containsKey(course) || course.getQuestions().isEmpty()) {
            return 0;
        }
        int answeredCount = answeredQuestions.get(course).size();
        int totalQuestions = course.getQuestions().size();
        return (int) ((answeredCount / (double) totalQuestions) * 100);
    }

    /**
     * Obtiene todos los cursos en los que el usuario ha respondido preguntas.
     * @return Set de cursos.
     */
    public Set<Course> getAllCourses() {
        return answeredQuestions.keySet();
    }

    /**
     * Registra una pregunta como respondida en un curso.
     * @param course Curso en el que se respondió la pregunta.
     * @param question Pregunta respondida.
     */
    public void addAnsweredQuestion(Course course, Question question) {
        answeredQuestions.computeIfAbsent(course, k -> new HashSet<>()).add(question);
    }

    /**
     * Verifica si el usuario ha respondido una pregunta en un curso.
     * @param course Curso en el que se encuentra la pregunta.
     * @param question Pregunta a verificar.
     * @return true si la pregunta fue respondida, false en caso contrario.
     */
    public boolean hasAnsweredQuestion(Course course, Question question) {
        return answeredQuestions.getOrDefault(course, Collections.emptySet()).contains(question);
    }

    /**
     * Obtiene todas las preguntas respondidas en un curso específico.
     * @param course Curso en el que se buscan las preguntas respondidas.
     * @return Set de preguntas respondidas.
     */
    public Set<Question> getAnsweredQuestionsInCourse(Course course) {
        return answeredQuestions.getOrDefault(course, Collections.emptySet());
    }

    // 🔹 Métodos para la gestión de la imagen de perfil
    public ImageIcon getProfilePic() {
        return ImageUtils.bytesToImageIcon(profilePic);
    }

    public void setProfilePic(ImageIcon profilePic) {
        this.profilePic = ImageUtils.imageIconToBytes(profilePic);
    }

    // 🔹 Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
