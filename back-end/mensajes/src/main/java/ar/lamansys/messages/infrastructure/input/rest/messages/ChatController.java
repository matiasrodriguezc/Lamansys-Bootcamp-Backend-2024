package ar.lamansys.messages.infrastructure.input.rest.messages;

import ar.lamansys.messages.application.messages.FetchUserChat;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.domain.messages.ChatMessageBo;
import ar.lamansys.messages.infrastructure.output.LogUtil;
import ar.lamansys.messages.infrastructure.output.UserStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final FetchUserChat fetchUserChat;
    private final UserStorage userStorage;

    @GetMapping("/{contactId}")
    public String fetchUserChat(@PathVariable String contactId) throws UserSessionNotExists, UserNotExistsException {
        List<ChatMessageBo> chatMessages = fetchUserChat.run(contactId);
        final String sentStyle = "style=\"margin-left: auto; text-align: right;\"";
        final String contactName = userStorage.getUsername(contactId);
        String messagesHtml = chatMessages.stream()
                .map(m -> {
                    String messageText = m.getText();
                    if (m.isSent()) {
                        return "<div " + sentStyle + ">" + messageText + "</div>";
                    } else {
                        return "<div>" + messageText + "</div>";
                    }
                })
                .collect(Collectors.joining("\n"));
        return "<div style=\"text-align: center; font-weight: bold;\">" + contactName + "</div>\n" + messagesHtml;
    }

}