package com.bgnc.bloodgpt.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "blood_tests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BloodTest extends BaseEntity{



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Tahlili yükleyen kullanıcı

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor requestedBy; // Tahlili isteyen doktor

    @Column(nullable = false)
    private String filePath; // PDF dosyasının saklandığı yol

    @Column(nullable = false, columnDefinition = "TEXT")
    private String aiComment; // Yapay zekâ tarafından üretilen yorum

    @Column(columnDefinition = "TEXT")
    private String doctorComment; // Doktor tarafından yazılan manuel yorum (isteğe bağlı)



    @Column(nullable = false)
    private LocalDateTime uploadTime; // Tahlilin sisteme yüklenme zamanı
}
