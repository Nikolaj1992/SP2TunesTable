package app.dtos;

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
public class AlbumDTO {
    @JsonProperty("name")
    String name;
    @JsonProperty("type")
    String type;
    @JsonProperty("total_tracks")
    int totalSongs;
    @JsonProperty("release_date")
    String releaseDate;
    @JsonProperty("release_date_precision")
    String releaseDatePrecision;
    @JsonProperty("artists")
    List<ArtistDTO> artists;
    @JsonProperty("tracks")
    List<SongDTO> songs;
}
