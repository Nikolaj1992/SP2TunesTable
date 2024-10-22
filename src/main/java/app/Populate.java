package app;

import app.config.HibernateConfig;
import app.dtos.AlbumDTO;
import app.entities.Album;
import app.entities.Artist;
import app.utils.json.JsonReader;
import jakarta.persistence.EntityManagerFactory;

public class Populate {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        AlbumDTO albumDTO = JsonReader.readAlbum("");
        Artist artist = new Artist(albumDTO.getArtists().get(0));
        int existingAlbums = 0;
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(artist);
            existingAlbums = 3;
            artist.addAlbum(new Album(albumDTO),existingAlbums);
            em.persist(artist);
            em.getTransaction().commit();
        }
    }
}
