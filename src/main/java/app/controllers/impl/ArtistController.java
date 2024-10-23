package app.controllers.impl;

import app.config.HibernateConfig;
import app.controllers.IController;
import app.daos.impl.ArtistDAO;
import app.dtos.ArtistDTO;
import app.entities.Album;
import app.entities.Artist;
import app.exceptions.DaoException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ArtistController implements IController<ArtistDTO, Integer> {

    private final ArtistDAO dao;

    public ArtistController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = ArtistDAO.getInstance(emf);
    }

    public void addAlbum(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        int albumID = ctx.pathParamAsClass("id2", Integer.class).get();
        ArtistDTO artistDTO = dao.read(id);
        Artist artist = new Artist(artistDTO);
        try (var em = HibernateConfig.getEntityManagerFactory().createEntityManager()) {
            em.getTransaction().begin();
            try {
            Album album = em.find(Album.class, albumID);
            artist.addAlbum(album);
            em.persist(artist);
            } catch (DaoException.EntityNotFoundException e) {
                throw new DaoException(e.getMessage());
            }
            em.getTransaction().commit();
        }
        ctx.res().setStatus(200);
        ctx.json(new ArtistDTO(artist), ArtistDTO.class);
    }

    @Override
    public void read(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        ArtistDTO artistDTO = dao.read(id);
        ctx.res().setStatus(200);
        ctx.json(artistDTO, ArtistDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        List<ArtistDTO> artistDTOS = dao.readAll();
        ctx.res().setStatus(200);
        ctx.json(artistDTOS, ArtistDTO.class);
    }

    @Override
    public void create(Context ctx) {
        ArtistDTO jsonRequest = ctx.bodyAsClass(ArtistDTO.class);
        ArtistDTO artistDTO = dao.create(jsonRequest);
        ctx.res().setStatus(201);
        ctx.json(artistDTO, ArtistDTO.class);
    }

    @Override
    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        ArtistDTO artistDTO = dao.update(id, validateEntity(ctx));
        ctx.res().setStatus(200);
        ctx.json(artistDTO, ArtistDTO.class);
    }

    @Override
    public void delete(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public ArtistDTO validateEntity(Context ctx) {      // TODO add needed checks
        return ctx.bodyValidator(ArtistDTO.class)
//                .check(a -> a.getName() != null && !a.getName().isEmpty(), "Invalid name")
                .get();
    }
}
