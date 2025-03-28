# Caso de Uso: Recuperar contraseña

- **Identificador**: RecuperarContrasena  
- **Actor principal**: Estudiante
- **Descripción**: Permite a un estudiante que ha olvidado su contraseña restablecerla.

---

## Flujo principal

1. El usuario accede a la pantalla de inicio de sesión.  
2. Selecciona la opción "¿Has olvidado tu contraseña?".  
3. El sistema muestra un formulario para introducir su correo electrónico.  
4. El usuario introduce su correo electrónico registrado.  
5. El sistema valida el formato del correo y verifica si está registrado.  
6. El sistema pide una nueva contraseña al usuario.  
7. El usuario introduce una nueva contraseña.  
8. El sistema actualiza la contraseña en la base de datos.  
9. El sistema muestra un mensaje de confirmación y redirige al inicio de sesión.

---

## Flujos alternativos

### A1. Formato de correo inválido
- **4a.** Si el correo introducido no tiene un formato válido, el sistema muestra un mensaje de error.  
- **4b.** El usuario vuelve al paso 3.

### A2. Correo no registrado
- **5a.** Si el correo no está registrado, el sistema informa al usuario que no se ha encontrado ninguna cuenta asociada.  
- **5b.** El usuario puede volver a intentarlo o registrarse.

---
