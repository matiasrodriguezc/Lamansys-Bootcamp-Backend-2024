package ar.lamansys.messages.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CartBO {
    private String clientId;
    private String sellerId;
}
