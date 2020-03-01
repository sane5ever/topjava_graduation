package ru.javaops.restaurantvoting.web;

import one.util.streamex.StreamEx;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.javaops.restaurantvoting.util.ValidationUtil;
import ru.javaops.restaurantvoting.util.exception.NotFoundException;

import javax.lang.model.type.ErrorType;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionInfoHandler {
    @ExceptionHandler(NotFoundException.class)
    public Map<String, Object> applicationError(NotFoundException e) {
        return Map.of("message", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Map<String, Object> bindValidationError(Exception ex) {
        var result = ex instanceof BindException ? ((BindException) ex).getBindingResult()
                : ((MethodArgumentNotValidException) ex).getBindingResult();
        var errors = StreamEx.of(result.getFieldErrors())
                .toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage);
        return Map.of("message", "There're validation errors",
                "details", errors);
    }
}
