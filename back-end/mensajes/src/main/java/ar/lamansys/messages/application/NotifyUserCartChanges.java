package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.application.messages.SendUserMessage;
import ar.lamansys.messages.application.messages.SetUserSession;
import ar.lamansys.messages.domain.messages.NewMessageBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotifyUserCartChanges {

    private final SendUserMessage sendUserMessage;
    private final GetUserSession getUserSession;
    private final SetUserSession setUserSession;

    public void run(String text, String sellerId) throws UserNotExistsException, UserSessionNotExists {
        String userId = getUserSession.run(); //guardo mi propio usuario en una variable local
        setUserSession.run(sellerId); //seteo la sesion en el usuario del vendedor para que sea èl quien envie el mensaje
        NewMessageBo messageBO = new NewMessageBo(userId, text);
        sendUserMessage.run(messageBO); //se envia el mensaje
        setUserSession.run(userId); //la sesion vuelve a estar con mi usuario.
    }
}
