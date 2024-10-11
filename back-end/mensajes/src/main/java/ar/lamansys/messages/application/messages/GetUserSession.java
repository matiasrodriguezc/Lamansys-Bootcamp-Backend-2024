package ar.lamansys.messages.application.messages;

import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.infrastructure.output.UserSessionStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetUserSession {
    private final UserSessionStorage userSessionStorage;

    public String run() throws UserSessionNotExists {
        var user = userSessionStorage.get();
        if (user.isEmpty()) {
            throw new UserSessionNotExists();
        }
        return user;
    }
}
