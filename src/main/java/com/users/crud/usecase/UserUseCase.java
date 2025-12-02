package com.users.crud.usecase;

import com.users.crud.domain.entity.User;
import com.users.crud.domain.port.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Use case for managing user CRUD operations.
 * This contains the business logic and orchestrates the domain objects.
 */
@ApplicationScoped
public class UserUseCase {

    @Inject
    private UserRepository userRepository;

    /**
     * Create a new user
     * @param user the user to create
     * @return the created user
     * @throws IllegalArgumentException if email already exists
     */
    @Transactional
    public User createUser(User user) {
        // Business rule: check if email already exists
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.create(user);
    }

    /**
     * Update an existing user
     * @param id the user ID
     * @param user the user data to update
     * @return the updated user
     * @throws IllegalArgumentException if user not found or email already exists for another user
     */
    @Transactional
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));

        // Business rule: check if email is being changed and if it already exists for another user
        if (!existingUser.getEmail().equals(user.getEmail())) {
            Optional<User> userWithEmail = userRepository.findByEmail(user.getEmail());
            if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(id)) {
                throw new IllegalArgumentException("Email " + user.getEmail() + " is already in use by another user");
            }
        }

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setActive(user.getActive());

        return userRepository.update(existingUser);
    }

    /**
     * Delete a user by ID
     * @param id the user ID
     * @throws IllegalArgumentException if user not found
     */
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
        userRepository.delete(id);
    }

    /**
     * Get a user by ID
     * @param id the user ID
     * @return the user
     * @throws IllegalArgumentException if user not found
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }

    /**
     * Get all users
     * @return list of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get all active users
     * @return list of active users
     */
    public List<User> getAllActiveUsers() {
        return userRepository.findAllActive();
    }

    /**
     * Get a user by email
     * @param email the user email
     * @return Optional containing the user if found
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
