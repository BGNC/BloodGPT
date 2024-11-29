package com.bgnc.bloodgpt.service.impl;

import com.bgnc.bloodgpt.dto.ParsedBloodTestData;
import com.bgnc.bloodgpt.service.BloodTestAnalysisService;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BloodTestAnalysisServiceImpl implements BloodTestAnalysisService {

    private final ChatModel chatModel;


    public String analyzeBloodTest(ParsedBloodTestData parsedData) {
        String promptContent = createAnalysisPrompt(parsedData);

        Prompt prompt = new Prompt(promptContent);

        Double temperature = 0.0;
        String apiModel = "gpt-4o";

        var response = chatModel.call(new Prompt(String.valueOf(prompt),
                OpenAiChatOptions.builder()
                        .withModel(apiModel)
                        .withTemperature(temperature)
                        .build()));

        return response.getResult().getOutput().getContent();
    }

    private String createAnalysisPrompt(ParsedBloodTestData parsedData) {
        return """
                Patient Name: %s
                Test Date: %s

                I have the following blood test results:

                %s

                Please analyze these results in detail and provide the following:
                1. Identify any abnormal values and explain their implications.
                2. Suggest lifestyle changes, treatments, or further medical tests.
                3. Summarize normal results without excessive detail.
                4. Provide an overall recommendation for the patient.

                Format your response for clarity and include headings for each section.
                """.formatted(parsedData.getPatientName(), parsedData.getDate(), parsedData.getContent());
    }


}