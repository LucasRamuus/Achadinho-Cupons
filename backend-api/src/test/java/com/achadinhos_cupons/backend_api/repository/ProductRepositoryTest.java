package com.achadinhos_cupons.backend_api.repository;

import com.achadinhos_cupons.backend_api.domain.entities.Product;
import com.achadinhos_cupons.backend_api.infra.gateways.persistence.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") // Vai usar application-test.properties
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndFindProduct() {
        // Criar produto
        Product product = new Product();
        product.setName("Produto Teste");
        product.setPrice(19.99);
        product.setDescription("Descrição do produto teste");

        // Salvar no H2
        Product saved = productRepository.save(product);

        // Buscar pelo ID
        Optional<Product> found = productRepository.findById(saved.getId());

        // Validar
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Produto Teste");
        assertThat(found.get().getPrice()).isEqualTo(19.99);
    }
}