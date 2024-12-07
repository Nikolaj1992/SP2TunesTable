package app;

import app.config.ApplicationConfig;

public class Main {
    public static void main(String[] args) {

        ApplicationConfig.startServer(7074);
        // Only runs locally
        Populate populate = new Populate();
        populate.runIfEmpty();

    }
}
