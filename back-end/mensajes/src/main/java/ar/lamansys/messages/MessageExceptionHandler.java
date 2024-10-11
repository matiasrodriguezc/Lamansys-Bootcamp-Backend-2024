package ar.lamansys.messages;

import ar.lamansys.messages.application.exception.*;
import ar.lamansys.messages.application.exception.messages.UserExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MessageExceptionHandler {

    @ExceptionHandler({UserNotExistsException.class, UserSessionNotExists.class})
    public ResponseEntity<String> notExistsHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<String> existsHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CartNotExistsException.class)
    public ResponseEntity<String> cartNotExistsHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ListIsEmptyException.class)
    public ResponseEntity<String> listIsEmptyHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<String> notEnoughStockHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ProductInCartNotExistsException.class)
    public ResponseEntity<String> productInCartNotExistsHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProductNotExistsException.class)
    public ResponseEntity<String> productNotExistsHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
