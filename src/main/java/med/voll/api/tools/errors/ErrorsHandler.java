package med.voll.api.tools.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldError> fieldErrors = methodArgumentNotValidException.getFieldErrors();
        return ResponseEntity.badRequest().body(fieldErrors.stream().map(ErrorHandlerDTO::new).toList());
    }

    private record ErrorHandlerDTO(String field, String message) {
        public ErrorHandlerDTO(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }

    }
}
