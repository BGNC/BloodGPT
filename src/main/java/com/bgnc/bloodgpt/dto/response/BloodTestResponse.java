package com.bgnc.bloodgpt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BloodTestResponse {

    private Long id; // Tahlil ID'si
    private String filePath; // PDF dosyasının saklandığı yol
    private String aiComment; // Yapay Zekâ yorumu
    private String doctorComment; // Doktorun yorumu
    private LocalDateTime uploadTime; // Tahlilin yüklendiği zaman
    private String requestedBy; // Tahlili isteyen doktorun adı (opsiyonel)


}