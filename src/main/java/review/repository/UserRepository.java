package review.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import review.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
