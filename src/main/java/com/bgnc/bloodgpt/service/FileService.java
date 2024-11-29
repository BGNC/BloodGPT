package com.bgnc.bloodgpt.service;

import com.bgnc.bloodgpt.dto.ParsedBloodTestData;
import com.bgnc.bloodgpt.exception.BaseException;
import com.bgnc.bloodgpt.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bgnc.bloodgpt.enums.MessageType.*;

@Service
@Slf4j
public class FileService {

    private static final String NAME_PATTERN = "Adı Soyadı:\\s*(.+)";
    private static final String DATE_PATTERN = "Tarih:\\s*(.+)";

    public @Valid ParsedBloodTestData uploadAndParsePDF(MultipartFile file) {
        validateFile(file);

        File tempFile = createTempFile(file);

        String pdfContent = parsePDF(tempFile);
        if (!tempFile.delete()) {
            log.warn("Temporary file {} could not be deleted.", tempFile.getAbsolutePath());
        }

        return extractPatientDetails(pdfContent);
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".pdf")) {
            throw new BaseException(new ErrorMessage(INVALID_FILE, "Uploaded file is not valid or empty."));
        }
    }

    private File createTempFile(MultipartFile file) {
        try {
            File tempFile = File.createTempFile("uploaded_", ".pdf");
            file.transferTo(tempFile);
            return tempFile;
        } catch (IOException e) {
            throw new BaseException(new ErrorMessage(PROCESSING_FILE_EXCEPTION, "Error processing the file."));
        }
    }

    private String parsePDF(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            throw new BaseException(new ErrorMessage(FILE_NOT_FOUND_EXCEPTION, "Failed to parse the PDF file."));
        }
    }

    private ParsedBloodTestData extractPatientDetails(String pdfContent) {
        String name = extractUsingPattern(pdfContent, NAME_PATTERN);
        String date = extractUsingPattern(pdfContent, DATE_PATTERN);

        log.info("Extracted Patient Name: {}", name);
        log.info("Extracted Date: {}", date);

        return new ParsedBloodTestData(name, date, pdfContent);
    }

    private String extractUsingPattern(String content, String pattern) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(content);
        return matcher.find() ? matcher.group(1).trim() : "Not Found";
    }
}