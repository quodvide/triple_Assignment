package review.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import review.domain.PointHistory;
import review.domain.User;
import review.service.PointService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static review.util.Util.SESSIONED_USER;

@RestController
@RequestMapping("/points")
public class ApiPointController {

    @Autowired
    PointService pointService;

    @GetMapping
    public ResponseEntity<Integer> getPoint(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(SESSIONED_USER);
        int point = pointService.getPoint(user);
        return ResponseEntity.ok(point);
    }

    @GetMapping("/details")
    public ResponseEntity<List<PointHistory>> getPointDetail(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(SESSIONED_USER);
        List<PointHistory> detail = pointService.getPointDetail(user);
        return ResponseEntity.ok(detail);
    }

}
