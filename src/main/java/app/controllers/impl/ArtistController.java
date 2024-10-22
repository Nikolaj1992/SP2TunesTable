package app.controllers.impl;

import app.config.HibernateConfig;
import app.controllers.IController;
import app.daos.impl.ArtistDAO;
import app.dtos.ArtistDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class ArtistController implements IController<ArtistDTO,Integer> {

    private final ArtistDAO dao;

    public ArtistController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = ArtistDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {

    }

    @Override
    public void readAll(Context ctx) {

    }

    @Override
    public void create(Context ctx) {

    }

    @Override
    public void update(Context ctx) {

    }

    @Override
    public void delete(Context ctx) {

    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return false;
    }

    @Override
    public ArtistDTO validateEntity(Context ctx) {
        return null;
    }
}
