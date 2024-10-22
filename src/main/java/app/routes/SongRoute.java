package app.routes;

import app.controllers.impl.SongController;
//import app.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class SongRoute {

    private final SongController songController = new SongController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/", songController::create, Role.USER);
            get("/", songController::readAll);
            get("/{id}", songController::read);
            put("/{id}", songController::update);
            delete("/{id}", songController::delete);
        };
    }
}