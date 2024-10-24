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
    String songSearchId;
    @JsonProperty("name")
    String name;
    @JsonProperty("type")
    String type;
    //not fetching artist, adding the primary artist manually
    @JsonProperty("track_number")
    int songNumber;
    Integer albumId;
    Integer artistId;

    public SongDTO(Song song) {
        this.id = String.valueOf(song.getId());
        this.songSearchId = song.getSongSearchId();
        this.name = song.getName();
        this.type = song.getType();
        this.albumId = song.getAlbum().getId();
        this.artistId = song.getArtist().getId();
    }
}