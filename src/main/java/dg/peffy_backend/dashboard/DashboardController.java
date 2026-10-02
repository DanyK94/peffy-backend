package dg.peffy_backend.dashboard;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public DashboardResponse getDashboard(@PathVariable Integer userId) {
        return dashboardService.getUserDashboard(userId);
    }
    
    
}
