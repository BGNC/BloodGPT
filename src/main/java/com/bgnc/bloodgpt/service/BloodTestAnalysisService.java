package com.bgnc.bloodgpt.service;

import com.bgnc.bloodgpt.dto.ParsedBloodTestData;

public interface BloodTestAnalysisService {

    /**
     * Analyzes blood test data using ChatGPT.
     *
     * @param bloodTestData Extracted blood test data as a String.
     * @return Analysis of the blood test data.
     */
    String analyzeBloodTest(ParsedBloodTestData bloodTestData);
}