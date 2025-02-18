package boraldan.findmax.cosysoft.controller;

import boraldan.findmax.cosysoft.controller.exception.FileProcessingException;
import boraldan.findmax.cosysoft.controller.exception.InvalidParameterException;
import boraldan.findmax.cosysoft.controller.exception.MyDataProcessingException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Hidden // делает невидимым этот контроллер для Swagger
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<String> handleFileProcessingException(FileProcessingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка обработки файла: " + ex.getMessage());
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<String> handleInvalidParameterException(InvalidParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка параметров: " + ex.getMessage());
    }

    @ExceptionHandler(MyDataProcessingException.class)
    public ResponseEntity<String> handleMyDataProcessingException(MyDataProcessingException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обработки данных: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Внутренняя ошибка сервера: " + ex.getMessage());
    }
}

