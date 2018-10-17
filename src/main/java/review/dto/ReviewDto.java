package review.dto;

import lombok.Data;
import review.domain.Photo;
import review.domain.Place;
import review.domain.Review;
import review.domain.User;

import java.util.List;

@Data
public class ReviewDto {

    private String type;
    private String action;
    private Long reviewId;
    private String content;
    private List<Long> photoIds;
    private Long userId;
    private Long placeId;

    public Review toReview(User user, Place place, List<Photo> photos, boolean isFirstReview) {
        return new Review(content, user, photos, place, evaluate(isFirstReview));
    }

    private int evaluate(boolean isFirstReview) {
        int point = 0;
        if (content.length() >= 1) point++;
        if (photoIds.size() >= 1) point++;
        if (isFirstReview) point++;

        return point;
    }

    public int modifyReview(Review savedReview, List<Photo> photo) {
        int point = evaluate(false);
        savedReview.setContent(content);
        savedReview.setPhotos(photo);
        int saved = savedReview.getPoint();
        savedReview.setPoint(point);
        return point - saved;
    }
}


