package app.dtos;

import app.entities.Artist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistDTO {
    String id;
    @JsonProperty("name")
    String name;
    @JsonProperty("type")
    String type;
    @JsonIgnore
    List<AlbumDTO> albums = new ArrayList<>();

    public ArtistDTO(Artist artist, boolean includeAlbums) {
        this.id = String.valueOf(artist.getId());
        this.name = artist.getName();
        this.type = artist.getType();

        // Only populate albums if includeAlbums is true, to prevent infinite recursion
        if (includeAlbums) {
            this.albums = artist.getAlbums().stream()
                    .map(album -> new AlbumDTO(album, false))  // Pass 'false' to avoid circular reference
                    .toList();
        }this.id = String.valueOf(artist.getId());
        this.name = artist.getName();
        this.type = artist.getType();

        // Only populate albums if includeAlbums is true, to prevent infinite recursion
        if (includeAlbums) {
            this.albums = artist.getAlbums().stream()
                    .map(album -> new AlbumDTO(album, false))  // Pass 'false' to avoid circular reference
                    .toList();
        }
    }
}