package ar.lamansys.application;

import ar.lamansys.messages.application.AddUser;
import ar.lamansys.messages.application.AssertUserNotExists;
import ar.lamansys.messages.application.exception.messages.UserExistsException;
import ar.lamansys.messages.infrastructure.output.UserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class AddUserTest {
    @Mock
    private UserStorage userStorage;
    @Mock
    private AssertUserNotExists assertUserNotExists;

    private AddUser addUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addUser = new AddUser(userStorage,assertUserNotExists);
    }
    @Test
    void addUser_ok() throws UserExistsException {
        // Arrange
        String userId = "user1";

        // Act
        addUser.run(userId);

        // Assert
        verify(assertUserNotExists).run(userId);
        verify(userStorage).save(userId);
    }
}
