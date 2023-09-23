package com.college.kkte.news;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news")
@ToString
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String header;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "create_time_at", nullable = false)
    private LocalTime createTimeAt;

    @Column(name = "create_date_at", nullable = false)
    private LocalDate createDateAt;

//    @Lob
    private byte[] photo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News news)) return false;
        return Objects.equals(id, news.id) && Objects.equals(header, news.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, header);
    }
}
