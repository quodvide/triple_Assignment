package review.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import review.domain.Place;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {
}
