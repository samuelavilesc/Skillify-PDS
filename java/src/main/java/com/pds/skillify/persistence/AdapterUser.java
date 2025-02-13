package com.pds.skillify.persistence;

import com.pds.skillify.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;

import java.util.List;

public class AdapterUser implements UserDAO {
	private final EntityManagerFactory emf;

	public AdapterUser(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	@Transactional
	public void registerUser(User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void deleteUser(User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		User userToDelete = em.find(User.class, user.getId());
		if (userToDelete != null) {
			em.remove(userToDelete);
		}
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void modifyUser(User user) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(user); // Actualiza los datos del usuario
		em.getTransaction().commit();
		em.close();
	}

	@Override
	@Transactional
	public User loadUser(Long id) {
		EntityManager em = emf.createEntityManager();
		User user = null;
		try {
			user = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.answeredQuestions WHERE u.id = :id",
					User.class).setParameter("id", id).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return user;
	}

	@Override
	public List<User> loadAllUsers() {
		EntityManager em = emf.createEntityManager();
		List<User> users = em
				.createQuery("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.answeredQuestions", User.class)
				.getResultList();
		em.close();
		return users;
	}
}
