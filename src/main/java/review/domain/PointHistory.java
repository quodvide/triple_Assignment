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
    private User user;
    @ManyToOne
    private Review review;
    @Column
    private int pointChange;
    @Column
    private String type;

    public PointHistory(User user, Review review, int pointChange, String type) {
        this.user = user;
        this.review = review;
        this.pointChange = pointChange;
        this.type = type;
    }
}
