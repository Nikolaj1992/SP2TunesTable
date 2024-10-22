package app.dtos;

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
    @JsonProperty("name")
    String name;
    @JsonProperty("type")
    String type;
    //not fetching artist, adding the primary artist manually
    @JsonProperty("track_number")
    int songNumber;
}