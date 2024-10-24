package app;

import app.config.HibernateConfig;
import app.dtos.AlbumDTO;
import app.entities.Artist;
import app.security.daos.SecurityDAO;
import app.security.enums.Role;
import app.utils.Utils;
import app.utils.json.JsonReader;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

public class Populate {
    public static void main(String[] args) {
        run();
    }

    public static void run(){
        // Environment state and credentials for user/admin handled in config.properties via Utils class
        String environment = Utils.getPropertyValue("ENVIRONMENT", "config.properties");
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        SecurityDAO securityDAO = new SecurityDAO(emf);     // securityDao handles creation of users and roles

        AlbumDTO albumDTO = JsonReader.readAlbum("");
        Artist artist = new Artist(albumDTO.getArtists().get(0));
        int availableAlbumIndex = 1; //starts at one because place 0 is for singles
        int existingAlbums = 0;

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            createUser(securityDAO);    // creates user with role User from config.properties
            if (!"development".equalsIgnoreCase(environment) && !"testing".equalsIgnoreCase(environment)) {
                System.out.println("Environment is not development or testing, skipping admin user creation");
            } else {
                seedAdminUser(securityDAO); // creates admin user with credentials from config.properties
            }
            em.persist(artist);
            existingAlbums = 2; //going to be a createdQuery in the final version
            availableAlbumIndex = availableAlbumIndex + existingAlbums;
            artist.addAlbumAsDTO(albumDTO, availableAlbumIndex);
            em.persist(artist);
            em.getTransaction().commit();
        }
    }

    private static void createUser(SecurityDAO securityDAO) {
        String username = Utils.getPropertyValue("USER_USERNAME", "config.properties");
        String password = Utils.getPropertyValue("USER_PASSWORD", "config.properties");

        if (username == null || password == null) {
            System.out.println("No user credentials found in config.properties");
            return;
        }

        try {
            securityDAO.createUser(username, password);     // createUser method should give the User role
            System.out.println("User created with username: " + username);
        } catch (Exception e) {
            System.out.println("Failed to create user: " + e.getMessage());
        }
    }

    private static void seedAdminUser(SecurityDAO securityDAO) {
        String adminUsername = Utils.getPropertyValue("ADMIN_USERNAME", "config.properties");
        String adminPassword = Utils.getPropertyValue("ADMIN_PASSWORD", "config.properties");

        if (adminUsername == null || adminPassword == null) {
            System.out.println("No admin credentials found in config.properties");
            return;
        }

        try {
            securityDAO.createUser(adminUsername, adminPassword);
            securityDAO.addRole(new UserDTO(adminUsername, Set.of(Role.ADMIN.name())), "admin");
            System.out.println("Admin user created with username: " + adminUsername);
        } catch (Exception e) {
            System.out.println("Failed to create admin user: " + e.getMessage());
        }
    }

}
