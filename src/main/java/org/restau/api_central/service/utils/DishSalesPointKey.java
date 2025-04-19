package org.restau.api_central.service.utils;

import org.restau.api_central.model.Dish;
import org.restau.api_central.model.SalesPoint;

public record DishSalesPointKey (Dish dish, SalesPoint salesPoint) {}
