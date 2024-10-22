package app.entities;

import app.dtos.AlbumDTO;
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
    private Long id;
    private String name;
    private String type;
    @OneToMany(mappedBy = "artist")
    @Cascade(CascadeType.PERSIST)
    private List<Album> albums = new ArrayList<>();

    public Artist(ArtistDTO dto){
        this.name = dto.getName();
        this.type = dto.getType();
    }
    public Artist ArtistWithID(ArtistDTO dto){ //use this to convert from dto to entity
        this.id = Long.valueOf(dto.getId());
        this.name = dto.getName();
        this.type = dto.getType();
        return this;
    }

    public void addAlbum(AlbumDTO albumDTO, int id){
        Album album = new Album(albumDTO);
        if (!albums.contains(album)){
            album.setId(this.id + "-" + String.valueOf(id+1));
            album.addSongs(albumDTO.getTracks().getSongs());
            album.setArtist(this);
            this.albums.add(album);
        }
    }
}
