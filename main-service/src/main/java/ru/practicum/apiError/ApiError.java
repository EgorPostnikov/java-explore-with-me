package ru.practicum.apiError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiError {
    //private List<String> errors;
    private String message;
    // private String reason;
    private String status;
    private String timestamp;
}
