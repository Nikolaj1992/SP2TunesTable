package app.controllers.impl;

import app.config.HibernateConfig;
import app.controllers.IController;
import app.daos.impl.AlbumDAO;
import app.dtos.AlbumDTO;
import app.entities.Album;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class AlbumController implements IController<AlbumDTO,Integer> {

    private final AlbumDAO dao;

    public AlbumController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = AlbumDAO.getInstance(emf);
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
    public Album validateEntity(Context ctx) {
        return null;
    }
}
