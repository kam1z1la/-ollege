package com.college.kkte.schedule.replacement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleReplacementRepository extends JpaRepository<ScheduleReplacement, Long> {
    @Query("SELECT sr FROM ScheduleReplacement sr WHERE sr.postingDate = :postingDate")
    List<ScheduleReplacement> getAllByPostingDate(@Param("postingDate") Date postingDate);
}
