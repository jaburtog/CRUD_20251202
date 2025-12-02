package com.users.crud.presentation.rest;

import com.users.crud.domain.entity.User;
import com.users.crud.usecase.UserUseCase;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

/**
 * REST API resource for User CRUD operations.
 * This is part of the presentation layer.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserUseCase userUseCase;

    /**
     * Create a new user
     * POST /api/users
     */
    @POST
    public Response createUser(@Valid User user) {
        try {
            User createdUser = userUseCase.createUser(user);
            return Response.status(Response.Status.CREATED)
                    .entity(createdUser)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error creating user: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Get all users
     * GET /api/users
     */
    @GET
    public Response getAllUsers(@QueryParam("active") @DefaultValue("false") boolean activeOnly) {
        try {
            List<User> users = activeOnly ? userUseCase.getAllActiveUsers() : userUseCase.getAllUsers();
            return Response.ok(users).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving users: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Get a user by ID
     * GET /api/users/{id}
     */
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        try {
            User user = userUseCase.getUserById(id);
            return Response.ok(user).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving user: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Update a user
     * PUT /api/users/{id}
     */
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, @Valid User user) {
        try {
            User updatedUser = userUseCase.updateUser(id, user);
            return Response.ok(updatedUser).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error updating user: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Delete a user
     * DELETE /api/users/{id}
     */
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        try {
            userUseCase.deleteUser(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error deleting user: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Get a user by email
     * GET /api/users/email/{email}
     */
    @GET
    @Path("/email/{email}")
    public Response getUserByEmail(@PathParam("email") String email) {
        try {
            Optional<User> user = userUseCase.getUserByEmail(email);
            if (user.isPresent()) {
                return Response.ok(user.get()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("User with email " + email + " not found"))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving user: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Simple error response class
     */
    public static class ErrorResponse {
        private String message;

        public ErrorResponse() {
        }

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
