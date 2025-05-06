<p align="center">
  <img src="https://github.com/user-attachments/assets/80a4ef64-9bee-4656-b25f-a0a03b0ba80d" width="300">
</p>

## Descripción
Skillify es una aplicación de aprendizaje desarrollada en Java como parte de la asignatura **Procesos de Desarrollo de Software** del **Grado en Ingeniería Informática** de la **Universidad de Murcia**, guiado por el profesor **Jesús Sánchez Cuadrado**. 

El objetivo de Skillify es proporcionar una plataforma interactiva que facilite el aprendizaje de diversas habilidades.

La apliación integra diversas funcionalidades descritas en los diferentes [casos de uso](./requisitos/casos-de-uso/readme.md) y en el [modelo de dominio](./diseño/modelo/modelo.png).
Podriamos desglosarlas en:
### Funcionalidad Obligatoria:

- Realizar un curso – Es el corazón de la aplicación.
- Ver su progreso en un curso – El enunciado exige poder guardar y reanudar el estado del curso.
- Ver sus logros y estadísticas accediendo a la ventana de logros – Se exige guardar estadísticas como tiempo de uso, mejor racha, etc.
- Tres tipos de preguntas: rellenar el hueco, ordenar letras y tipo test.
- Elegir estrategias de aprendizaje: Aleatorio, secuencial y repetición.
### Funcionalidad Opcional:

- Registrarse – No es obligatorio; el sistema podría funcionar sin cuentas de usuario.
- Iniciar sesión – Igual que arriba.
- Cambiar su foto de perfil
- Actualizar los datos de su perfil (contraseña y foto)
- Recuperar su cuenta en caso de olvido de contraseña
- Obtener logros por completar cursos – No se exige que existan “logros”, solo estadísticas.
- Consultar logros de otros usuarios a través de la ventana de comunidad.
- Resetear progreso de un curso.

## Instrucciones de ejecución
Para ejecutar la aplicación, basta con cargar el proyecto en eclipse (carpeta java) y ejecutar la clase main (Skillify.java)
Tambien se puede hacer uso del **fichero ejecutable .jar.**

Para **probar la aplicación**, se adjuntan ciertos cursos ya creados que se pueden encontrar en:  _java/src/main/resources/courses_

## Integrantes del equipo
- **Samuel Avilés Conesa**
- **Gloria Sánchez Alonso**
- **Mikael Henrique Krüppel Pinto**

## Tecnologías utilizadas
- **Lenguaje**: Java
- **Gestión de dependencias**: Maven
- **Base de datos**: JPA (sqlite)
- **Interfaz gráfica**: Swing

## Licencia
Este proyecto se desarrolla únicamente con fines académicos.
