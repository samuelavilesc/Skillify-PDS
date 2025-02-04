## Carga de Contenido desde JSON

Como **usuario autenticado**, quiero poder **importar contenido en formato JSON**, para agregar nuevos temas de aprendizaje.

### Criterios de Aceptación:

Dado que me encuentro en la ventana principal,  
cuando presiono en el botón actualizar, se recarga  
la lista de cursos disponibles cargando el nuevo introducido.  
Si el formato del nuevo curso es correcto se cargará correctamente  
entonces podré estudiar la lección.

### Checklist de ayuda al desarrollo

- [ ] El usuario puede cargar un archivo JSON desde su equipo.
- [ ] El sistema valida la estructura del archivo antes de importarlo.
- [ ] Si la carga es exitosa, los nuevos temas aparecen en la lista.
- [ ] Si hay errores en el JSON, se muestra un mensaje de error.
