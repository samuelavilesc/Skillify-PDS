# Registro de un Nuevo Usuario

- **Identificador**: Registro de un Nuevo Usuario
- **Actores principales**: Candidato a Estudiante / Creador de cursos  
- **Descripción**: Permite a un nuevo usuario registrarse en la aplicación proporcionando su nombre de usuario, correo electrónico y contraseña.

---

##  Flujo principal (éxito)

1. El usuario accede a la ventana de registro.  
2. Introduce un nombre de usuario, correo electrónico y contraseña.  
3. El sistema valida que el correo electrónico tenga un formato válido.  
4. El sistema verifica que la contraseña cumpla con los requisitos de seguridad (mínimo 8 caracteres, etc.).  
5. El sistema comprueba que el correo electrónico no esté ya registrado.  
6. El sistema registra al nuevo usuario con éxito.  
7. El sistema redirige al usuario a la pantalla principal de la aplicación.

---

##  Flujos alternativos

###Formato de correo no válido
- **3a.** Si el correo electrónico no tiene un formato válido, el sistema muestra un mensaje de error y solicita corrección.  
- **3b.** El usuario vuelve al paso 2.

### Contraseña no segura
- **4a.** Si la contraseña no cumple con los requisitos de seguridad, el sistema muestra un mensaje de advertencia.  
- **4b.** El usuario vuelve al paso 2.

### Campos vacíos
- **2a.** Si alguno de los campos está vacío, el sistema muestra un mensaje de error indicando que todos los campos son obligatorios.  
- **2b.** El usuario vuelve al paso 2.

### Correo ya registrado
- **5a.** Si el correo electrónico ya está en uso, el sistema muestra un mensaje indicando que el usuario ya existe.  
- **5b.** El usuario vuelve al paso 2.

---
