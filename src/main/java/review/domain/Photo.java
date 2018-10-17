package review.domain;

import javax.persistence.*;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_photo_review"))
    private Review review;

}
