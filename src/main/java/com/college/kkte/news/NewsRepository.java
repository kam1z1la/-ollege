package com.college.kkte.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News,Long> {
    Optional<News> findByHeader(String header);

    Optional<News> findByCreateDateAt(LocalDate createDateAt);

    List<News> findByCreateDateAtBetween(LocalDate startDate, LocalDate endDate);
}
