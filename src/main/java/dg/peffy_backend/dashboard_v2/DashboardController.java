package dg.peffy_backend.dashboard_v2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dg.peffy_backend.dashboard_v2.dto.DashboardResponse;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping ("/api/dashboard_v2")
public class DashboardController {

    private DashboardService dashboardService; 

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponse getDashboard(
            @RequestParam Integer userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return dashboardService.getDashboardResponse(userId, startDate, endDate);
    }
    
    
}
