package app.utils.json;

import app.dtos.AlbumDTO;
import app.dtos.SongDTO;
import app.dtos.TracksDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Read JSON file and convert to JsonNode
            JsonNode rootNode = objectMapper.readTree(new File("src/result.json"));

            // Deserialize general information into AlbumDTO
            AlbumDTO album = objectMapper.treeToValue(rootNode, AlbumDTO.class);

            // Access the "items" array directly from tracks
            List<SongDTO> songs = objectMapper.treeToValue(rootNode.path("tracks").path("items"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, SongDTO.class));
            album.setTracks(new TracksDTO()); // Initialize TracksDTO
            album.getTracks().setSongs(songs); // Set the items in TracksDTO

            // Print the item details
            System.out.println("name: " + album.getName());
            System.out.println("type: " + album.getType());
            System.out.println("total songs: " + album.getTotalSongs());
            System.out.println("total songs: " + album.getTotalSongs());
            System.out.println("release date: " + album.getReleaseDate());
            System.out.println("rdp: " + album.getReleaseDatePrecision());
            System.out.println("artists: " + album.getArtists().toString());
            songs.forEach(System.out::println);
//            System.out.println("songs: " + album.getTracks().getSongs().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
