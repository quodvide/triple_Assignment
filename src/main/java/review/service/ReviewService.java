package review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import review.domain.*;
import review.dto.ReviewDto;
import review.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    PointHistoryRepository pointHistoryRepository;

    public void handleEvent(ReviewDto reviewDto) {
        // 몇점인지 확인하고 알맞은 포인트 히스토리 만들어주고 리뷰 업데이트 유저 포인트 업데이트
        String type = reviewDto.getType();
        User user = userRepository.findById(reviewDto.getUserId()).get();
        Place place = placeRepository.findById(reviewDto.getPlaceId()).get();
        Boolean isFirstReview = reviewRepository.findByPlaceAndIsDeletedFalse(place).isEmpty();
        List<Long> photoIds = reviewDto.getPhotoIds();
        List<Photo> photos = new ArrayList<>();

        for (int i = 0; i < photoIds.size(); i++) {
            photos.add(photoRepository.findById(photoIds.get(i)).get());
        }

        PointHistory history;
        Review target;
        int point = 0;

        switch(type) {
            case "ADD":
                target = reviewRepository.save(reviewDto.toReview(user, place, photos, isFirstReview));
                point = target.getPoint();
                history = pointHistoryRepository.save(new PointHistory(user, target, point, type));
                break;
            case "MOD":
                target = reviewRepository.findById(reviewDto.getReviewId()).get();
                point = reviewDto.modifyReview(target, photos);
                reviewRepository.save(target);
                history = pointHistoryRepository.save(new PointHistory(user, target, point, type));
                break;
            case "DELETE":
                target = reviewRepository.findById(reviewDto.getReviewId()).get();
                point = target.getPoint()*(-1);
                target.delete();
                history = pointHistoryRepository.save(new PointHistory(user, target, point, type));
                break;
            default :
        }

        userRepository.save(userRepository.findById(reviewDto.getUserId()).get().updatePoint(point));
    }
}
