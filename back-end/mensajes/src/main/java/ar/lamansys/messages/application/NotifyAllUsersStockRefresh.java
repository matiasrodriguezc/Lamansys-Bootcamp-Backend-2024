package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.SendUserMessage;
import ar.lamansys.messages.domain.messages.NewMessageBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NotifyAllUsersStockRefresh {

    private final SendUserMessage sendUserMessage;

    public void run(List<String> clients, String productId) throws UserSessionNotExists, UserNotExistsException {
        String text = "El producto con id:" + productId + " que estaba en tu Lista de Deseos renovó su stock y alcanza las unidades que solicitabas. Podes seguir con el proceso de compra.";
        for (String clientId : clients) {
            NewMessageBo messageBO = new NewMessageBo(clientId, text);
            sendUserMessage.run(messageBO); //se envia el mensaje
        }
    }
}
