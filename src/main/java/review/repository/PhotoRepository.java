package review.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import review.domain.Photo;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Long> {
}
