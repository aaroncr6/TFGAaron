package tfc.gestorRestaurante.exception.User;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Data
public class Response
{
    private int code;
    private String message;
    private Map<String, String> errors;

    public Response(int errorCode, String errorMessage) {
        code = errorCode;
        message = errorMessage;
        errors = new HashMap<>();
    }

    public Response(int code, String message, Map<String, String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public static Response noErrorResponse(String message)
    {
        return new Response(HttpStatus.OK.value(), message);
    }

    public static Response generalError(int code, String message) {
        return new Response(code, message);
    }

    public static Response generalError(Map<String, String> errors) {
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error", errors);
    }
}
