package app.entities;

import app.dtos.ArtistDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
    @Cascade(CascadeType.PERSIST)
    private List<Album> albums = new ArrayList<>();

    public Artist(ArtistDTO dto){
        this.name = dto.getName();
        this.type = dto.getType();
    }

    public void addAlbum(Album album, int id){
        if (!albums.contains(album)){
            album.setId(this.id + "-" + String.valueOf(id+1));
            album.setArtist(this);
            this.albums.add(album);
        }
    }
}
