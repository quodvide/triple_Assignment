package review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import review.domain.PointHistory;
import review.domain.User;
import review.repository.PointHistoryRepository;

import java.util.List;

@Service
public class PointService {

    @Autowired
    PointHistoryRepository pointHistoryRepository;

    public int getPoint(User user) {
        return user.getPoint();
    }

    public List<PointHistory> getPointDetail(User user) {
        return pointHistoryRepository.findByUser(user);
    }
}
