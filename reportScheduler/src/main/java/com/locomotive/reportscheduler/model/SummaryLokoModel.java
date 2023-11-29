package com.locomotive.reportscheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "summary_loco")
@AllArgsConstructor
@NoArgsConstructor
public class SummaryLokoModel {
    @Id
    private String code;
    private String name;
    private Integer active;
    private Integer inactive;
    private Integer under_maintenance;
    private LocalDateTime updated_at;
}
