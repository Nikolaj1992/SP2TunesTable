package app.dtos;

import app.entities.Song;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SongDTO {
    String id;
    @JsonProperty("name")
    String name;
    @JsonProperty("type")
    String type;
    //not fetching artist, adding the primary artist manually
    @JsonProperty("track_number")
    int songNumber;
    AlbumDTO album;

    public SongDTO(Song song) {
        this.id = song.getSongSearchId();
        this.name = song.getName();
        this.type = song.getType();
        this.album = new AlbumDTO(song.getAlbum());
    }
}