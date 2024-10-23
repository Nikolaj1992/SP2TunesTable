package app.daos.impl;

import app.daos.IDAO;
import app.dtos.SongDTO;
import app.entities.Song;
import app.exceptions.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class SongDAO implements IDAO<SongDTO, String> {

    private static SongDAO instance;
    private static EntityManagerFactory emf;

    public static SongDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SongDAO();
        }
        return instance;
    }

    @Override
    public SongDTO read(String s) {
        try (EntityManager em = emf.createEntityManager()) {
            Song song = em.find(Song.class, s);
            if (song == null) {
                throw new DaoException.EntityNotFoundException(Song.class, s);
            }
            return new SongDTO(song);
        } catch (Exception e) {
            throw new DaoException.EntityNotFoundException(Song.class, s);
        }
    }

    @Override
    public List<SongDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<SongDTO> query = em.createQuery("SELECT new app.dtos.SongDTO(s) FROM Song s", SongDTO.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException.EntityFindAllException(Song.class, e);
        }
    }

    @Override
    public SongDTO create(SongDTO songDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Song song = new Song(songDTO);
            em.persist(song);
            em.getTransaction().commit();
            return new SongDTO(song);
        } catch (Exception e) {
            throw new DaoException.EntityCreateException(Song.class, e);
        }
    }

    @Override
    public SongDTO update(String s, SongDTO songDTO) {       // TODO correct the setting of fields
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Song song = em.find(Song.class, s);
            if (song == null) {
                throw new DaoException.EntityNotFoundException(Song.class, s);
            }
            song.setName(songDTO.getName());
//            song.setDuration(songDTO.getDuration());
            Song mergedSong = em.merge(song);
            em.getTransaction().commit();
            return mergedSong != null ? new SongDTO(mergedSong) : null;
        } catch (Exception e) {
            throw new DaoException.EntityUpdateException(Song.class, s, e);
        }
    }

    @Override
    public void delete(String s) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Song song = em.find(Song.class, s);
            if (song == null) {
                throw new DaoException.EntityNotFoundException(Song.class, s);
            }
            em.remove(song);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DaoException.EntityDeleteException(Song.class, s, e);
        }
    }

    @Override
    public boolean validatePrimaryKey(String s) {
        try (EntityManager em = emf.createEntityManager()) {
            Song song = em.find(Song.class, s);
            return song != null;
        }
    }
}
