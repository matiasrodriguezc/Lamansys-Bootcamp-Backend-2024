package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.domain.ProductBO;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FetchUserStock {
    private final ProductStorage productStorage;
    private final AssertUserExists assertUserExists;

    public List<ProductBO> run(String sellerId) throws UserNotExistsException {
        assertUserExists.run(sellerId);
        return productStorage.findBySeller(sellerId);
    }
}