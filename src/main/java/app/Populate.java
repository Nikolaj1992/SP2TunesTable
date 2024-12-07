package app;

import app.config.HibernateConfig;
import app.dtos.AlbumDTO;
import app.entities.Album;
import app.entities.Artist;
import app.entities.Song;
import app.security.daos.SecurityDAO;
import app.security.enums.Role;
import app.utils.Utils;
import app.utils.json.JsonReader;
import dk.bugelhartmann.UserDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Set;

public class Populate {
    public static void main(String[] args) {
        runMulti();
    }

    public void runIfEmpty(Context ctx) {
        try (var em = HibernateConfig.getEntityManagerFactory().createEntityManager()) {
            List<Artist> artistList = em.createQuery("select a from Artist a").getResultList();
            List<Album> albumList = em.createQuery("select a from Album a").getResultList();
            List<Song> songList = em.createQuery("select a from Song a").getResultList();
            if (artistList.isEmpty() && albumList.isEmpty() && songList.isEmpty()) {
                runMulti();
                ctx.res().setStatus(200);
                ctx.json("Database populated");
            } else {
                ctx.res().setStatus(400);
                ctx.json("Database already populated");
            }
        }
    }

    public void runIfEmpty() {
        try (var em = HibernateConfig.getEntityManagerFactory().createEntityManager()) {
            List<Artist> artistList = em.createQuery("select a from Artist a").getResultList();
            List<Album> albumList = em.createQuery("select a from Album a").getResultList();
            List<Song> songList = em.createQuery("select a from Song a").getResultList();
            if (artistList.isEmpty() && albumList.isEmpty() && songList.isEmpty()) {
                runMulti();
            }
        }
    }

//     Old run method for one album
    public static void runSingle(){
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
            createRoles(securityDAO);   // creates roles in DB if they do not exist
            createUser(securityDAO);    // creates user with role User from config.properties
            if (!"development".equalsIgnoreCase(environment) && !"testing".equalsIgnoreCase(environment)) {
                System.out.println("Environment is not development or testing, skipping admin user creation");
            } else {
                seedAdminUser(securityDAO); // creates admin user with credentials from config.properties
            }
            em.persist(artist);
            em.flush();
            existingAlbums = em.createQuery(
                            "SELECT COUNT(a) FROM Album a WHERE a.artist.id = :artistId", Long.class)
                    .setParameter("artistId", artist.getId())   // Artist should have ID from persist above
                    .getSingleResult()
                    .intValue();
            availableAlbumIndex = availableAlbumIndex + existingAlbums;
            artist.addAlbumAsDTO(albumDTO, availableAlbumIndex);
            em.persist(artist);
            em.getTransaction().commit();
        }
    }

    // New run method for multiple albums
    public static void runMulti(){
        // Environment state and credentials for user/admin handled in config.properties via Utils class
        String environment = Utils.getPropertyValue("ENVIRONMENT", "config.properties");
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        SecurityDAO securityDAO = new SecurityDAO(emf);     // securityDao handles creation of users and roles

        List<AlbumDTO> albumDTOs = JsonReader.readAlbums("");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            createRoles(securityDAO);   // creates roles in DB if they do not exist
            createUser(securityDAO);    // creates user with role User from config.properties

            if (!"development".equalsIgnoreCase(environment) && !"testing".equalsIgnoreCase(environment)) {
                System.out.println("Environment is not development or testing, skipping admin user creation");
            } else {
                seedAdminUser(securityDAO); // creates admin user with credentials from config.properties
            }

            em.getTransaction().commit();

            int availableAlbumIndex = 1; //starts at one because place 0 is for singles
            for (AlbumDTO albumDTO : albumDTOs) {
                em.getTransaction().begin();

                Artist artist = new Artist(albumDTO.getArtists().get(0));
                em.persist(artist);

                em.flush();     // Flush to get the artist ID

                int existingAlbums = em.createQuery(
                        "SELECT COUNT(a) FROM Album a WHERE a.artist.id = :artistId", Long.class)
                        .setParameter("artistId", artist.getId())   // Artist should have ID from persist above
                        .getSingleResult()
                        .intValue();
                availableAlbumIndex = availableAlbumIndex + existingAlbums;

                artist.addAlbumAsDTO(albumDTO, availableAlbumIndex);
                em.persist(artist);

                em.getTransaction().commit();
            }

            System.out.println("Albums added to database");
        } catch (Exception e) {
            System.out.println("Failed to add albums to database: " + e.getMessage());
            e.printStackTrace();
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

    private static void createRoles(SecurityDAO securityDAO) {
        try {
            securityDAO.createRoleIfNotPresent(Role.ANYONE.name());
            securityDAO.createRoleIfNotPresent(Role.USER.name());
            securityDAO.createRoleIfNotPresent(Role.ADMIN.name());
        } catch (Exception e) {
            System.out.println("Failed to create roles: " + e.getMessage());
        }
    }

}
