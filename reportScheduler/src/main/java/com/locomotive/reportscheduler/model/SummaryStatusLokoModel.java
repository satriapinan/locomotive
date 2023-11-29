package com.locomotive.reportscheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "summary_loco_status")
@AllArgsConstructor
@NoArgsConstructor
public class SummaryStatusLokoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String status;
    private Integer total;
    private LocalDateTime updated_at;
}

