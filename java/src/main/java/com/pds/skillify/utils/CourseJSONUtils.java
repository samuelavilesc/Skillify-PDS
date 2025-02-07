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

	public static Course loadCourseFromJson(String filePath) {
		try {
			return objectMapper.readValue(Files.newInputStream(Paths.get(filePath)), Course.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean validateQuizJSON(String filePath) throws IOException {
		// Leer el contenido del archivo
		String jsonString = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
		JSONObject course = new JSONObject(jsonString);

		// Verificar que existan 'name' y 'description'
		if (!course.has("name") || !course.has("description")) {
			return false;
		}

		// Verificar que 'name' y 'description' sean cadenas
		if (!(course.get("name") instanceof String) || !(course.get("description") instanceof String)) {
			return false;
		}

		// Verificar que 'questions' sea un array y no esté vacío
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

			// Verificar que 'type' y 'statement' sean cadenas
			if (!(question.get("type") instanceof String) || !(question.get("statement") instanceof String)) {
				return false;
			}

			// Validar según el tipo de pregunta
			String type = question.getString("type");

			switch (type) {
			case "multiple_choice":
				// Verificar que haya opciones y que correctAnswer sea un índice válido
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
				// Verificar que correctAnswer sea una cadena
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
