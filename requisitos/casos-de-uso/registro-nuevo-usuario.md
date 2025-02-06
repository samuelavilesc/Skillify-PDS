# Casos de Uso - Sistema de Gestión de la Aplicación

## 7. Registro de un Nuevo Usuario

- **Identificador**: Registro de un Nuevo Usuario  
- **Actores**: Usuario nuevo  
- **Descripción**: Permite a un usuario nuevo registrarse en la aplicación proporcionando su nombre de usuario, correo electrónico y contraseña para acceder a las funcionalidades de aprendizaje.  

### Pasos:  
1. El usuario accede a la ventana de registro.  
2. Ingresa un nombre de usuario, un correo electrónico y una contraseña.  
3. El sistema valida que el correo tenga un formato válido.  
4. El sistema verifica que la contraseña cumpla con los requisitos de seguridad (mínimo 8 caracteres).  
5. Si algún campo está vacío o no es válido, el sistema muestra un mensaje de error.  
6. Si los datos son correctos, el sistema verifica que el correo no esté en uso.  
7. Si el correo ya está registrado, se muestra un mensaje indicando que el usuario ya existe.  
8. Si los datos son válidos y el correo no está en uso, el usuario es registrado con éxito.  
9. El sistema redirige al usuario a la pantalla principal.  
