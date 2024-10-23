package app.controllers.impl;

import app.config.HibernateConfig;
import app.controllers.impl.AlbumController;
import app.dtos.AlbumDTO;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.*;

@Testcontainers
class AlbumControllerTest {

    private static Javalin app;
    private static EntityManagerFactory emf;

    @BeforeAll
    public static void setUp() {
//        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
//        System.setProperty("hibernate.connection.username", postgres.getUsername());
//        System.setProperty("hibernate.connection.password", postgres.getPassword());
//
//        HibernateConfig.setTest(true);
//        emf = HibernateConfig.getEntityManagerFactoryForTest();
//
//        app = Javalin.create().start(7000);
//        app.routes(() -> {
//            AlbumController albumController = new AlbumController();
//            app.get("/albums", albumController::readAll);
//            app.get("/albums/:id", albumController::read);
//            app.post("/albums", albumController::create);
//            app.put("/albums/:id", albumController::update);
//            app.delete("/albums/:id", albumController::delete);
//        });
//
//        RestAssured.baseURI = "http://localhost:7000";
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