package com.example.demo;

import java.util.List;

public record Faq(String intent, List<String> keywords, String answer) {}