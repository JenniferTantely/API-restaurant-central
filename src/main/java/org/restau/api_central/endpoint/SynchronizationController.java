package org.restau.api_central.endpoint;

import lombok.RequiredArgsConstructor;
import org.restau.api_central.service.SynchronizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SynchronizationController {

    private final SynchronizationService syncService;

    @PostMapping("/synchronization")
    public ResponseEntity<String> synchronizeSalesData() {
        syncService.synchronizeAllSalesPoints();
        return ResponseEntity.ok("Sales data synchronized from all sales points.");
    }
}

