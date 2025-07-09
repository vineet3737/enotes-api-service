package com.becoder.util;

import com.becoder.entity.User;
import com.becoder.handler.GenericResponse;
import com.becoder.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtils {

    public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("failed")
                .message(message)
                .build();
        return response.create();

    }

    public static String getContentType(String originalFileName) {

        String extension = FilenameUtils.getExtension(originalFileName);

        switch (extension){
            case "pdf":
                return "application/pdf";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
            case "txt":
                return "text/plan";
            case "png":
                return "image/png";
            case "jpeg":
                return "image/jpeg";
            default:
                return "application/octet-stream";
        }
    }

    public static String getUrl(HttpServletRequest request) {
        String apiUrl = request.getRequestURL().toString(); // http://localhost:8080/api/v1/auth/
        apiUrl = apiUrl.replace(request.getServletPath(),""); // http://localhost:8080
        return apiUrl;

    }

    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .message("success")
                .data(data)
                .build();
        return response.create();

    }

    public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .message(message)
                .build();
        return response.create();

    }

    public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("failed")
                .message("failed")
                .data(data)
                .build();
        return response.create();

    }

    public static User getLoggedInUser(){
                   try{
                       CustomUserDetails logUser = (CustomUserDetails) SecurityContextHolder.getContext()
                               .getAuthentication().getPrincipal();
                       return logUser.getUser();
                   }catch(Exception e){
                             throw e;
                   }
    }
}
