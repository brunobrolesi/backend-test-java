package br.com.brunobrolesi.parking.controller;

import br.com.brunobrolesi.parking.controller.dto.ErrorDto;
import br.com.brunobrolesi.parking.controller.dto.FormValidationErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionController {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FormValidationErrorDto> validationHandler(MethodArgumentNotValidException exception) {
        List<FormValidationErrorDto> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            FormValidationErrorDto error = new FormValidationErrorDto(e.getField(), message);
            dto.add(error);
        });

        return dto;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ErrorDto ExceptionHandler(RuntimeException exception) {
        ErrorDto error = new ErrorDto(exception.getMessage());
        return error;
    }
}
