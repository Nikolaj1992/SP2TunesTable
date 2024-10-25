package app.controllers.impl;

import app.dtos.AlbumDTO;
import app.dtos.ArtistDTO;
import app.entities.Album;
import app.entities.Artist;
import app.security.entities.Role;
import app.security.entities.User;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManagerFactory;

public class Populator {

    public static UserDTO[] populateUsers(EntityManagerFactory emf) {
        User user, admin;
        Role userRole, adminRole;

        user = new User("testuser", "user1234");
        admin = new User("testadmin", "admin1234");
        userRole = new Role("USER");
        adminRole = new Role("ADMIN");
        user.addRole(userRole);
        admin.addRole(adminRole);

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.getTransaction().commit();
        }
        UserDTO userDTO = new UserDTO(user.getUsername(), "user1234");
        UserDTO adminDTO = new UserDTO(admin.getUsername(), "admin1234");
        return new UserDTO[]{userDTO, adminDTO};
    }

    public static void populateArtists(EntityManagerFactory emf) {
        ArtistDTO artist1;
        ArtistDTO artist2;

        artist1 = new ArtistDTO();
        artist1.setName("Test Artist 1");
        artist1.setType("artist");

        artist2 = new ArtistDTO();
        artist2.setName("Test Artist 2");
        artist2.setType("artist");

        Artist entity1 = new Artist(artist1);
        Artist entity2 = new Artist(artist2);

        AlbumDTO album1;
        AlbumDTO album2;

        album1 = new AlbumDTO();
        album1.setName("Test Album 1");
        album1.setType("album");

        album2 = new AlbumDTO();
        album2.setName("Test Album 2");
        album2.setType("album");

        Album entity3 = new Album(album1);
        Album entity4 = new Album(album2);

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Artist").executeUpdate();
            em.persist(entity1);
            em.persist(entity2);
            entity3.setArtist(entity1);
            entity4.setArtist(entity2);
            em.persist(entity3);
            em.persist(entity4);
            em.getTransaction().commit();
        }
    }

}
