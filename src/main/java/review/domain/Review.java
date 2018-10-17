package review.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String content;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_review_user"))
    private User user;
    @OneToMany(mappedBy = "review")
    private List<Photo> photos;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_review_place"))
    private Place place;
    @Column
    private int point;
    @Column
    private boolean isDeleted;

    public Review(String content, User user, List<Photo> photos, Place place, int point) {
        this.content = content;
        this.user = user;
        this.photos = photos;
        this.place = place;
        this.point = point;
        isDeleted = false;
    }

    public void delete() {
        isDeleted = true;
    }
}
