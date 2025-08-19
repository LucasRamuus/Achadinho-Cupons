package com.achadinhos_cupons.backend_api.repository;

import com.achadinhos_cupons.backend_api.domain.entities.Product;
import com.achadinhos_cupons.backend_api.infra.gateways.persistence.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test") // Usa application-test.properties com H2
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Deve salvar e buscar um produto com sucesso")
    void shouldSaveAndFindProduct() {
        Product product = new Product();
        product.setName("Produto Teste");
        product.setPrice(19.99);
        product.setDescription("Descrição do produto teste");

        Product saved = productRepository.save(product);
        Optional<Product> found = productRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Produto Teste");
        assertThat(found.get().getPrice()).isEqualTo(19.99);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar definir preço negativo")
    void shouldThrowExceptionWhenPriceIsNegative() {
        Product product = new Product();
        product.setName("Produto Inválido");

        assertThrows(IllegalArgumentException.class, () -> {
            product.setPrice(-10.0);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar produto sem nome")
    void shouldThrowExceptionWhenNameIsNullOrEmpty() {
        Product product = new Product();
        product.setPrice(9.99);
        product.setDescription("Produto sem nome");

        assertThrows(IllegalArgumentException.class, () -> {
            product.setName("");
        });
    }
}
