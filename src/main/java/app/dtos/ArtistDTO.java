package app.dtos;

import app.entities.Album;
import app.entities.Artist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
//    List<AlbumDTO> albums;
//    List<SongDTO> songs;

    public ArtistDTO(Artist artist) {
        this.id = String.valueOf(artist.getId());
        this.name = artist.getName();
        this.type = artist.getType();
//        this.albums = artist.getAlbums().stream().map(album -> new AlbumDTO(album)).toList();
//        this.songs = artist.getSongs().stream().map(song -> new SongDTO(song)).toList();
    }
}