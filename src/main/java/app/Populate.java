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
        int existingAlbums = 1; //starts at one because place 0 is for singles
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(artist);
            existingAlbums = existingAlbums + 2;
//            Album album = new Album(albumDTO);
//            album.addSongs(albumDTO.getTracks().getSongs());
            artist.addAlbum(albumDTO,existingAlbums);
//            em.persist(album);
            em.persist(artist);
            em.getTransaction().commit();
        }
    }
}
