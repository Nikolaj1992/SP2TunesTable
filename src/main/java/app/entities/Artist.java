package app.entities;

import app.dtos.ArtistDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String type;
    @OneToMany(mappedBy = "id")
    private List<Album> albums = new ArrayList<>();

    public Artist(ArtistDTO dto){
        this.name = dto.getName();
        this.type = dto.getType();
    }

    public void addAlbum(Album album){
        if (!albums.contains(album)){
            album.setArtist(this);
            this.albums.add(album);
        }
    }
}
