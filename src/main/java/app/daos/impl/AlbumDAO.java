package app.daos.impl;

import app.config.HibernateConfig;
import app.daos.IDAO;
import app.dtos.AlbumDTO;
import app.entities.Album;
import app.entities.Artist;
import app.exceptions.DaoException;
import app.security.daos.SecurityDAO;
import app.security.enums.Role;
import app.utils.Utils;
import app.utils.json.JsonReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AlbumDAO implements IDAO<AlbumDTO, Integer> {

    private static AlbumDAO instance;
    private static EntityManagerFactory emf;

    public static AlbumDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AlbumDAO();
        }
        return instance;
    }

    @Override
    public AlbumDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Album album = em.find(Album.class, integer);
            if (album == null) {
                throw new DaoException.EntityNotFoundException(Album.class, integer);
            }
            return new AlbumDTO(album);
        } catch (Exception e) {
            throw new DaoException.EntityNotFoundException(Album.class, integer);
        }
    }

    @Override
    public List<AlbumDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<AlbumDTO> query = em.createQuery("SELECT new app.dtos.AlbumDTO(a) FROM Album a", AlbumDTO.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException.EntityFindAllException(Album.class, e);
        }
    }

    @Override
    public AlbumDTO create(AlbumDTO albumDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Album album = new Album(albumDTO);
            int existingAlbums = em.createQuery("SELECT COUNT(a) FROM Album a", Long.class).getSingleResult().intValue();
            album.giveId(existingAlbums);
            em.persist(album);
            em.getTransaction().commit();
            return new AlbumDTO(album);
        } catch (Exception e) {
            throw new DaoException.EntityCreateException(Album.class, e);
        }
    }

    @Override
    public AlbumDTO update(Integer integer, AlbumDTO albumDTO) {        // TODO correct the setting of fields
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Album a = em.find(Album.class, integer);
            if (a == null) {
                throw new DaoException.EntityNotFoundException(Album.class, integer);
            }
            a.setName(albumDTO.getName());
//            a.setPopularity(albumDTO.getPopularity());
            Album mergedAlbum = em.merge(a);
            em.getTransaction().commit();
            return mergedAlbum != null ? new AlbumDTO(mergedAlbum) : null;
        } catch (Exception e) {
            throw new DaoException.EntityUpdateException(Album.class, integer, e);
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Album album = em.find(Album.class, integer);
            if (album == null) {
                throw new DaoException.EntityNotFoundException(Album.class, integer);
            }
            em.remove(album);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DaoException.EntityDeleteException(Album.class, integer, e);
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Album album = em.find(Album.class, integer);
            return album != null;
        }
    }

    public void populate(){
        // Environment state and credentials for user/admin handled in config.properties via Utils class
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        SecurityDAO securityDAO = new SecurityDAO(emf);     // securityDao handles creation of users and roles

        List<AlbumDTO> albumDTOs = JsonReader.readAlbums("");

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            createRoles(securityDAO);   // creates roles in DB if they do not exist

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

    private void createRoles(SecurityDAO securityDAO) {
        try {
            securityDAO.createRoleIfNotPresent(Role.ANYONE.name());
            securityDAO.createRoleIfNotPresent(Role.USER.name());
            securityDAO.createRoleIfNotPresent(Role.ADMIN.name());
        } catch (Exception e) {
            System.out.println("Failed to create roles: " + e.getMessage());
        }
    }

}
