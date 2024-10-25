package app.controllers.impl;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.dtos.ArtistDTO;
import app.entities.Artist;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class ArtistControllerTest {

    private static EntityManagerFactory emf;
    private static Javalin app;
    private static final int TEST_PORT = 7000;

    private ArtistDTO artist1;
    private ArtistDTO artist2;

    @BeforeAll
    static void setUpAll() {    // TODO handle security test setup here
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        app = ApplicationConfig.startServer(TEST_PORT);

        RestAssured.baseURI = "http://localhost:" + TEST_PORT + "/api";
    }

    @AfterAll
    static void tearDownAll() {
        ApplicationConfig.stopServer(app);
        emf.close();
    }

    @BeforeEach
    void setUp() {
        artist1 = new ArtistDTO();
        artist1.setName("Test Artist 1");
        artist1.setType("artist");

        artist2 = new ArtistDTO();
        artist2.setName("Test Artist 2");
        artist2.setType("artist");

        Artist entity1 = new Artist(artist1);
        Artist entity2 = new Artist(artist2);

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Artist").executeUpdate();
            em.persist(entity1);
            artist1.setArtistId(String.valueOf(entity1.getId()));
            em.persist(entity2);
            artist2.setArtistId(String.valueOf(entity2.getId()));
            em.getTransaction().commit();
        }
    }

    @AfterEach
    void tearDown() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Artist ").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE artist_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }

    // error from post possibly not working. This code should work once security is implemented.
    // This would also make the entire test class more uniform.
//    @BeforeEach
//    void setUp() {
//        clearArtists();
//        createArtist("Test Artist 1");
//        createArtist("Test Artist 2");
//    }
//
//    // Helper methods
//    private void clearArtists() {
//        given()
//                .when()
//                .delete("/artists")
//                .then()
//                .statusCode(204);
//    }
//
//    private void createArtist(String name) {
//        given()
//                .contentType("application/json")
//                .body("{\"name\":\"" + name + "\", \"type\":\"artist\"}")
//                .when()
//                .post("/artists")
//                .then()
//                .statusCode(201);
//    }

    @Test
    void read() {
        int artistId = 1;
        given()
                .contentType("application/json")
                .when()
                .get("/artists/{id}", artistId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Test Artist 1"));
    }

    @Test
    void readAll() {
        given()
                .when()
                .get("/artists")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2)); // Expecting two artists created in setup
    }

    @Test
    void create() { // TODO factor in security roles
        given()
                .contentType("application/json")
                .body("{\"name\":\"Test Artist 3\", \"type\":\"artist\"}")
                .when()
                .post("/artists")
                .then()
                .statusCode(201);

        given()
                .when()
                .get("/artists")
                .then()
                .statusCode(200)
                .body("size()", equalTo(3)); // Expecting three artists now
    }

    @Test
    void update() {
        int artistId = 1;
        given()
                .contentType("application/json")
                .body("{\"name\":\"Updated Artist\", \"type\":\"artist\"}")
                .when()
                .put("/artists/{id}", artistId)
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .when()
                .get("/artists/{id}", artistId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Artist"));
    }

    @Test
    void delete() {
        int artistId = 1;
        given()
                .contentType("application/json")
                .when()
                .delete("/artists/{id}", artistId)
                .then()
                .statusCode(204);

        given()
                .contentType("application/json")
                .when()
                .get("/artists/{id}", artistId)
                .then()
                .statusCode(400);       // TODO should ideally be 404, add error handling
    }

}