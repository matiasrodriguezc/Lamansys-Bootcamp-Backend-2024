package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.CartNotExistsException;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertCartExists {
    private final CartStorage cartStorage;

    public void run(String cartId, String userId) throws CartNotExistsException {
        if (! cartStorage.exists(cartId, userId)){
            throw new CartNotExistsException();
        }
    }
}
