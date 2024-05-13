package tfc.gestorRestaurante.exception.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import tfc.gestorRestaurante.exception.Pedido.PedidoNotFoundException;

import java.util.Arrays;

@ControllerAdvice
public class ControllerExceptionHandler
{
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<Response> handleValidationArgumentsErrors(PedidoNotFoundException ex) {
        Response errorResponse = new Response(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleSecurityException(Exception exception)
    {
        ProblemDetail errorDetail = null;

        // TODO send this stack trace to an observability tool
        logger.error(Arrays.toString(exception.getStackTrace()));

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value()),
                                                            exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");

            return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value()),
                                                            exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");

            return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value()),
                                                            exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");

            return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value()),
                                                            exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");

            return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value()),
                                                            exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");

            return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value()),
                                                            exception.getMessage());
            errorDetail.setProperty("description", "Unknown internal server error.");
        }

        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
