package org.restau.api_central.endpoint;

import lombok.RequiredArgsConstructor;
import org.restau.api_central.endpoint.mapper.SaleElementMapper;
import org.restau.api_central.endpoint.rest.BestSalesResponse;
import org.restau.api_central.endpoint.rest.SalesElement;
import org.restau.api_central.exception.ClientException;
import org.restau.api_central.exception.NotFoundException;
import org.restau.api_central.exception.ServerException;
import org.restau.api_central.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;
    private final SaleElementMapper saleElementMapper;

    @GetMapping("/bestSales")
    public ResponseEntity<Object> getBestSales(@RequestParam(name = "top") Integer top) {
        if(top == null) {
            return ResponseEntity.badRequest().body("No top value provided");
        }
        try {
            List<SalesElement> bestSales = saleService.getAllSales(top).stream()
                    .map(saleElementMapper::toRest)
                    .toList();

            BestSalesResponse response = new BestSalesResponse();
            response.setUpdatedAt(Instant.now());
            response.setSales(bestSales);
            return ResponseEntity.ok().body(response);
        }
        catch (ServerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        catch (ClientException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
