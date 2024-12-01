package com.bgnc.bloodgpt.service;

import com.bgnc.bloodgpt.dto.request.ParsedBloodTestData;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bgnc.bloodgpt.enums.MessageType.*;

@Service
@Slf4j
public class FileService {

    private static final String UPLOAD_DIRECTORY = "uploads";
    private static final String NAME_PATTERN = "Adı Soyadı:\\s*(.+)";
    private static final String DATE_PATTERN = "Tarih:\\s*(.+)";

    /**
     * Uploads the file to a local directory and returns the saved file path.
     *
     * @param file the uploaded file
     * @return the path of the saved file
     */
    public String uploadFile(MultipartFile file) {
        validateFile(file);

        try {
            // Ensure the upload directory exists
            Path uploadDir = Paths.get(UPLOAD_DIRECTORY);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Create a unique file path
            String fileName = System.currentTimeMillis() + "_" + Objects.requireNonNull(file.getOriginalFilename());
            Path filePath = uploadDir.resolve(fileName);

            // Save the file to the directory
            file.transferTo(filePath.toFile());

            log.info("File successfully uploaded to {}", filePath);
            return filePath.toString();
        } catch (IOException e) {
            log.error("Error saving the file", e);
            throw new BaseException(new ErrorMessage(PROCESSING_FILE_EXCEPTION, "Error saving the file."));
        }
    }

    /**
     * Parses the uploaded PDF file and extracts relevant blood test data.
     *
     * @param file the uploaded file
     * @return parsed blood test data
     */
    public @Valid ParsedBloodTestData uploadAndParsePDF(MultipartFile file) {
        validateFile(file);

        File tempFile = createTempFile(file);

        String pdfContent = parsePDF(tempFile);
        if (!tempFile.delete()) {
            log.warn("Temporary file {} could not be deleted.", tempFile.getAbsolutePath());
        }

        return extractPatientDetails(pdfContent);
    }

    /**
     * Validates the uploaded file.
     *
     * @param file the file to validate
     */
    private void validateFile(MultipartFile file) {
        if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".pdf")) {
            throw new BaseException(new ErrorMessage(INVALID_FILE, "Uploaded file is not valid or empty."));
        }
    }

    /**
     * Creates a temporary file from the uploaded file.
     *
     * @param file the uploaded file
     * @return the temporary file
     */
    private File createTempFile(MultipartFile file) {
        try {
            File tempFile = File.createTempFile("uploaded_", ".pdf");
            file.transferTo(tempFile);
            return tempFile;
        } catch (IOException e) {
            log.error("Error creating temporary file", e);
            throw new BaseException(new ErrorMessage(PROCESSING_FILE_EXCEPTION, "Error processing the file."));
        }
    }

    /**
     * Extracts the text content from a PDF file.
     *
     * @param file the PDF file
     * @return the extracted text content
     */
    private String parsePDF(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            log.error("Failed to parse the PDF file", e);
            throw new BaseException(new ErrorMessage(FILE_NOT_FOUND_EXCEPTION, "Failed to parse the PDF file."));
        }
    }

    /**
     * Extracts patient details (e.g., name, date) from the PDF content.
     *
     * @param pdfContent the text content of the PDF file
     * @return parsed blood test data
     */
    private ParsedBloodTestData extractPatientDetails(String pdfContent) {
        String name = extractUsingPattern(pdfContent, NAME_PATTERN);
        String date = extractUsingPattern(pdfContent, DATE_PATTERN);

        log.info("Extracted Patient Name: {}", name);
        log.info("Extracted Date: {}", date);

        return new ParsedBloodTestData(name, date, pdfContent);
    }

    /**
     * Extracts data using a regex pattern.
     *
     * @param content the content to search
     * @param pattern the regex pattern
     * @return the extracted data or "Not Found" if no match is found
     */
    private String extractUsingPattern(String content, String pattern) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(content);
        return matcher.find() ? matcher.group(1).trim() : "Not Found";
    }
}