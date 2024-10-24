package app.dtos;

import app.entities.Album;
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
public class AlbumDTO {
    String id;
    @JsonProperty("name")
    String name;
    @JsonProperty("type")
    String type;
    @JsonProperty("total_tracks")
    int totalSongs;
    @JsonProperty("release_date")
    String releaseDate;
    @JsonProperty("artists")
    List<ArtistDTO> artists = new ArrayList<>();
    @JsonProperty("tracks")
    TracksDTO tracks;

    public AlbumDTO(Album album) {
        this.id = album.getAlbumSearchId();
        this.name = album.getName();
        this.type = album.getType();
        this.totalSongs = album.getTotalSongs();
        this.releaseDate = album.getReleaseDate();
        List<ArtistDTO> artist = List.of(new ArtistDTO(album.getArtist()))
        if (!this.artists.contains(artist.get(0))) {
        this.artists = artist;
        }
        if (!album.getSongs().isEmpty()) {
        this.tracks = new TracksDTO(album.getSongs().stream().map(song -> new SongDTO(song)).toList());
        }
    }

}
