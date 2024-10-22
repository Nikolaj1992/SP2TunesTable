package app.controllers.impl;

import app.config.HibernateConfig;
import app.controllers.IController;
import app.daos.impl.SongDAO;
import app.dtos.SongDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class SongController implements IController<SongDTO,Integer> {

    private final SongDAO dao;

    public SongController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = SongDAO.getInstance(emf);
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
    public SongDTO validateEntity(Context ctx) {
        return null;
    }
}
