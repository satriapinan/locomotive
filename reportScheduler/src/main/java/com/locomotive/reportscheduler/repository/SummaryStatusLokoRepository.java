package com.locomotive.reportscheduler.repository;

import com.locomotive.reportscheduler.model.SummaryStatusLokoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummaryStatusLokoRepository extends JpaRepository<SummaryStatusLokoModel, Integer> {
    SummaryStatusLokoModel findByStatus(String status);
}

