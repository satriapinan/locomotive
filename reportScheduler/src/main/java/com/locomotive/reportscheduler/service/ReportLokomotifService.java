package com.locomotive.reportscheduler.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.locomotive.reportscheduler.component.LokoSummaryBot;
import com.locomotive.reportscheduler.model.InfoLokomotifModel;
import com.locomotive.reportscheduler.model.SummaryLokoModel;
import com.locomotive.reportscheduler.model.SummaryStatusLokoModel;
import com.locomotive.reportscheduler.repository.InfoLokomotifRepository;
import com.locomotive.reportscheduler.repository.SummaryLokoRepository;
import com.locomotive.reportscheduler.repository.SummaryStatusLokoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReportLokomotifService {

    private final InfoLokomotifRepository infoLokomotifRepository;
    private final SummaryStatusLokoRepository summaryStatusLokoRepository;
    private final SummaryLokoRepository summaryLokoRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportLokomotifService.class);
    private final LokoSummaryBot telegramBot;

    @Autowired
    public ReportLokomotifService(InfoLokomotifRepository infoLokomotifRepository,
                                  SummaryStatusLokoRepository summaryStatusLokoRepository,
                                  SummaryLokoRepository summaryLokoRepository,
                                  LokoSummaryBot telegramBot) {
        this.infoLokomotifRepository = infoLokomotifRepository;
        this.summaryStatusLokoRepository = summaryStatusLokoRepository;
        this.summaryLokoRepository = summaryLokoRepository;
        this.telegramBot = telegramBot;
    }

    @Scheduled(fixedRate = 10000)
    public void LokoStatusSummary() {
        try {
            LOGGER.info("Menghitung summary status lokomotif...");
            List<InfoLokomotifModel> allInfoLokomotif = infoLokomotifRepository.findAll();

            StringBuilder summaryReport = new StringBuilder();
            summaryReport.append("Summary Report - Locomotive Status:\n\n");

            Map<String, Long> statusCounts = allInfoLokomotif.stream()
                    .filter(info -> info.getStatus() != null)
                    .collect(Collectors.groupingBy(InfoLokomotifModel::getStatus, Collectors.counting()));

            statusCounts.forEach((status, count) -> {
                SummaryStatusLokoModel existingSummary = summaryStatusLokoRepository.findByStatus(status);

                summaryReport.append("Status - ")
                        .append(status)
                        .append(" : ")
                        .append(count.intValue())
                        .append("\n");

                if (existingSummary != null) {
                    existingSummary.setTotal(count.intValue());
                    existingSummary.setUpdated_at(LocalDateTime.now());
                    summaryStatusLokoRepository.save(existingSummary);
                } else {
                    SummaryStatusLokoModel newSummary = new SummaryStatusLokoModel();
                    newSummary.setStatus(status);
                    newSummary.setTotal(count.intValue());
                    newSummary.setUpdated_at(LocalDateTime.now());
                    summaryStatusLokoRepository.save(newSummary);
                }
            });

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            summaryReport.append("\n").append("Updated at: ").append(formattedDateTime).append("\n");

            LOGGER.info("Summary status lokomotif telah diupdate.");
            telegramBot.sendSummaryReport(summaryReport.toString());

        } catch (Exception e) {
            LOGGER.error("Terjadi kesalahan saat menghitung summary status lokomotif: " + e.getMessage(), e);
        }
    }

    public List<SummaryStatusLokoModel> getStatusSummary() {
        try {
            List<SummaryStatusLokoModel> summaryList = summaryStatusLokoRepository.findAll();
            return summaryList;
        } catch (Exception e) {
            LOGGER.error("Terjadi kesalahan saat mengambil status summary: " + e.getMessage(), e);
            throw new RuntimeException("Gagal mengambil status summary: " + e.getMessage(), e);
        }
    }


    @Scheduled(fixedRate = 11000)
    public void LocomotiveSummary() {
        try {
            LOGGER.info("Membuat summary tiap lokomotif...");
            List<InfoLokomotifModel> allInfoLokomotif = infoLokomotifRepository.findAll();

            Map<String, Map<String, Long>> statusSummary = allInfoLokomotif.stream()
                    .collect(Collectors.groupingBy(
                            InfoLokomotifModel::getCode,
                            Collectors.groupingBy(
                                    InfoLokomotifModel::getStatus,
                                    Collectors.counting()
                            )
                    ));

            StringBuilder summaryReport = new StringBuilder();
            summaryReport.append("Summary Report - Locomotive Status by Code:\n");

            statusSummary.forEach((code, statusCountMap) -> {
                summaryReport.append("\n").append("Code: ").append(code).append("\n");

                statusCountMap.forEach((status, count) -> {
                    summaryReport.append("Status - ").append(status)
                            .append(" : ").append(count).append("\n");
                });

                SummaryLokoModel summary = summaryLokoRepository.findByCode(code);
                summary.setActive(statusCountMap.getOrDefault("Active", 0L).intValue());
                summary.setInactive(statusCountMap.getOrDefault("Inactive", 0L).intValue());
                summary.setUnder_maintenance(statusCountMap.getOrDefault("Under Maintenance", 0L).intValue());
                summary.setUpdated_at(LocalDateTime.now());

                summaryLokoRepository.save(summary);
            });


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            summaryReport.append("\n").append("Updated at: ").append(formattedDateTime).append("\n\n");

            LOGGER.info("Proses pembuatan summary tiap lokomotif selesai.");
            telegramBot.sendSummaryReport(summaryReport.toString());

        } catch (Exception e) {
            LOGGER.error("Terjadi kesalahan saat membuat summary tiap lokomotif: " + e.getMessage(), e);
        }
    }

    public List<SummaryLokoModel> getLocomotiveSummary() {
        try {
            List<SummaryLokoModel> summaryList = summaryLokoRepository.findAll();
            summaryList.sort(Comparator.comparing(SummaryLokoModel::getCode));
            return summaryList;
        } catch (Exception e) {
            LOGGER.error("Terjadi kesalahan saat mengambil summary lokomotif: " + e.getMessage(), e);
            throw new RuntimeException("Gagal mengambil summary lokomotif: " + e.getMessage(), e);
        }
    }


}
