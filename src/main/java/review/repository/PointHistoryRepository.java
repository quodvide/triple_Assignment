package review.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import review.domain.PointHistory;

@Repository
public interface PointHistoryRepository extends CrudRepository<PointHistory, Long> {
}
