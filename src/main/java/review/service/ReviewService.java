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
        String type = reviewDto.getType(); // switch문을 위한 type

        User user = userRepository.findById(reviewDto.getUserId()).get(); // 유저가져오기
        Place place = placeRepository.findById(reviewDto.getPlaceId()).get(); // 장소가져오기
        Boolean isFirstReview = reviewRepository.findByPlaceAndIsDeletedFalse(place).isEmpty(); // 보너스점수를 위함
        List<Long> photoIds = reviewDto.getPhotoIds();
        List<Photo> photos = new ArrayList<>();

        for (int i = 0; i < photoIds.size(); i++) {
            photos.add(photoRepository.findById(photoIds.get(i)).get());
        } // photoIds에 있는 id에 해당하는 사진들 리스트에 저장

        PointHistory history; // 이 결과로 만들어질 포인트히스토리
        Review target; // 저장할 타겟
        int point = 0; // 히스토리에 저장할 포인트 증가/감소값

        switch (type) {
            case "ADD": // Dto로 부터 리뷰 만들어 저장, point에는 그 리뷰에 해당하는 점수
                target = reviewRepository.save(reviewDto.toReview(user, place, photos, isFirstReview));
                point = target.getPoint();
                history = pointHistoryRepository.save(new PointHistory(user, target, point, type));
                break;
            case "MOD": // 해당 reviewId로 이전 리뷰 불러옴, 현재 리뷰로 update후 차이를 point에 저장, 리뷰 다시 저장
                target = reviewRepository.findById(reviewDto.getReviewId()).get();
                point = reviewDto.modifyReview(target, photos);
                reviewRepository.save(target);
                history = pointHistoryRepository.save(new PointHistory(user, target, point, type));
                break;
            case "DELETE": // 해당 reviewId로 이전 리뷰 불러옴, 삭제할것이므로 해당리뷰 점수 -1로 point저장, 리뷰에 isDeleted를 true로
                target = reviewRepository.findById(reviewDto.getReviewId()).get();
                point = target.getPoint() * (-1);
                target.delete();
                history = pointHistoryRepository.save(new PointHistory(user, target, point, type));
                break;
            default:
        }

        userRepository.save(userRepository.findById(reviewDto.getUserId()).get().updatePoint(point));
        // 최종적으로 해당 유저에 해당 point(포인트 증가, 감소값)만큼 포인트 업데이트
    }
}
