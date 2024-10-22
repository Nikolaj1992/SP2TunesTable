package app.daos.impl;

import app.daos.IDAO;
import app.dtos.ArtistDTO;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ArtistDAO implements IDAO<ArtistDTO,Integer> {

    private static ArtistDAO instance;
    private static EntityManagerFactory emf;

    public static ArtistDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ArtistDAO();
        }
        return instance;
    }

    @Override
    public ArtistDTO read(Integer integer) {
        return null;
    }

    @Override
    public List<ArtistDTO> readAll() {
        return List.of();
    }

    @Override
    public ArtistDTO create(ArtistDTO artistDTO) {
        return null;
    }

    @Override
    public ArtistDTO update(Integer integer, ArtistDTO artistDTO) {
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
