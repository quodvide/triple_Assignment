package review.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import review.domain.Place;
import review.domain.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Place> findByPlace(Place place);
    List<Place> findByPlaceAndIsDeletedFalse(Place place);
}
