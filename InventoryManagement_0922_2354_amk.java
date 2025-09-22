// 代码生成时间: 2025-09-22 23:54:15
package com.yourcompany.inventorymanagement;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Path("/inventory")
public class InventoryManagement {

    private final Map<String, Integer> inventory = new HashMap<>();

    // Adds an item to the inventory
    @POST
    @Path("/items/{itemId}")
    @RolesAllowed("admin")
    public Response addItem(@PathParam("itemId") String itemId, int quantity) {
        try {
            inventory.merge(itemId, quantity, Integer::sum);
            return Response.ok("Item added to inventory.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error adding item to inventory.").build();
        }
    }

    // Removes an item from the inventory
    @POST
    @Path("/items/{itemId}")
    @RolesAllowed("admin")
    public Response removeItem(@PathParam("itemId") String itemId, int quantity) {
        try {
            inventory.computeIfPresent(itemId, (key, value) -> value - quantity < 0 ? 0 : value - quantity);
            return Response.ok("Item removed from inventory.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error removing item from inventory.").build();
        }
    }

    // Retrieves an item's quantity from the inventory
    @GET
    @Path("/items/{itemId}")
    public Response getItem(@PathParam("itemId") String itemId) {
        try {
            Optional<Integer> quantity = Optional.ofNullable(inventory.get(itemId));
            if (quantity.isPresent()) {
                return Response.ok(quantity).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Item not found in inventory.").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving item from inventory.").build();
        }
    }

    // Retrieves the entire inventory
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInventory() {
        return Response.ok(inventory).build();
    }
}
