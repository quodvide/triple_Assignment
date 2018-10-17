package review.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import review.domain.PointHistory;
import review.domain.User;

import java.util.List;

@Repository
public interface PointHistoryRepository extends CrudRepository<PointHistory, Long> {
    List<PointHistory> findByUser(User user);
}
