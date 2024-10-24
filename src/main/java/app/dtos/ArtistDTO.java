package app.dtos;

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
    List<Integer> albumIds;
    List<Integer> songIds;

    public ArtistDTO(Artist artist) {
        this.id = String.valueOf(artist.getId());
        this.name = artist.getName();
        this.type = artist.getType();
        this.albumIds = artist.getAlbums().stream().map(album -> album.getId()).toList();
        this.songIds = artist.getSongs().stream().map(song -> song.getId()).toList();
    }
}