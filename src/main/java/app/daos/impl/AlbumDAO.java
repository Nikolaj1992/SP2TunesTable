package app.daos.impl;

import app.daos.IDAO;
import app.dtos.AlbumDTO;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class AlbumDAO implements IDAO<AlbumDTO,Integer> {

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
        return null;
    }

    @Override
    public List<AlbumDTO> readAll() {
        return List.of();
    }

    @Override
    public AlbumDTO create(AlbumDTO albumDTO) {
        return null;
    }

    @Override
    public AlbumDTO update(Integer integer, AlbumDTO albumDTO) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return false;
    }
}
