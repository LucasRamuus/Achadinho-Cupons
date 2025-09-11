document.addEventListener("DOMContentLoaded", () => {
  const API_URL = "http://localhost:8080";
  let products = [];

  // -------------------- TOKEN --------------------
  function isTokenValid(token) {
    if (!token) return false;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const now = Math.floor(Date.now() / 1000);
      return payload.exp > now;
    } catch {
      return false;
    }
  }

  const token = sessionStorage.getItem("jwtToken");
  if (!isTokenValid(token)) {
    sessionStorage.removeItem("jwtToken");
    window.location.href = "/frontend/public/login.html";
  }

  // -------------------- DOM ELEMENTS --------------------
  const addProductBtn = document.getElementById("addProductBtn");
  const productModal = document.getElementById("productModal");
  const productForm = document.getElementById("productForm");
  const cancelBtn = document.getElementById("cancelBtn");
  const closeBtn = document.querySelector(".close");
  const logoutBtn = document.getElementById("logoutBtn");

  // Campos usados para calcular desconto
  const priceInput = document.getElementById("price");
  const oldPriceInput = document.getElementById("oldPrice");
  const discountInput = document.getElementById("discountPercentage");

  // -------------------- LOGOUT --------------------
  logoutBtn.addEventListener("click", () => {
    sessionStorage.removeItem("jwtToken");
    window.location.href = "/frontend/public/login.html";
  });

  // -------------------- FUNÇÕES DE PRODUTOS --------------------
  async function fetchProducts() {
    try {
      const response = await fetch(`${API_URL}/products`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!response.ok) throw new Error("Erro ao buscar produtos");
      products = await response.json();
      loadProducts();
    } catch (error) {
      console.error(error);
      alert("Não foi possível conectar ao servidor.");
    }
  }

  function loadProducts() {
    const productsTable = document.querySelector(".products-table tbody");
    productsTable.innerHTML = "";

    products.forEach(product => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td><img src="${product.image}" alt="${product.name}" class="product-image"></td>
        <td>${product.name}</td>
        <td>R$ ${product.price.toFixed(2)}</td>
        <td><span class="discount-badge">${product.discountPercentage}% OFF</span></td>
        <td>
          <label class="featured-toggle">
            <input type="checkbox" ${product.featured ? "checked" : ""} data-id="${product.id}">
            <span class="featured-slider"></span>
          </label>
        </td>
        <td class="actions">
          <button class="btn btn-primary btn-sm edit-btn" data-id="${product.id}"><i class="fas fa-edit"></i> Editar</button>
          <button class="btn btn-secondary btn-sm delete-btn" data-id="${product.id}"><i class="fas fa-trash"></i> Excluir</button>
        </td>
      `;
      productsTable.appendChild(row);
    });

    productsTable.addEventListener("click", handleTableClick);
    productsTable.addEventListener("change", handleFeaturedToggle);
  }

  function handleTableClick(e) {
    if (e.target.closest(".edit-btn")) editProduct(e.target.closest(".edit-btn").dataset.id);
    if (e.target.closest(".delete-btn")) deleteProduct(e.target.closest(".delete-btn").dataset.id);
  }

  async function handleFeaturedToggle(e) {
    if (!e.target.matches(".featured-toggle input[type='checkbox']")) return;
    const checkbox = e.target;
    const id = checkbox.dataset.id;
    const product = products.find(p => p.id == id);
    if (!product) return;

    const formData = new FormData();
    formData.append("name", product.name);
    formData.append("price", product.price);
    formData.append("oldPrice", product.oldPrice);
    formData.append("discountPercentage", product.discountPercentage);
    formData.append("affiliateLink", product.affiliateLink);
    formData.append("featured", checkbox.checked);

    try {
      await fetch(`${API_URL}/products/${id}`, {
        method: "PUT",
        headers: { Authorization: `Bearer ${token}` },
        body: formData
      });
      product.featured = checkbox.checked;
    } catch {
      alert("Erro ao atualizar destaque.");
      checkbox.checked = !checkbox.checked;
    }
  }

  // -------------------- CALCULAR DESCONTO --------------------
  function calcularDesconto() {
    const price = parseFloat(priceInput.value);
    const oldPrice = parseFloat(oldPriceInput.value);

    if (!isNaN(price) && !isNaN(oldPrice) && oldPrice > 0 && price < oldPrice) {
      const desconto = ((oldPrice - price) / oldPrice) * 100;
      discountInput.value = Math.round(desconto); // arredonda para inteiro
    } else {
      discountInput.value = "";
    }
  }

  priceInput.addEventListener("input", calcularDesconto);
  oldPriceInput.addEventListener("input", calcularDesconto);

  // -------------------- MODAL --------------------
  function openAddModal() {
    document.getElementById("modalTitle").textContent = "Adicionar Produto";
    productForm.reset();
    discountInput.value = "";
    document.getElementById("file").required = true;
    productForm.removeAttribute("data-edit-id");
    productModal.style.display = "block";
  }

  function editProduct(id) {
    const product = products.find(p => p.id == id);
    if (!product) return;

    document.getElementById("modalTitle").textContent = "Editar Produto";
    productForm.reset();
    productForm.querySelector("#name").value = product.name;
    productForm.querySelector("#price").value = product.price;
    productForm.querySelector("#oldPrice").value = product.oldPrice;
    productForm.querySelector("#affiliateLink").value = product.affiliateLink;
    productForm.querySelector("#featured").checked = product.featured;
    productForm.querySelector("#file").required = false;
    productForm.setAttribute("data-edit-id", id);

    // recalcula o desconto ao abrir
    calcularDesconto();

    productModal.style.display = "block";
  }

  async function deleteProduct(id) {
    if (!confirm("Tem certeza que deseja excluir este produto?")) return;
    try {
      await fetch(`${API_URL}/products/${id}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });
      fetchProducts();
    } catch {
      alert("Erro ao excluir produto.");
    }
  }

  async function saveProduct(e) {
    e.preventDefault();

    // garante desconto atualizado
    calcularDesconto();

    const editId = productForm.getAttribute("data-edit-id");
    let url = `${API_URL}/products`;
    let method = "POST";
    if (editId) { url += `/${editId}`; method = "PUT"; }

    const formData = new FormData();
    formData.append("name", document.getElementById("name").value);
    formData.append("price", parseFloat(priceInput.value));
    formData.append("oldPrice", parseFloat(oldPriceInput.value));
    formData.append("discountPercentage", parseFloat(discountInput.value) || 0);
    formData.append("affiliateLink", document.getElementById("affiliateLink").value);
    formData.append("featured", document.getElementById("featured").checked);

    const fileInput = document.getElementById("file");
    if (fileInput.files.length > 0) formData.append("file", fileInput.files[0]);

    try {
      await fetch(url, { method, headers: { Authorization: `Bearer ${token}` }, body: formData });
      productForm.removeAttribute("data-edit-id");
      closeModal();
      fetchProducts();
    } catch {
      alert("Erro ao salvar produto.");
    }
  }

  function closeModal() {
    productModal.style.display = "none";
    productForm.removeAttribute("data-edit-id");
  }

  // -------------------- EVENTOS --------------------
  addProductBtn.addEventListener("click", openAddModal);
  cancelBtn.addEventListener("click", closeModal);
  closeBtn.addEventListener("click", closeModal);
  productForm.addEventListener("submit", saveProduct);
  window.addEventListener("click", e => { if (e.target === productModal) closeModal(); });

  // -------------------- INICIALIZAÇÃO --------------------
  fetchProducts();
});
