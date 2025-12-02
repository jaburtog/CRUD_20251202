package com.users.crud.infrastructure.config;

import com.users.crud.domain.port.UserRepository;
import com.users.crud.infrastructure.persistence.UserRepositoryJPA;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

/**
 * CDI configuration for dependency injection.
 * Produces implementations for domain ports.
 */
@ApplicationScoped
public class RepositoryProducer {

    @Inject
    private UserRepositoryJPA userRepositoryJPA;

    @Produces
    @ApplicationScoped
    public UserRepository produceUserRepository() {
        return userRepositoryJPA;
    }
}
