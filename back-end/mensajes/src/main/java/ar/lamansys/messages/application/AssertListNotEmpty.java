package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.ListIsEmptyException;
import ar.lamansys.messages.domain.ProductInCartBO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssertListNotEmpty {
    public void run(List<ProductInCartBO> list) throws ListIsEmptyException {
        if (list.isEmpty()){
            throw new ListIsEmptyException();
        }
    }
}
