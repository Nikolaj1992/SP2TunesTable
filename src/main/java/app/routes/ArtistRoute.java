package app.routes;

import app.controllers.impl.ArtistController;
import app.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ArtistRoute {

    private final ArtistController artistController = new ArtistController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/", artistController::create, Role.USER);
            post("/{id}/add_album/{id2}", artistController::addAlbum);
            get("/", artistController::readAll);
            get("/{id}", artistController::read);
            put("/{id}", artistController::update);
            delete("/{id}", artistController::delete);
        };
    }
}