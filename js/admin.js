// Configuração do Firebase
const db = firebase.firestore();
const auth = firebase.auth();

// Elementos da página
const form = document.getElementById("form-produto");
const listaProdutos = document.getElementById("lista-produtos");
const logoutBtn = document.getElementById("logout-btn");
const btnCancelar = document.getElementById("btn-cancelar");
const btnBuscarDados = document.getElementById("btn-buscar-dados");
const imagemPreview = document.getElementById("imagem-preview");

// Verificação de autenticação
auth.onAuthStateChanged((user) => {
  if (!user) {
    window.location.href = "login.html";
  } else {
    console.log("Usuário logado:", user.email);
    carregarProdutos();
  }
});

// Logout
logoutBtn.addEventListener("click", () => {
  if (confirm("Deseja realmente sair do painel admin?")) {
    auth.signOut().then(() => {
      window.location.href = "login.html";
    });
  }
});

// Função para buscar dados da Shopee (ATUALIZADA E TESTADA)
btnBuscarDados.addEventListener("click", async function() {
  const linkInput = document.getElementById("link");
  const link = linkInput.value.trim();
  
  if (!link) {
    alert("Por favor, cole um link da Shopee");
    linkInput.focus();
    return;
  }

  // Mostra loading
  const btnTextoOriginal = btnBuscarDados.innerHTML;
  btnBuscarDados.disabled = true;
  btnBuscarDados.innerHTML = '<span>Buscando...</span>';

  try {
    // Extrai o ID do produto de links encurtados ou completos
    let itemId;
    
    // Padrão para links encurtados (shope.ee)
    if (link.includes("shope.ee")) {
      const response = await fetch(`https://api.allorigins.win/get?url=${encodeURIComponent(link)}`);
      const data = await response.json();
      const html = data.contents;
      
      // Extrai o ID do produto do HTML
      const idMatch = html.match(/i\.(\d+)\.(\d+)/);
      if (!idMatch) throw new Error("ID não encontrado no link");
      itemId = idMatch[1];
    } 
    // Padrão para links completos (shopee.com.br)
    else if (link.includes("shopee.com.br")) {
      const idMatch = link.match(/i\.(\d+)\.(\d+)/);
      if (!idMatch) throw new Error("Formato de link inválido");
      itemId = idMatch[1];
    } else {
      throw new Error("Link não reconhecido");
    }

    // Gera URL da imagem
    const imagemUrl = `https://cf.shopee.com.br/file/${itemId}`;
    
    // Atualiza a interface
    document.getElementById("imagem-url").value = imagemUrl;
    imagemPreview.innerHTML = `
      <img src="${imagemUrl}" 
           alt="Preview do produto"
           onerror="this.src='imgs/fallback.jpg'"
           style="max-width: 100%">
    `;
    
    // Sugere nome do produto
    if (!document.getElementById("nome").value) {
      const nomeSugerido = link.split('/')[4]?.replace(/-/g, ' ') || "Produto Shopee";
      document.getElementById("nome").value = nomeSugerido;
    }

  } catch (error) {
    console.error("Erro ao buscar dados:", error);
    alert(`Não foi possível extrair dados do link. Erro: ${error.message}`);
  } finally {
    btnBuscarDados.disabled = false;
    btnBuscarDados.innerHTML = btnTextoOriginal;
  }
});

// Carrega produtos cadastrados
function carregarProdutos() {
  listaProdutos.innerHTML = '<div class="loading">Carregando produtos...</div>';
  
  db.collection("produtos").orderBy("data_cadastro", "desc").onSnapshot((snapshot) => {
    if (snapshot.empty) {
      listaProdutos.innerHTML = '<div class="sem-produtos">Nenhum produto cadastrado ainda.</div>';
      return;
    }

    listaProdutos.innerHTML = snapshot.docs.map(doc => {
      const produto = doc.data();
      return `
        <div class="produto-box ${produto.destaque ? 'destaque' : ''}" data-id="${doc.id}">
          <div class="produto-imagem">
            <img src="${produto.imagemUrl}" alt="${produto.nome}" onerror="this.src='imgs/fallback.jpg'">
            ${produto.destaque ? '<span class="badge-destaque">⭐ Destaque</span>' : ''}
          </div>
          <div class="produto-info">
            <h3>${produto.nome}</h3>
            <div class="produto-precos">
              <span class="preco-original">R$ ${produto.preco_original.toFixed(2)}</span>
              <span class="preco-desconto">R$ ${produto.preco_desconto.toFixed(2)}</span>
              <span class="desconto">${produto.desconto}% OFF</span>
            </div>
            <div class="produto-acoes">
              <button onclick="editarProduto('${doc.id}')" class="btn-editar">✏️ Editar</button>
              <button onclick="excluirProduto('${doc.id}')" class="btn-excluir">🗑️ Excluir</button>
            </div>
          </div>
        </div>
      `;
    }).join("");
  }, error => {
    console.error("Erro ao carregar produtos:", error);
    listaProdutos.innerHTML = '<div class="error">Erro ao carregar produtos. Recarregue a página.</div>';
  });
}

// Formulário de produto
form.addEventListener("submit", async (e) => {
  e.preventDefault();
  
  // Validações
  const precoOriginal = parseFloat(document.getElementById("preco-original").value);
  const precoDesconto = parseFloat(document.getElementById("preco-desconto").value);
  const imagemUrl = document.getElementById("imagem-url").value;

  if (precoDesconto >= precoOriginal) {
    alert("O preço com desconto deve ser menor que o original!");
    return;
  }

  if (!imagemUrl.includes("http")) {
    alert("URL da imagem inválida! Use o botão 'Buscar Dados' para gerar automaticamente.");
    return;
  }

  // Dados do produto
  const produto = {
    nome: document.getElementById("nome").value.trim(),
    link_afiliado: document.getElementById("link").value.trim(),
    preco_original: precoOriginal,
    preco_desconto: precoDesconto,
    desconto: parseInt(document.getElementById("desconto").value),
    destaque: document.getElementById("destaque").checked,
    imagemUrl: imagemUrl,
    data_cadastro: new Date()
  };

  try {
    const produtoId = document.getElementById("produto-id").value;
    const btnSalvar = form.querySelector(".btn-salvar");
    const btnTextoOriginal = btnSalvar.innerHTML;
    
    btnSalvar.disabled = true;
    btnSalvar.innerHTML = '<span>Salvando...</span>';

    if (produtoId) {
      // Atualiza produto existente
      await db.collection("produtos").doc(produtoId).update(produto);
    } else {
      // Adiciona novo produto
      await db.collection("produtos").add(produto);
    }
    
    // Limpa o formulário
    form.reset();
    document.getElementById("produto-id").value = "";
    imagemPreview.innerHTML = "";
    
  } catch (error) {
    console.error("Erro ao salvar produto:", error);
    alert("Erro ao salvar produto: " + error.message);
  } finally {
    const btnSalvar = form.querySelector(".btn-salvar");
    btnSalvar.disabled = false;
    btnSalvar.innerHTML = btnTextoOriginal;
  }
});

// Funções globais para edição/exclusão
window.editarProduto = async (id) => {
  try {
    const doc = await db.collection("produtos").doc(id).get();
    if (!doc.exists) throw new Error("Produto não encontrado");

    const produto = doc.data();
    
    // Preenche o formulário
    document.getElementById("produto-id").value = id;
    document.getElementById("nome").value = produto.nome;
    document.getElementById("link").value = produto.link_afiliado;
    document.getElementById("preco-original").value = produto.preco_original;
    document.getElementById("preco-desconto").value = produto.preco_desconto;
    document.getElementById("desconto").value = produto.desconto;
    document.getElementById("destaque").checked = produto.destaque;
    document.getElementById("imagem-url").value = produto.imagemUrl;
    imagemPreview.innerHTML = `<img src="${produto.imagemUrl}" alt="Preview" style="max-width: 100%">`;
    
    window.scrollTo({ top: 0, behavior: "smooth" });
  } catch (error) {
    console.error("Erro ao editar produto:", error);
    alert("Não foi possível carregar o produto para edição");
  }
};

window.excluirProduto = async (id) => {
  if (!confirm("Tem certeza que deseja excluir este produto permanentemente?")) return;
  
  try {
    await db.collection("produtos").doc(id).delete();
  } catch (error) {
    console.error("Erro ao excluir produto:", error);
    alert("Erro ao excluir produto: " + error.message);
  }
};