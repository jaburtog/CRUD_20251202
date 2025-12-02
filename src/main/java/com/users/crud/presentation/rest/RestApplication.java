package com.users.crud.presentation.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * JAX-RS Application configuration.
 * Configures the base path for all REST endpoints.
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
    // JAX-RS will automatically discover all @Path annotated classes
}
