package review.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import review.dto.ReviewDto;
import review.service.ReviewService;

@RestController
@RequestMapping("/events")
public class ApiReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> eventsHandle(@RequestBody ReviewDto reviewDto) {
        reviewService.handleEvent(reviewDto);
        return ResponseEntity.ok().build();
    }
}
