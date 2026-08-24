import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/examples")
class TaskControllerExample {

    @GetMapping
    List<String> findAll() {
        return List.of("Comprendre une route GET");
    }
}
