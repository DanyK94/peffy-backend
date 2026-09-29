package dg.peffy_backend.dashboard;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping ("/api/dashboard")
public class DashboardController {

    private DashboardService dashboardService; 

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/{userId}")
    public List<DashboardResponse> getDashboard(@PathVariable Integer userId) {
        return dashboardService.defineUserDashboard(userId);
    }
    
    
}
