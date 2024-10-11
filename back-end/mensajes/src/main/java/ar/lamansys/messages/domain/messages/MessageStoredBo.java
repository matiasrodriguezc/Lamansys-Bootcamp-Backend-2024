package ar.lamansys.messages.domain.messages;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class MessageStoredBo {
    String ownerId;
    String targetId;
    String text;
}


