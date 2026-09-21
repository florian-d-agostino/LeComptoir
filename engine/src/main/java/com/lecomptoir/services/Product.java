package com.lecomptoir.services;
import java.math.BigDecimal;

import com.lecomptoir.services.enums.Category;





public record Product(String reference, String label, BigDecimal unitPrice, Category category) {}