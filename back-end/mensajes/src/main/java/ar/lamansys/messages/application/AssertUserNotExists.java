package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.messages.UserExistsException;
import ar.lamansys.messages.infrastructure.output.UserStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertUserNotExists {
    private UserStorage userStorage;

    public void run(String userId) throws UserExistsException {
        if ( userStorage.exists(userId) ) {
            throw new UserExistsException(userId);
        }
    }
}
