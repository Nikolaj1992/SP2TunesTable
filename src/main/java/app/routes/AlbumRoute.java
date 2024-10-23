package app.routes;

import app.controllers.impl.AlbumController;
import app.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class AlbumRoute {

    private final AlbumController albumController = new AlbumController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/", albumController::create, Role.USER);
            get("/", albumController::readAll);
            get("/{id}", albumController::read);
            put("/{id}", albumController::update);
            delete("/{id}", albumController::delete); //make this turn all songs into singles or add a word to indicate they are former album songs
        };
    }
}