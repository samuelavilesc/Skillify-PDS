package com.pds.skillify.model;

import java.util.*;
import java.util.stream.Collectors;

import com.pds.skillify.persistence.DAOException;
import com.pds.skillify.persistence.FactoryDAO;
import com.pds.skillify.persistence.UserDAO;

public class UserCatalog {

    private Map<String, User> users; // Mapa que asocia correos electrónicos con usuarios
    private static UserCatalog instance;
    private FactoryDAO factory;
    private UserDAO userDAO;

    /**
     * Constructor privado que inicializa la instancia única del catálogo.
     * Carga los usuarios desde la base de datos utilizando el DAO.
     */
    private UserCatalog() {
        try {
            factory = FactoryDAO.getInstance();
            userDAO = factory.getUserDAO();
            users = new HashMap<>();
            this.loadCatalog(); // Cargar usuarios desde la base de datos
        } catch (DAOException eDAO) {
            eDAO.printStackTrace();
            users = new HashMap<>();
        }
    }

    /**
     * Devuelve la instancia única del `UserCatalog`.
     *
     * @return Instancia única del catálogo de usuarios.
     */
    public static UserCatalog getInstance() {
        if (instance == null)
            instance = new UserCatalog();
        return instance;
    }

    /**
     * Obtiene una lista de todos los usuarios en el catálogo.
     *
     * @return Lista de usuarios.
     */
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario.
     * @return El usuario correspondiente, o null si no existe.
     */
    public User getUser(String username) {
        return users.values().stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findAny()
                    .orElse(null);
    }
    
    public Set<User> getUsersStartingWith(String prefix) {
        return users.values().stream()
                    .filter(u -> u.getUsername().startsWith(prefix))
                    .collect(Collectors.toSet());
    }

    /**
     * Busca un usuario por su identificador único.
     *
     * @param id Identificador único del usuario.
     * @return El usuario correspondiente, o null si no existe.
     */
    public User getUser(Long id) {
        return users.values().stream()
                    .filter(u -> u.getId() == id)
                    .findAny()
                    .orElse(null);
    }

    /**
     * Agrega un usuario al catálogo y lo guarda en la base de datos.
     *
     * @param user Usuario a agregar.
     */
    public void addUser(User user) {
        users.put(user.getEmail(), user);
        try {
            userDAO.registerUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un usuario del catálogo y lo borra de la base de datos.
     *
     * @param user Usuario a eliminar.
     */
    public void removeUser(User user) {
        users.remove(user.getEmail());
        try {
            userDAO.deleteUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica si un usuario existe en el catálogo.
     *
     * @param user Usuario a verificar.
     * @return true si el usuario existe, false de lo contrario.
     */
    public boolean existsUser(User user) {
        return users.containsValue(user);
    }

    /**
     * Carga todos los usuarios desde la base de datos y los almacena en el catálogo.
     *
     * @throws DAOException Si ocurre un error al interactuar con la capa de persistencia.
     */
    private void loadCatalog() throws DAOException {
        List<User> usersFromDB = userDAO.loadAllUsers();
        for (User user : usersFromDB) {
            users.put(user.getEmail(), user);
        }
    }
    /**
     * Actualiza los datos de un usuario en el catálogo y en la base de datos.
     *
     * @param user Usuario con los datos actualizados.
     */
    public void updateUser(User user) {
        if (users.containsKey(user.getEmail())) {
            users.put(user.getEmail(), user); // Actualizar el usuario en el catálogo
            try {
                userDAO.modifyUser(user); // Actualizar el usuario en la base de datos
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Error: El usuario no existe en el catálogo.");
        }
    }

}
