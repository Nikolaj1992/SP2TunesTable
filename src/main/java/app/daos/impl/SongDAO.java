package app.daos.impl;

import app.daos.IDAO;
import app.dtos.SongDTO;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class SongDAO implements IDAO<SongDTO,Integer> {

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
    public SongDTO read(Integer integer) {
        return null;
    }

    @Override
    public List<SongDTO> readAll() {
        return List.of();
    }

    @Override
    public SongDTO create(SongDTO songDTO) {
        return null;
    }

    @Override
    public SongDTO update(Integer integer, SongDTO songDTO) {
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
