package app.dtos;

import app.entities.Artist;
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
    List<AlbumDTO> albums = new ArrayList<>();

    public ArtistDTO(Artist artist) {
        this.id = String.valueOf(artist.getId());
        this.name = artist.getName();
        this.type = artist.getType();
        List<AlbumDTO> albumDTOList = artist.getAlbums().stream().map(album -> new AlbumDTO(album)).toList();
        for (AlbumDTO albumDTO : albumDTOList) {
            if (!this.albums.contains(albumDTO)) {
                this.albums.add(albumDTO);
            }
        }
    }
}