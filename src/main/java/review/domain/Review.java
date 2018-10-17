package review.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_review_user"))
    private User userId;
    @OneToMany(mappedBy = "review")
    private List<Photo> photos;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_review_place"))
    private Place placeId;
    @Column
    private int point;
    @Column
    private boolean isDeleted;

    public Review(String content, User userId, List<Photo> photos, Place placeId, int point) {
        this.content = content;
        this.userId = userId;
        this.photos = photos;
        this.placeId = placeId;
        this.point = point;
        isDeleted = false;
    }

    public void delete() {
        isDeleted = true;
    }
}
