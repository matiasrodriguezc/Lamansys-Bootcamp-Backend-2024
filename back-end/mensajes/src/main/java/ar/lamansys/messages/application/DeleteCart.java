package ar.lamansys.messages.application;

import ar.lamansys.messages.infrastructure.output.CartStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteCart {
    private final CartStorage cartStorage;

    public void run(String cartId){
        cartStorage.deleteById(cartId);
    }
}
