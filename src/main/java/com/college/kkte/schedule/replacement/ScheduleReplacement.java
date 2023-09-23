package com.college.kkte.schedule.replacement;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Date;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedule_replacement_table")
public class ScheduleReplacement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource", columnDefinition = "bytea")
    private byte[] resource;

    @Column(name = "posting_date", columnDefinition = "date")
    private Date postingDate;
}
