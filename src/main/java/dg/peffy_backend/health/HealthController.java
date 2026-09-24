package dg.peffy_backend.health;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;


@RestController 
public class HealthController {

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "application", "Peffy"
        );
    }
    
    
}
