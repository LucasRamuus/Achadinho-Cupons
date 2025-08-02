// Verificação de inicialização
console.log("Firebase carregado:", typeof firebase !== 'undefined');
console.log("Aplicação Firebase:", firebase.apps.length > 0 ? firebase.app().name : "Não inicializada");

// Elementos da página
const form = document.getElementById("form-produto");
const listaProdutos = document.getElementById("lista-produtos");
const logoutBtn = document.getElementById("logout-btn");
const btnCancelar = document.getElementById("btn-cancelar");
const btnBuscarDados = document.getElementById("btn-buscar-dados");
const imagemPreview = document.getElementById("imagem-preview");

// Serviços do Firebase
const db = firebase.firestore();
const auth = firebase.auth();

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

// Busca de dados da Shopee (versão simplificada e robusta)
btnBuscarDados.addEventListener("click", function() {
  const linkInput = document.getElementById("link");
  const link = linkInput.value.trim();
  
  if (!link) {
    alert("Por favor, cole um link da Shopee");
    return;
  }

  btnBuscarDados.disabled = true;
  btnBuscarDados.innerHTML = "Buscando...";

  try {
    // Extração do ID do produto (funciona para shope.ee e links completos)
    const idMatch = link.match(/(?:i\.(\d+)\.(\d+))|(?:shopee\.com\.br\/.*\bi=(\d+))/i);
    if (!idMatch) throw new Error("Formato de link inválido");
    
    const itemId = idMatch[1] || idMatch[3];
    const imagemUrl = `https://cf.shopee.com.br/file/${itemId}?_=${Date.now()}`;
    
    // Atualiza a interface
    document.getElementById("imagem-url").value = imagemUrl;
    imagemPreview.innerHTML = `
      <img src="${imagemUrl}" 
           onerror="this.src='imgs/fallback.jpg'" 
           style="max-width:200px">
    `;
    
    // Sugere nome do produto
    if (!document.getElementById("nome").value) {
      const nome = link.split('/')[4]?.replace(/-/g, ' ') || "Produto Shopee";
      document.getElementById("nome").value = nome;
    }
  } catch (error) {
    console.error("Erro na busca:", error);
    alert(`Erro: ${error.message}\nUse links no formato:\nhttps://shope.ee/...\nou\nhttps://shopee.com.br/...-i.123456789.987654321`);
  } finally {
    btnBuscarDados.disabled = false;
    btnBuscarDados.innerHTML = "Buscar Dados";
  }
});

// Carregamento de produtos
function carregarProdutos() {
  listaProdutos.innerHTML = '<div class="loading">Carregando produtos...</div>';
  
  db.collection("produtos").orderBy("data_cadastro", "desc").onSnapshot({
    next: (snapshot) => {
      if (snapshot.empty) {
        listaProdutos.innerHTML = '<div class="sem-produtos">Nenhum produto cadastrado</div>';
        return;
      }
      
      listaProdutos.innerHTML = snapshot.docs.map(doc => {
        const p = doc.data();
        return `
          <div class="produto-box ${p.destaque ? 'destaque' : ''}" data-id="${doc.id}">
            <img src="${p.imagemUrl}" alt="${p.nome}" onerror="this.src='imgs/fallback.jpg'">
            <h3>${p.nome}</h3>
            <p>De: R$ ${p.preco_original.toFixed(2)}</p>
            <p>Por: R$ ${p.preco_desconto.toFixed(2)} (${p.desconto}% OFF)</p>
            <div>
              <button onclick="editarProduto('${doc.id}')">✏️ Editar</button>
              <button onclick="excluirProduto('${doc.id}')">🗑️ Excluir</button>
            </div>
          </div>
        `;
      }).join('');
    },
    error: (error) => {
      console.error("Erro no Firestore:", error);
      listaProdutos.innerHTML = '<div class="error">Erro ao carregar produtos</div>';
    }
  });
}

// Formulário de produto
form.addEventListener("submit", async (e) => {
  e.preventDefault();
  
  const produto = {
    nome: document.getElementById("nome").value.trim(),
    link_afiliado: document.getElementById("link").value.trim(),
    preco_original: parseFloat(document.getElementById("preco-original").value),
    preco_desconto: parseFloat(document.getElementById("preco-desconto").value),
    desconto: parseInt(document.getElementById("desconto").value),
    destaque: document.getElementById("destaque").checked,
    imagemUrl: document.getElementById("imagem-url").value,
    data_cadastro: new Date()
  };

  // Validações
  if (produto.preco_desconto >= produto.preco_original) {
    alert("O preço com desconto deve ser menor!");
    return;
  }

  try {
    const btn = form.querySelector(".btn-salvar");
    btn.disabled = true;
    btn.textContent = "Salvando...";
    
    const id = document.getElementById("produto-id").value;
    if (id) {
      await db.collection("produtos").doc(id).update(produto);
    } else {
      await db.collection("produtos").add(produto);
    }
    
    form.reset();
    imagemPreview.innerHTML = "";
  } catch (error) {
    console.error("Erro ao salvar:", error);
    alert("Erro ao salvar: " + error.message);
  } finally {
    const btn = form.querySelector(".btn-salvar");
    btn.disabled = false;
    btn.textContent = "Salvar Produto";
  }
});

// Funções globais
window.editarProduto = async (id) => {
  const doc = await db.collection("produtos").doc(id).get();
  const produto = doc.data();
  
  document.getElementById("produto-id").value = id;
  document.getElementById("nome").value = produto.nome;
  document.getElementById("link").value = produto.link_afiliado;
  document.getElementById("preco-original").value = produto.preco_original;
  document.getElementById("preco-desconto").value = produto.preco_desconto;
  document.getElementById("desconto").value = produto.desconto;
  document.getElementById("destaque").checked = produto.destaque;
  document.getElementById("imagem-url").value = produto.imagemUrl;
  imagemPreview.innerHTML = `<img src="${produto.imagemUrl}" style="max-width:200px">`;
  
  window.scrollTo({ top: 0, behavior: 'smooth' });
};

window.excluirProduto = async (id) => {
  if (confirm("Excluir este produto permanentemente?")) {
    await db.collection("produtos").doc(id).delete();
  }
};