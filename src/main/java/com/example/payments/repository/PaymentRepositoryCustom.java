package com.example.payments.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepositoryCustom {
    Double sumAllAmounts();
}
