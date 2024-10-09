package com.example.payments.repository;

import com.example.payments.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public PaymentRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Double sumAllAmounts() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group().sum("amount").as("totalAmount") // Group and sum the 'amount' field
        );

        AggregationResults<AggregationResult> results = mongoTemplate.aggregate(aggregation, Payment.class, AggregationResult.class);
        AggregationResult aggregationResult = results.getUniqueMappedResult();
        return aggregationResult != null ? aggregationResult.getTotalAmount() : 0.0; // Return totalAmount or 0.0 if not found
    }

    // Inner class for holding the aggregation result
    static class AggregationResult {
        private Double totalAmount;

        public Double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
        }
    }
}
