package ru.practicum.apiError;

public class Response {
    private String message;
    private String status;

    public Response() {
    }

    public Response(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}