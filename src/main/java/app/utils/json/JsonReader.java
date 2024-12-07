package app.utils.json;

import app.dtos.AlbumDTO;
import app.dtos.SongDTO;
import app.dtos.TracksDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {

    private static final String DEFAULT_FILE_PATH = "result.json";

    // Use in populate run method for one album
//    public static AlbumDTO readAlbum(String filePath) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        AlbumDTO albumResult = null;
//        try {
//            // Read JSON file and convert to JsonNode
//            JsonNode rootNode = null;
//            if (filePath.equals("")){
//            rootNode = objectMapper.readTree(new File("src/result.json"));
//            } else {
//            rootNode = objectMapper.readTree(new File(filePath));
//            }
//
//            // Deserialize general information into AlbumDTO
//            AlbumDTO album = objectMapper.treeToValue(rootNode, AlbumDTO.class);
//
//            // Access the "items" array directly from tracks
//            List<SongDTO> songs = objectMapper.treeToValue(rootNode.path("tracks").path("items"),
//                    objectMapper.getTypeFactory().constructCollectionType(List.class, SongDTO.class));
//            album.setTracks(new TracksDTO()); // Initialize TracksDTO
//            album.getTracks().setSongs(songs); // Set the items in TracksDTO
//
//            albumResult = album;
//
//            // Print the item details
//            System.out.println("name: " + album.getName());
//            System.out.println("type: " + album.getType());
//            System.out.println("total songs: " + album.getTotalSongs());
//            System.out.println("total songs: " + album.getTotalSongs());
//            System.out.println("release date: " + album.getReleaseDate());
//            System.out.println("artists: " + album.getArtists().toString());
//            songs.forEach(System.out::println);
////            System.out.println("songs: " + album.getTracks().getSongs().toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return albumResult;
//    }
//
//    // Use in populate run method for multiple albums
//    public static List<AlbumDTO> readAlbums(String filePath) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<AlbumDTO> albumList = new ArrayList<>();
//
//        try {
//            JsonNode rootNode = null;
//            if (filePath.equals("")){
//                rootNode = objectMapper.readTree(new File("src/result.json"));
//            } else {
//                rootNode = objectMapper.readTree(new File(filePath));
//            }
//
//            JsonNode albumsNode = rootNode.path("albums");
//
//            if (albumsNode.isArray()) {
//                for (JsonNode albumNode : albumsNode) {
//                    AlbumDTO album = objectMapper.treeToValue(albumNode, AlbumDTO.class);
//
//                    List<SongDTO> songs = objectMapper.treeToValue(albumNode.path("tracks").path("items"),
//                            objectMapper.getTypeFactory().constructCollectionType(List.class, SongDTO.class));
//
//                    album.setTracks(new TracksDTO());
//                    album.getTracks().setSongs(songs);
//
//                    albumList.add(album);
//                }
//            }
//
//            // Print album details
//            albumList.forEach(album -> {
//                System.out.println("name: " + album.getName());
//                System.out.println("type: " + album.getType());
//                System.out.println("total songs: " + album.getTotalSongs());
//                System.out.println("release date: " + album.getReleaseDate());
//                System.out.println("artists: " + album.getArtists().toString());
//                album.getTracks().getSongs().forEach(System.out::println);
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return albumList;
//    }

    public static AlbumDTO readAlbum(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        AlbumDTO albumResult = null;

        try (InputStream inStream = filePath.isEmpty()
                ? JsonReader.class.getClassLoader().getResourceAsStream(DEFAULT_FILE_PATH)
                : new FileInputStream(filePath)) {

            if (inStream == null) {
                throw new IOException("File not found: " + (filePath.isEmpty() ? DEFAULT_FILE_PATH : filePath));
            }

            // Read JSON content
            JsonNode rootNode = objectMapper.readTree(inStream);

            // Deserialize general information into AlbumDTO
            albumResult = objectMapper.treeToValue(rootNode, AlbumDTO.class);

            // Access the "items" array directly from tracks
            List<SongDTO> songs = objectMapper.treeToValue(
                    rootNode.path("tracks").path("items"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, SongDTO.class)
            );

            albumResult.setTracks(new TracksDTO()); // Initialize TracksDTO
            albumResult.getTracks().setSongs(songs); // Set the items in TracksDTO

            // Print the item details for debugging purposes
            System.out.println("Album Name: " + albumResult.getName());
            System.out.println("Type: " + albumResult.getType());
            System.out.println("Total Songs: " + albumResult.getTotalSongs());
            System.out.println("Release Date: " + albumResult.getReleaseDate());
            System.out.println("Artists: " + albumResult.getArtists());
            songs.forEach(song -> System.out.println("Song: " + song));

        } catch (IOException e) {
            System.err.println("Error reading album JSON: " + e.getMessage());
            e.printStackTrace();
        }

        return albumResult;
    }

    // Use in populate run method for multiple albums
    public static List<AlbumDTO> readAlbums(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<AlbumDTO> albumList = new ArrayList<>();

        try (InputStream inStream = filePath.isEmpty()
                ? JsonReader.class.getClassLoader().getResourceAsStream(DEFAULT_FILE_PATH)
                : new FileInputStream(filePath)) {

            if (inStream == null) {
                throw new IOException("File not found: " + (filePath.isEmpty() ? DEFAULT_FILE_PATH : filePath));
            }

            // Read JSON content
            JsonNode rootNode = objectMapper.readTree(inStream);

            // Access "albums" array node
            JsonNode albumsNode = rootNode.path("albums");

            if (albumsNode.isArray()) {
                for (JsonNode albumNode : albumsNode) {
                    // Deserialize album
                    AlbumDTO album = objectMapper.treeToValue(albumNode, AlbumDTO.class);

                    // Deserialize songs under "tracks.items"
                    List<SongDTO> songs = objectMapper.treeToValue(
                            albumNode.path("tracks").path("items"),
                            objectMapper.getTypeFactory().constructCollectionType(List.class, SongDTO.class)
                    );

                    album.setTracks(new TracksDTO());
                    album.getTracks().setSongs(songs);

                    albumList.add(album);
                }
            }

            // Print album details for debugging purposes
            albumList.forEach(album -> {
                System.out.println("Album Name: " + album.getName());
                System.out.println("Type: " + album.getType());
                System.out.println("Total Songs: " + album.getTotalSongs());
                System.out.println("Release Date: " + album.getReleaseDate());
                System.out.println("Artists: " + album.getArtists());
                album.getTracks().getSongs().forEach(song -> System.out.println("Song: " + song));
            });

        } catch (IOException e) {
            System.err.println("Error reading albums JSON: " + e.getMessage());
            e.printStackTrace();
        }

        return albumList;
    }

}
