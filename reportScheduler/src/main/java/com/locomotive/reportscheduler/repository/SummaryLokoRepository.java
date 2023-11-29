package com.locomotive.reportscheduler.repository;

import com.locomotive.reportscheduler.model.SummaryLokoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummaryLokoRepository extends JpaRepository<SummaryLokoModel, String> {
    SummaryLokoModel findByCode(String code);
}
