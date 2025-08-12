package com.achadinhos_cupons.backend_api.domain.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateValidProduct() {
        Product product = new Product(
                1L,
                "Camiseta",
                50.0,
                70.0,
                "Camiseta de algodão",
                20.0,
                "imagem.jpg"
        );

        assertEquals(1L, product.getId());
        assertEquals("Camiseta", product.getName());
        assertEquals(50.0, product.getPrice());
        assertEquals(70.0, product.getOldPrice());
        assertEquals("Camiseta de algodão", product.getDescription());
        assertEquals(20.0, product.getDiscountPercentage());
        assertEquals("imagem.jpg", product.getImage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Product product = new Product();
        assertThrows(IllegalArgumentException.class, () -> product.setName(""));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        Product product = new Product();
        assertThrows(IllegalArgumentException.class, () -> product.setPrice(-10.0));
    }

    @Test
    void shouldThrowExceptionWhenOldPriceIsNegative() {
        Product product = new Product();
        assertThrows(IllegalArgumentException.class, () -> product.setOldPrice(-5.0));
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsGreaterThan100() {
        Product product = new Product();
        assertThrows(IllegalArgumentException.class, () -> product.setDiscountPercentage(150.0));
    }

    @Test
    void shouldSetDiscountToZeroWhenNull() {
        Product product = new Product();
        product.setDiscountPercentage(null);
        assertEquals(0.0, product.getDiscountPercentage());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNegative() {
        Product product = new Product();
        assertThrows(IllegalArgumentException.class, () -> product.setId(-1L));
    }
}
