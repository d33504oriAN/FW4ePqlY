// 代码生成时间: 2025-09-18 19:06:55
package com.example.security;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.QuarkusApplicationLifecycle;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.example.model.User;
import java.util.List;
import java.util.Optional;

/**
 * This class demonstrates a basic example of preventing SQL injection in a Quarkus application.
 * It shows how to interact with the database in a safe manner using parameterized queries.
 */
@ApplicationScoped
public class SqlInjectionPrevention {

    @Inject
    QuarkusApplicationLifecycle lifecycle; // For demonstration purposes
    
    @PersistenceContext
    EntityManager em;
    
    /**
     * Retrieves a list of users from the database.
     * @param username The username to search for.
     * @return A list of users matching the username.
     */
    @Transactional(TxType.REQUIRED)
    public List<User> findUsersByUsername(String username) {
        // Use a parameterized query to prevent SQL injection
        String hql = "SELECT u FROM User u WHERE u.username = :username";
        Query<User> query = em.createQuery(hql, User.class).setParameter("username", username);
        return query.getResultList();
    }

    /**
     * Retrieves a user by ID.
     * @param id The ID of the user to retrieve.
     * @return An optional user object.
     */
    @Transactional(TxType.REQUIRED)
    public Optional<User> findUserById(Long id) {
        return em.find(User.class, id);
    }

    /**
     * Creates a new user in the database.
     * @param user The user object to be created.
     */
    @Transactional(TxType.REQUIRES_NEW)
    public void createUser(User user) {
        em.persist(user);
    }

    /**
     * Updates an existing user in the database.
     * @param user The user object with updated information.
     */
    @Transactional(TxType.REQUIRES_NEW)
    public void updateUser(User user) {
        em.merge(user);
    }

    /**
     * Deletes a user from the database.
     * @param id The ID of the user to delete.
     */
    @Transactional(TxType.REQUIRES_NEW)
    public void deleteUser(Long id) {
        User userToDelete = em.find(User.class, id);
        if (userToDelete != null) {
            em.remove(userToDelete);
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
    }

    /**
     * Starts the Quarkus application lifecycle.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        QuarkusApplication app = new QuarkusApplication(() -> new SqlInjectionPrevention());
        app.run(args);
    }
}
