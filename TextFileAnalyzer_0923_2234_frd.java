// 代码生成时间: 2025-09-23 22:34:30
package com.example.textanalyzer;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.logging.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/analyze")
public class TextFileAnalyzer {
    private static final Logger log = Logger.getLogger(TextFileAnalyzer.class);

    @Inject
    @ConfigProperty(name = "text.file.path")
    String textFilePath;

    // REST endpoint to analyze a text file and return statistics
    @GET
    @Path("/{filename}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<FileAnalysisResult> analyzeFile(@PathParam String filename) {
        return Uni.createFrom().item(() -> new FileAnalysisResult()).onItem().transformToUni(fileAnalysisResult -> {
            // Update the file path if needed
            String filePath = filename != null ? filename : textFilePath;
            
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                // Analyze the file content
                int charCount = 0, wordCount = 0, lineCount = 0;
                String line;
                while ((line = reader.readLine()) != null) {
                    charCount += line.length();
                    lineCount++;
                    Matcher matcher = Pattern.compile("\b\w+\b").matcher(line);
                    while (matcher.find()) {
                        wordCount++;
                    }
                }
                // Update the result object
                fileAnalysisResult.setCharacterCount(charCount);
                fileAnalysisResult.setWordCount(wordCount);
                fileAnalysisResult.setLineCount(lineCount);
            } catch (IOException e) {
                log.error("Error reading file: " + filePath, e);
                throw new RuntimeException("Error reading file: " + filePath, e);
            }
            return Uni.createFrom().item(fileAnalysisResult);
        });
    }
}

/**
 * A simple DTO to hold the analysis results of a text file.
 */
public class FileAnalysisResult {
    private int characterCount;
    private int wordCount;
    private int lineCount;

    // Getters and setters
    public int getCharacterCount() {
        return characterCount;
    }
    public void setCharacterCount(int characterCount) {
        this.characterCount = characterCount;
    }
    public int getWordCount() {
        return wordCount;
    }
    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }
    public int getLineCount() {
        return lineCount;
    }
    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }
}