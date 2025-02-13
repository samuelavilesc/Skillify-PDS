package com.pds.skillify.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pds.skillify.model.Course;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

public class CourseJSONUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Carga un curso desde un archivo JSON.
	 * 
	 * Este método lee un archivo JSON que contiene la información de un curso y lo
	 * deserializa en un objeto {@code Course} utilizando {@code objectMapper}.
	 *
	 * @param filePath Ruta del archivo JSON del curso.
	 * @return Un objeto {@code Course} si la carga es exitosa, o {@code null} si
	 *         ocurre un error.
	 */
	public static Course loadCourseFromJson(String filePath) {
		try {
			return objectMapper.readValue(Files.newInputStream(Paths.get(filePath)), Course.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Valida la estructura de un archivo JSON de curso.
	 * 
	 * Este método verifica que el archivo JSON contenga los campos obligatorios
	 * para definir un curso correctamente, como el nombre, la descripción y al
	 * menos una pregunta válida.
	 * 
	 * <p>
	 * Se realizan las siguientes validaciones:
	 * </p>
	 * <ul>
	 * <li>Debe contener los campos "name" y "description" de tipo
	 * {@code String}.</li>
	 * <li>Debe contener un campo "questions" de tipo {@code JSONArray} con al menos
	 * un elemento.</li>
	 * <li>Cada pregunta debe tener los campos "type", "statement" y
	 * "correctAnswer".</li>
	 * <li>El tipo de pregunta determina validaciones adicionales:</li>
	 * <ul>
	 * <li>Para preguntas de opción múltiple, "options" debe ser un
	 * {@code JSONArray} no vacío y "correctAnswer" debe ser un índice válido.</li>
	 * <li>Para preguntas de completar huecos o reordenar letras, "correctAnswer"
	 * debe ser un {@code String}.</li>
	 * </ul>
	 * </ul>
	 * 
	 * @param filePath Ruta del archivo JSON a validar.
	 * @return {@code true} si el JSON es válido, {@code false} si falta algún campo
	 *         o hay inconsistencias.
	 * @throws IOException Si hay un error al leer el archivo.
	 */
	public static boolean validateCourseJSON(String filePath) throws IOException {
		String jsonString = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
		JSONObject course = new JSONObject(jsonString);

		if (!course.has("name") || !course.has("description")) {
			return false;
		}

		if (!(course.get("name") instanceof String) || !(course.get("description") instanceof String)) {
			return false;
		}

		if (!course.has("questions") || !(course.get("questions") instanceof JSONArray)) {
			return false;
		}

		JSONArray questions = course.getJSONArray("questions");
		if (questions.isEmpty()) {
			return false;
		}

		// Validar cada pregunta
		for (int i = 0; i < questions.length(); i++) {
			JSONObject question = questions.getJSONObject(i);

			if (!question.has("type") || !question.has("statement") || !question.has("correctAnswer")) {
				return false;
			}

			if (!(question.get("type") instanceof String) || !(question.get("statement") instanceof String)) {
				return false;
			}

			String type = question.getString("type");

			switch (type) {
			case "multiple_choice":
				if (!question.has("options") || !(question.get("options") instanceof JSONArray)) {
					return false;
				}
				JSONArray options = question.getJSONArray("options");
				if (options.isEmpty()) {
					return false;
				}
				if (!(question.get("correctAnswer") instanceof Integer) || question.getInt("correctAnswer") < 0
						|| question.getInt("correctAnswer") >= options.length()) {
					return false;
				}
				break;

			case "fill_in_blank":
			case "reorder_letters":
				if (!(question.get("correctAnswer") instanceof String)) {
					return false;
				}
				break;

			default:
				return false;
			}
		}

		return true;
	}

}
