// 代码生成时间: 2025-09-29 00:02:31
package com.example.energymanagement;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.quarkus.runtime.StartupEvent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

@Path("/energy")
@ApplicationScoped
public class EnergyManagementSystem {
    @Inject
    Logger logger;

    @PersistenceContext
    EntityManager em;

    public EnergyManagementSystem() {
        logger.info("EnergyManagementSystem initialized");
    }

    @Transactional
    public void checkAndUpdateEnergyUsage() {
        // Logic to check and update energy usage
        // This is a placeholder for actual energy usage logic
    }

    @GET
    @Path("/currentStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EnergyUsage> getCurrentStatus() {
        try {
            TypedQuery<EnergyUsage> query = em.createQuery("SELECT e FROM EnergyUsage e", EnergyUsage.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.severe("Failed to retrieve energy usage: " + e.getMessage());
            throw new RuntimeException("Error retrieving energy usage");
        }
    }

    // Method to handle startup event
    void onStart(@Observes StartupEvent ev) {
        checkAndUpdateEnergyUsage();
        logger.info("Energy management system is up and running");
    }
}

/*
 * EnergyUsage.java
 * This class represents the energy usage entity.
 */
package com.example.energymanagement;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class EnergyUsage implements Serializable {
    @Id
    private Long id;
    private String deviceType;
    private Double energyConsumption;
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public Double getEnergyConsumption() { return energyConsumption; }
    public void setEnergyConsumption(Double energyConsumption) { this.energyConsumption = energyConsumption; }
}
