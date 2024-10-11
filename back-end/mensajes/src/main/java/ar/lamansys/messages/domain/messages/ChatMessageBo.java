package ar.lamansys.messages.domain.messages;

import lombok.Value;

@Value
public class ChatMessageBo {
    boolean isSent;
    String text;
}
