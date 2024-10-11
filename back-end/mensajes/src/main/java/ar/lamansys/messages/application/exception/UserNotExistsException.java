package ar.lamansys.messages.application.exception;

import lombok.Getter;

@Getter
public class UserNotExistsException extends Exception{
    String userId;

    public UserNotExistsException(String userId) {
        super(String.format("El usuario %s no existe.", userId));
        this.userId = userId;
    }
}
