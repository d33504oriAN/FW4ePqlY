// 代码生成时间: 2025-09-23 00:53:33
package com.example.security;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/permission")
@ApplicationScoped
public class UserPermissionManagement {

    private final Map<String, String> permissions;

    public UserPermissionManagement() {
        this.permissions = new HashMap<>();
        // Initialize permissions for users
        this.permissions.put("user1", "admin");
        this.permissions.put("user2", "user");
        this.permissions.put("user3", "guest");
    }

    /**
     * Check if the user has the specified permission.
     *
     * @param username The username to check.
     * @param permission The permission to check.
     * @return A response indicating whether the user has the permission.
     */
    @GET
    @Path("/check")
    @RolesAllowed("admin")
    public Response checkPermission(String username, String permission) {
        try {
            if (permissions.containsKey(username) && permissions.get(username).equals(permission)) {
                return Response.status(Response.Status.OK)
                        .entity("User has the specified permission.")
                        .type(MediaType.TEXT_PLAIN).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN)
                        .entity("User does not have the specified permission.")
                        .type(MediaType.TEXT_PLAIN).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while checking permissions.")
                    .type(MediaType.TEXT_PLAIN).build();
        }
    }

    /**
     * A main method to run the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        UserPermissionManagement instance = new UserPermissionManagement();
        System.out.println("User Permission Management System is running...");
    }
}
