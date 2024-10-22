package app.daos.impl;

import app.config.HibernateConfig;
import app.dtos.AlbumDTO;
import app.entities.Album;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlbumDAOTest {

    private static EntityManagerFactory emf;
    private static AlbumDAO aDao;
    private static ArtistDAO arDao;
    private static SongDAO sDao;

    @BeforeAll
    static void setUpAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        aDao = AlbumDAO.getInstance(emf);
    }

    @BeforeEach
    void setUp() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
//            em.createQuery("DELETE FROM Album").executeUpdate();
//            em.createQuery("DELETE FROM Artist").executeUpdate();
//            em.createQuery("DELETE FROM Song").executeUpdate();
//            em.persist(album);
            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void tearDownAll() {
        emf.close();
    }

    @Test
    void read() {
    }

    @Test
    void readAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

}