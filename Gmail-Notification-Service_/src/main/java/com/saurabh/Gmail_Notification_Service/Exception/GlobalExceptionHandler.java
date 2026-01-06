package com.saurabh.Gmail_Notification_Service.Exception;

import com.saurabh.Gmail_Notification_Service.Dto.EmailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handelValidationException(MethodArgumentNotValidException ex){
        Map<String,String>map=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError)error).getField();
            String errorMessage=error.getDefaultMessage();
            map.put(fieldName,errorMessage);
        });
        logger.error("Validation error:"+ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<EmailResponse> handelRuntimeException(RuntimeException ex){
        logger.error("Runtime error occurred:"+ex.getMessage()+" "+ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new EmailResponse("Failed:",ex.getMessage(),true));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EmailResponse> handelException(Exception ex){
        logger.error("Exception has occurred:"+ex.getMessage()+" "+ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new EmailResponse("Failed","An unexpected error has occurred",true));
    }
}
