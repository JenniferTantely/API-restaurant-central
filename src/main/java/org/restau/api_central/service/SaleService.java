package org.restau.api_central.service;

import lombok.RequiredArgsConstructor;
import org.restau.api_central.dao.operations.SaleDAO;
import org.restau.api_central.model.Sale;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleDAO saleDAO;

    public List<Sale> getAllSales(Integer top) {
        Comparator<Sale> bestSalesComparator = Comparator
                .comparingInt(Sale::getQuantitySold).reversed()
                .thenComparing(Comparator.comparingDouble(Sale::getTotalAmount).reversed());

        return saleDAO.getAllSales().stream()
                .sorted(bestSalesComparator)
                .limit(top)
                .toList();
    }

}
