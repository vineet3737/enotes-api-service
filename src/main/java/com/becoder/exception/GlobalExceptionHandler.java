package com.becoder.exception;

import com.becoder.util.CommonUtils;
import com.becoder.util.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(Exception e){
       // return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtils.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        //return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtils.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> ResourceNotFoundException(Exception e){
       // return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        return CommonUtils.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> ValidationException(ValidationException e){
        //return new ResponseEntity<>(e.getErrors(), HttpStatus.NOT_FOUND);
        return CommonUtils.createErrorResponse(e.getErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> HttpMessageNotReadableException(HttpMessageNotReadableException e){
        //return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        return CommonUtils.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistsDataException.class)
    public ResponseEntity<?> ExistsDataException(ExistsDataException e){
        //return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        return CommonUtils.createErrorResponseMessage(e.getMessage(), HttpStatus.CONFLICT);
    }


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> ResourceNotFoundException(MethodArgumentNotValidException e){
//        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
//        Map<String, Object> error = new LinkedHashMap<>();
//          allErrors.stream().forEach(er -> {
//              String msg = er.getDefaultMessage();
//              String field = ((FieldError) (er)).getField();
//              error.put(field,msg);
//          });
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }
}
