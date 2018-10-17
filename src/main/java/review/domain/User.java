package review.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String userName;
    @Column
    private int point;

    public User updatePoint(int pointChange) {
        point += pointChange;
        return this;
    }
}

