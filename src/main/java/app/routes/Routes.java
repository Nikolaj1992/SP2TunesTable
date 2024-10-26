package app.routes;

import app.security.routes.SecurityRoutes;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final ArtistRoute artistRoute = new ArtistRoute();
    private final AlbumRoute albumRoute = new AlbumRoute();
    private final SongRoute songRoute = new SongRoute();
    private final SecurityRoutes securityRoutes = new SecurityRoutes();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/artists", artistRoute.getRoutes());
            path("/albums", albumRoute.getRoutes());
            path("/songs", songRoute.getRoutes());
            path("/auth", securityRoutes.getSecurityRoutes());
        };
    }
}