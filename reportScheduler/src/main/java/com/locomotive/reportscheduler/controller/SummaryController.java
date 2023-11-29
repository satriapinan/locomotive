package com.locomotive.reportscheduler.controller;

import com.locomotive.reportscheduler.model.SummaryLokoModel;
import com.locomotive.reportscheduler.model.SummaryStatusLokoModel;
import com.locomotive.reportscheduler.service.ReportLokomotifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/summary")
public class SummaryController {

    @Autowired
    private ReportLokomotifService reportLokomotifService;

    @GetMapping("/status")
    public ResponseEntity<?> getStatusSummary() {
        try {
            List<SummaryStatusLokoModel> statusSummary = reportLokomotifService.getStatusSummary();
            return ResponseEntity.ok(statusSummary);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan saat mengambil status summary: " + e.getMessage());
        }
    }

    @GetMapping("/locomotives")
    public ResponseEntity<?> getLocomotiveSummary() {
        try {
            List<SummaryLokoModel> summaryLokoModels = reportLokomotifService.getLocomotiveSummary();
            return ResponseEntity.ok(summaryLokoModels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan saat mengambil data: " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan pada server: " + e.getMessage());
    }
}


