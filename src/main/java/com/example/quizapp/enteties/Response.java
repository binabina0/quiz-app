package com.example.quizapp.enteties;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Data
@RequiredArgsConstructor
public class Response {
    private Integer id;
    private String response;
}
