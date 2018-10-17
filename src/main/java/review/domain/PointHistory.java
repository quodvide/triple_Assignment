package review.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_point_history_user"))
    private User user;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_point_history_review"))
    private Review review;
    @Column(nullable = false)
    private int pointChange;
    @Column(nullable = false)
    private String type;

    public PointHistory(User user, Review review, int pointChange, String type) {
        this.user = user;
        this.review = review;
        this.pointChange = pointChange;
        this.type = type;
    }
}
