package ar.lamansys.application;

import ar.lamansys.messages.application.AssertProductExists;
import ar.lamansys.messages.application.exception.ProductNotExistsException;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AssertProductExistsTest {
    @Mock
    private ProductStorage productStorage;

    private AssertProductExists assertProductExists;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        assertProductExists = new AssertProductExists(productStorage);
    }
    @Test
    void productExists_ok() throws ProductNotExistsException {
        String productId = "product1";
        String sellerId = "seller1";

        when(productStorage.exists(productId, sellerId)).thenReturn(true);

        assertDoesNotThrow(() -> assertProductExists.run(productId, sellerId));

        verify(productStorage).exists(productId,sellerId);

    }
}
