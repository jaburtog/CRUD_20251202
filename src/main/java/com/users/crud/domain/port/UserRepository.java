package com.users.crud.domain.port;

import com.users.crud.domain.entity.User;
import java.util.List;
import java.util.Optional;

/**
 * Repository port interface defining the contract for user data access.
 * This is part of the domain layer and follows the Dependency Inversion Principle.
 * The infrastructure layer will implement this interface.
 */
public interface UserRepository {

    /**
     * Create a new user
     * @param user the user to create
     * @return the created user with generated ID
     */
    User create(User user);

    /**
     * Update an existing user
     * @param user the user to update
     * @return the updated user
     */
    User update(User user);

    /**
     * Delete a user by ID
     * @param id the user ID
     */
    void delete(Long id);

    /**
     * Find a user by ID
     * @param id the user ID
     * @return Optional containing the user if found
     */
    Optional<User> findById(Long id);

    /**
     * Find all users
     * @return list of all users
     */
    List<User> findAll();

    /**
     * Find a user by email
     * @param email the user email
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find all active users
     * @return list of active users
     */
    List<User> findAllActive();
}
