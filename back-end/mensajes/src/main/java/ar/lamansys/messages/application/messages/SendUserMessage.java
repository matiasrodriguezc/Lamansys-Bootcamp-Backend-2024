package ar.lamansys.messages.application.messages;

import ar.lamansys.messages.application.AssertUserExists;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.domain.messages.MessageMapper;
import ar.lamansys.messages.domain.messages.NewMessageBo;
import ar.lamansys.messages.infrastructure.output.MessageStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SendUserMessage {
    private final GetUserSession getUserSession;
    private final MessageStorage messageStorage;
    private final AssertUserExists assertUserExists;

    public void run(NewMessageBo newMessage) throws UserNotExistsException, UserSessionNotExists {
        assertUserExists.run(newMessage.getTargetId());
        var message = MessageMapper.buildMessage(
                getUserSession.run(),
                newMessage
        );
        messageStorage.save(message);
    }

}
