// Configurações
const db = firebase.firestore();
const auth = firebase.auth();

// Elementos
const form = document.getElementById("form-produto");
const listaProdutos = document.getElementById("lista-produtos");
const logoutBtn = document.getElementById("logout-btn");
const btnCancelar = document.getElementById("btn-cancelar");
const btnBuscarDados = document.getElementById("btn-buscar-dados");
const imagemPreview = document.getElementById("imagem-preview");

// Verifica autenticação
auth.onAuthStateChanged((user) => {
  if (!user) window.location.href = "login.html";
});

// Logout
logoutBtn.addEventListener("click", () => auth.signOut());

// Busca dados da Shopee
btnBuscarDados.addEventListener("click", async () => {
  const link = document.getElementById("link").value.trim();
  
  if (!link.includes("shopee")) {
    alert("Por favor, insira um link válido da Shopee");
    return;
  }

  try {
    // Extrai IDs do link
    const ids = link.match(/i\.(\d+)\.(\d+)/);
    if (!ids) throw new Error("Formato de link inválido");
    
    const [_, itemid, shopid] = ids;
    
    // Chama a Netlify Function
    const response = await fetch(`/.netlify/functions/shopee?itemid=${itemid}&shopid=${shopid}`);
    const produto = await response.json();
    
    // Preenche os campos
    document.getElementById("nome").value = produto.nome;
    document.getElementById("preco-original").value = produto.preco_original;
    document.getElementById("preco-desconto").value = produto.preco_desconto;
    document.getElementById("desconto").value = produto.desconto;
    document.getElementById("imagem-url").value = produto.imagem;
    
    // Atualiza preview
    imagemPreview.innerHTML = `<img src="${produto.imagem}" alt="Preview">`;
    
  } catch (error) {
    alert("Erro ao buscar dados: " + error.message);
    console.error(error);
  }
});

// Cancelar edição
btnCancelar.addEventListener("click", () => {
  form.reset();
  document.getElementById("produto-id").value = "";
  imagemPreview.innerHTML = "";
});

// Carrega produtos
function carregarProdutos() {
  listaProdutos.innerHTML = '<div class="loading">Carregando...</div>';
  
  db.collection("produtos").orderBy("data_cadastro", "desc").onSnapshot((snapshot) => {
    if (snapshot.empty) {
      listaProdutos.innerHTML = '<div class="sem-produtos">Nenhum produto cadastrado.</div>';
      return;
    }

    listaProdutos.innerHTML = snapshot.docs.map(doc => `
      <div class="produto-box ${doc.data().destaque ? 'destaque' : ''}" data-id="${doc.id}">
        <div class="produto-imagem">
          <img src="${doc.data().imagemUrl}" alt="${doc.data().nome}" onerror="this.src='imgs/fallback.jpg'">
          ${doc.data().destaque ? '<span class="badge-destaque">⭐ Destaque</span>' : ''}
        </div>
        <div class="produto-info">
          <h3>${doc.data().nome}</h3>
          <div class="produto-precos">
            <span class="preco-original">R$ ${doc.data().preco_original.toFixed(2)}</span>
            <span class="preco-desconto">R$ ${doc.data().preco_desconto.toFixed(2)}</span>
            <span class="desconto">${doc.data().desconto}% OFF</span>
          </div>
          <div class="produto-acoes">
            <button onclick="editarProduto('${doc.id}')" class="btn-editar">✏️ Editar</button>
            <button onclick="excluirProduto('${doc.id}')" class="btn-excluir">🗑️ Excluir</button>
          </div>
        </div>
      </div>
    `).join("");
  });
}

// Formulário
form.addEventListener("submit", async (e) => {
  e.preventDefault();
  
  const id = document.getElementById("produto-id").value;
  const nome = document.getElementById("nome").value.trim();
  const link = document.getElementById("link").value.trim();
  const precoOriginal = parseFloat(document.getElementById("preco-original").value);
  const precoDesconto = parseFloat(document.getElementById("preco-desconto").value);
  const desconto = parseInt(document.getElementById("desconto").value);
  const destaque = document.getElementById("destaque").checked;
  const imagemUrl = document.getElementById("imagem-url").value;

  // Validação
  if (precoDesconto >= precoOriginal) {
    alert("O preço com desconto deve ser menor que o original!");
    return;
  }

  try {
    if (id) {
      await atualizarProduto(id, nome, link, precoOriginal, precoDesconto, desconto, destaque, imagemUrl);
    } else {
      await adicionarProduto(nome, link, precoOriginal, precoDesconto, desconto, destaque, imagemUrl);
    }
    
    form.reset();
    document.getElementById("produto-id").value = "";
    imagemPreview.innerHTML = "";
  } catch (error) {
    alert("Erro: " + error.message);
  }
});

// Funções CRUD
async function adicionarProduto(nome, link, precoOriginal, precoDesconto, desconto, destaque, imagemUrl) {
  await db.collection("produtos").add({
    nome,
    link_afiliado: link,
    preco_original: precoOriginal,
    preco_desconto: precoDesconto,
    desconto,
    destaque,
    imagemUrl,
    data_cadastro: new Date()
  });
}

async function atualizarProduto(id, nome, link, precoOriginal, precoDesconto, desconto, destaque, imagemUrl) {
  await db.collection("produtos").doc(id).update({
    nome, 
    link_afiliado: link,
    preco_original: precoOriginal,
    preco_desconto: precoDesconto,
    desconto,
    destaque,
    imagemUrl
  });
}

// Funções Globais
window.editarProduto = async (id) => {
  const doc = await db.collection("produtos").doc(id).get();
  if (!doc.exists) return;

  const produto = doc.data();
  document.getElementById("produto-id").value = id;
  document.getElementById("nome").value = produto.nome;
  document.getElementById("link").value = produto.link_afiliado;
  document.getElementById("preco-original").value = produto.preco_original;
  document.getElementById("preco-desconto").value = produto.preco_desconto;
  document.getElementById("desconto").value = produto.desconto;
  document.getElementById("destaque").checked = produto.destaque;
  document.getElementById("imagem-url").value = produto.imagemUrl;
  imagemPreview.innerHTML = `<img src="${produto.imagemUrl}" alt="Preview">`;
  window.scrollTo({ top: 0, behavior: "smooth" });
};

window.excluirProduto = async (id) => {
  if (!confirm("Tem certeza que deseja excluir este produto permanentemente?")) return;
  await db.collection("produtos").doc(id).delete();
};

// Inicialização
carregarProdutos();