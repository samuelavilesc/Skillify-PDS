## Autenticación de Estudiante

- **Identificador**: Autenticación de estudiante
- **Actores**: Estudiante
- **Precondición:** El estudiante debe estar registrado
- **Descripción**: Permite a los estudiante registrados iniciar sesión en la aplicación proporcionando su nombre de usuario y contraseña, accediendo así a su cuenta y continuando con su aprendizaje.

### Pasos:

1. El estudiante accede a la ventana de inicio de sesión.
2. El estudiante ingresa su nombre de usuario y contraseña en el formulario.
3. El sistema verifica que el estudiante ya está registrado.
4. Si las credenciales son correctas, el estudiante es redirigido a la pantalla principal de la aplicación.
### Flujo alternativo:
4a. Si la contraseña es incorrecta, el sistema muestra un mensaje de error.
-  Se proporciona un enlace para la recuperación de la contraseña en caso de olvido.

4b. Si algún campo está vacío, el sistema muestra un mensaje indicando que todos los campos son obligatorios.
