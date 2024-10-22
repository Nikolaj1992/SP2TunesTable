package app.utils.json;

import app.dtos.AlbumDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonReader {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            AlbumDTO album = objectMapper.readValue(
                    new File("path/to/your/file.json"),
                    AlbumDTO.class
            );

            // Print the item details
            System.out.println("name: " + album.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
