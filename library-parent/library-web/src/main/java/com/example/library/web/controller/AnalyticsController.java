// package com.example.library.web.controller;

// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/analytics")
// public class AnalyticsController {

//     @GetMapping("/audit")
//     @PreAuthorize("hasRole('MEMBER')")
//     public ResponseEntity<String> audit() {
//         return ResponseEntity.ok("audit-ok");
//     }
// }
package com.example.library.web.controller;

import com.example.library.domain.enums.BookStatus;
import com.example.library.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/audit")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<Map<BookStatus, Long>> audit() {
        return ResponseEntity.ok(analyticsService.auditBooksByStatus());
    }
}

