# Casos de Uso - Sistema de Gestión de la Aplicación

## 8. Selección de Tema de Aprendizaje

- **Identificador**: Selección de Tema de Aprendizaje  
- **Actores**: Usuario autenticado  
- **Descripción**: Permite al usuario seleccionar un tema de aprendizaje de una lista disponible para estudiar el contenido que le interesa.  

### Pasos:  
1. El usuario accede a la ventana principal de la aplicación.  
2. Se muestra una lista de temas de aprendizaje disponibles.  
3. El usuario selecciona un tema de la lista.  
4. El sistema verifica si hay contenido disponible.  
5. Si el tema tiene contenido, el usuario accede a la lección correspondiente.  
6. Si no hay temas cargados, el sistema muestra un mensaje indicando que debe añadir uno.  

### Checklist de ayuda al desarrollo  
- [ ] El usuario puede ver una lista de temas disponibles.  
- [ ] Puede seleccionar un tema y comenzar la lección.  
- [ ] Si no hay temas cargados, se muestra un mensaje indicando que debe añadir uno.  
- [ ] La lista de temas se obtiene desde un archivo JSON.  
