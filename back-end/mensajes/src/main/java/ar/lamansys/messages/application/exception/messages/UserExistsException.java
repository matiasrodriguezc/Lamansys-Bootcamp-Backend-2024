package ar.lamansys.messages.application.exception.messages;

import lombok.Getter;

@Getter
public class UserExistsException extends Throwable {
    String userId;

    public UserExistsException(String userId) {
        super(String.format("El usuario %s ya existe", userId));
        this.userId = userId;
    }
}
