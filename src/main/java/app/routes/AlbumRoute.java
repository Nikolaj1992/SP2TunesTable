package app.routes;

import app.controllers.impl.AlbumController;
import app.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class AlbumRoute {

    private final AlbumController albumController = new AlbumController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/populate", albumController::populate, Role.USER);        // this is a test so far
            post("/", albumController::create, Role.ADMIN);
            get("/", albumController::readAll, Role.ANYONE, Role.USER);
            get("/{id}", albumController::read, Role.ANYONE, Role.USER);
            put("/{id}", albumController::update, Role.ADMIN);
            delete("/{id}", albumController::delete, Role.ADMIN); //make this turn all songs into singles or add a word to indicate they are former album songs
        };
    }
}