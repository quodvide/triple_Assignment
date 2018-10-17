package review.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import review.domain.User;
import review.dto.ReviewDto;
import review.service.PointService;

import javax.servlet.http.HttpSession;

import static review.util.Util.SESSIONED_USER;

@RestController
@RequestMapping("/points")
public class ApiPointController {

    @Autowired
    PointService pointService;

    @GetMapping
    public ResponseEntity<Void> getPoint(HttpSession httpSession) {
        User user = (User)httpSession.getAttribute(SESSIONED_USER);
        pointService.getPoint(user);
        return ResponseEntity.ok().build();
    }

}
