package ar.lamansys.messages.domain.messages;

import lombok.Value;

@Value
public class NewMessageBo {
    String targetId;
    String text;
}
