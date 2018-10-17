package review.domain;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userName;
    @Column
    private int point;

    public User updatePoint(int pointChange) {
        point += pointChange;
        return this;
    }
}
