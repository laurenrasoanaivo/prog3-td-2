package app.foot.controller;

import app.foot.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@ControllerAdvice
public class BaseController {

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> handleException() {
        log.info("A bad request exception was handled");
        return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
    }
}

