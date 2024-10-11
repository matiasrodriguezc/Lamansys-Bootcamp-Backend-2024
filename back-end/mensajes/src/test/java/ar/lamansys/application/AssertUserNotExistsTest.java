package ar.lamansys.application;

import ar.lamansys.messages.application.AssertUserNotExists;
import ar.lamansys.messages.application.exception.messages.UserExistsException;
import ar.lamansys.messages.infrastructure.output.UserStorage;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AssertUserNotExistsTest {

    UserStorage userStorage = Mockito.mock(UserStorage.class);
    AssertUserNotExists assertUserNotExists = new AssertUserNotExists(userStorage);


    @Test
    void userExistence_ok() {
        when(userStorage.exists("userId")).thenReturn(false);
        assertDoesNotThrow(() -> assertUserNotExists.run("userId"));
    }

    @Test
    void userExistence_notOk() {
        when(userStorage.exists("userId")).thenReturn(true);
        assertThrows(UserExistsException.class, () -> assertUserNotExists.run("userId"));
    }

}

