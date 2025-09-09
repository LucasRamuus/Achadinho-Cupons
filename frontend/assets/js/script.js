document.addEventListener('DOMContentLoaded', async function() {
    const abas = document.querySelectorAll('[data-aba]');

    // Controle das abas
    abas.forEach(aba => {
        aba.addEventListener('click', function() {
            document.querySelectorAll('.abas-navegacao button').forEach(b => b.classList.remove('aba-ativo'));
            document.querySelectorAll('.aba-conteudo').forEach(c => c.classList.remove('aba-ativa'));
            this.classList.add('aba-ativo');
            const abaAlvo = this.getAttribute('data-aba');
            document.getElementById(abaAlvo).classList.add('aba-ativa');
        });
    });

    // ======== BUSCAR PRODUTOS DA API ========
    async function carregarProdutos() {
        try {
            const response = await fetch("http://localhost:8080/products"); // ajuste se for deploy
            const produtos = await response.json();

            const gridDestaques = document.querySelector("#destaques .produtos-grid");
            const gridMaisOfertas = document.querySelector("#mais-ofertas .produtos-grid");

            gridDestaques.innerHTML = "";
            gridMaisOfertas.innerHTML = "";

            produtos.forEach(produto => {
                // Tratamento de dados
                const nome = (produto.name || "").replace(/"/g, "");
                const link = (produto.affiliateLink || "#").replace(/"/g, "");
                const precoAntigo = produto.oldPrice ? `R$ ${produto.oldPrice.toFixed(2)}` : "";
                const precoAtual = produto.price ? `R$ ${produto.price.toFixed(2)}` : "";
                const desconto = produto.discountPercentage ? `${produto.discountPercentage}% OFF` : "";

                // Criação do card do produto
                const div = document.createElement("div");
                div.classList.add("produto");

                div.innerHTML = `
                    ${desconto ? `<div class="badge">${desconto}</div>` : ""}
                    <img src="${produto.image}" alt="${nome}">
                    <div class="produto-info">
                        <h3>${nome}</h3>
                        <div class="precos">
                            ${precoAntigo ? `<span class="preco-antigo">${precoAntigo}</span>` : ""}
                            ${precoAtual ? `<span class="preco-atual">${precoAtual}</span>` : ""}
                        </div>
                        <a href="${link}" target="_blank" class="btn-comprar">
                            <i class="fas fa-shopping-cart"></i> Comprar Agora
                        </a>
                    </div>
                `;

                // Decide onde exibir
                if (produto.featured) {
                    gridDestaques.appendChild(div);
                } else {
                    gridMaisOfertas.appendChild(div);
                }
            });

        } catch (error) {
            console.error("Erro ao carregar produtos:", error);
        }
    }

    carregarProdutos();
});
