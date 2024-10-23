package app;

import app.config.HibernateConfig;
import app.dtos.AlbumDTO;
import app.entities.Album;
import app.entities.Artist;
import app.utils.json.JsonReader;
import jakarta.persistence.EntityManagerFactory;

public class Populate {
    public static void main(String[] args) {
        Populate populate = new Populate();
        populate.run();
    }

    public void run(){
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        AlbumDTO albumDTO = JsonReader.readAlbum("");
        Artist artist = new Artist(albumDTO.getArtists().get(0));
        int availableAlbumIndex = 1; //starts at one because place 0 is for singles
        int existingAlbums = 0;
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(artist);
            existingAlbums = 2;
            availableAlbumIndex = availableAlbumIndex + existingAlbums;
            artist.addAlbum(albumDTO,availableAlbumIndex);
            em.persist(artist);
            em.getTransaction().commit();
        }
    }
}
