// Dados de exemplo (em um cenário real, viriam de uma API)
let products = [
    {
        id: 1,
        name: "Tênis Esportivo",
        price: 199.99,
        oldPrice: 299.99,
        discountPercentage: 33,
        image: "https://via.placeholder.com/150",
        affiliateLink: "#"
    },
    {
        id: 2,
        name: "Smartphone Android",
        price: 899.99,
        oldPrice: 1199.99,
        discountPercentage: 25,
        image: "https://via.placeholder.com/150",
        affiliateLink: "#"
    },
    {
        id: 3,
        name: "Fone de Ouvido Bluetooth",
        price: 149.99,
        oldPrice: 249.99,
        discountPercentage: 40,
        image: "https://via.placeholder.com/150",
        affiliateLink: "#"
    }
];

// Elementos do DOM
const productsTable = document.querySelector('.products-table tbody');
const addProductBtn = document.getElementById('addProductBtn');
const productModal = document.getElementById('productModal');
const productForm = document.getElementById('productForm');
const modalTitle = document.getElementById('modalTitle');
const cancelBtn = document.getElementById('cancelBtn');
const closeBtn = document.querySelector('.close');

// Carregar produtos na tabela
function loadProducts() {
    productsTable.innerHTML = '';
    
    const searchTerm = searchInput.value.toLowerCase();
    const filterValue = filterSelect.value;
    
    const filteredProducts = products.filter(product => {
        // Filtrar por busca
        const matchesSearch = product.name.toLowerCase().includes(searchTerm);
        
        // Filtrar por tipo
        let matchesFilter = true;
        if (filterValue === 'featured') {
            matchesFilter = product.featured;
        } else if (filterValue === 'regular') {
            matchesFilter = !product.featured;
        }
        
        return matchesSearch && matchesFilter;
    });
    
    filteredProducts.forEach(product => {
        const row = document.createElement('tr');
        
        row.innerHTML = `
            <td><img src="${product.image}" alt="${product.name}" class="product-image"></td>
            <td>${product.name}</td>
            <td>R$ ${product.price.toFixed(2)}</td>
            <td><span class="discount-badge">${product.discountPercentage}% OFF</span></td>
            <td class="featured-cell">
                <label class="featured-toggle">
                    <input type="checkbox" ${product.featured ? 'checked' : ''} data-id="${product.id}">
                    <span class="featured-slider"></span>
                </label>
            </td>
            <td class="actions">
                <button class="btn btn-primary btn-sm edit-btn" data-id="${product.id}">
                    <i class="fas fa-edit"></i> Editar
                </button>
                <button class="btn btn-secondary btn-sm delete-btn" data-id="${product.id}">
                    <i class="fas fa-trash"></i> Excluir
                </button>
            </td>
        `;
        
        productsTable.appendChild(row);
    });
    
    // Adicionar event listeners aos botões
    document.querySelectorAll('.edit-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const productId = parseInt(e.currentTarget.getAttribute('data-id'));
            editProduct(productId);
        });
    });
    
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const productId = parseInt(e.currentTarget.getAttribute('data-id'));
            deleteProduct(productId);
        });
    });
}

// Abrir modal para adicionar produto
function openAddModal() {
    modalTitle.textContent = 'Adicionar Produto';
    productForm.reset();
    productModal.style.display = 'block';
}

// Editar produto
function editProduct(id) {
    const product = products.find(p => p.id === id);
    
    if (product) {
        modalTitle.textContent = 'Editar Produto';
        document.getElementById('name').value = product.name;
        document.getElementById('price').value = product.price;
        document.getElementById('oldPrice').value = product.oldPrice;
        document.getElementById('discountPercentage').value = product.discountPercentage;
        document.getElementById('image').value = product.image;
        document.getElementById('affiliateLink').value = product.affiliateLink;
        
        // Armazenar o ID do produto sendo editado em um campo oculto ou atributo
        productForm.setAttribute('data-edit-id', id);
        productModal.style.display = 'block';
    }
}

// Excluir produto
function deleteProduct(id) {
    if (confirm('Tem certeza que deseja excluir este produto?')) {
        products = products.filter(p => p.id !== id);
        loadProducts();
    }
}

// Salvar produto (adicionar ou editar)
function saveProduct(e) {
    e.preventDefault();
    
    const name = document.getElementById('name').value;
    const price = parseFloat(document.getElementById('price').value);
    const oldPrice = parseFloat(document.getElementById('oldPrice').value);
    const discountPercentage = parseFloat(document.getElementById('discountPercentage').value);
    const image = document.getElementById('image').value;
    const affiliateLink = document.getElementById('affiliateLink').value;
    
    // Verificar se é uma edição
    const editId = productForm.getAttribute('data-edit-id');
    
    if (editId) {
        // Atualizar produto existente
        const index = products.findIndex(p => p.id === parseInt(editId));
        if (index !== -1) {
            products[index] = {
                ...products[index],
                name,
                price,
                oldPrice,
                discountPercentage,
                image,
                affiliateLink
            };
        }
        productForm.removeAttribute('data-edit-id');
    } else {
        // Adicionar novo produto
        const newProduct = {
            id: products.length > 0 ? Math.max(...products.map(p => p.id)) + 1 : 1,
            name,
            price,
            oldPrice,
            discountPercentage,
            image,
            affiliateLink
        };
        
        products.push(newProduct);
    }
    
    loadProducts();
    closeModal();
}

// Fechar modal
function closeModal() {
    productModal.style.display = 'none';
    productForm.removeAttribute('data-edit-id');
}

// Event Listeners
addProductBtn.addEventListener('click', openAddModal);
cancelBtn.addEventListener('click', closeModal);
closeBtn.addEventListener('click', closeModal);
productForm.addEventListener('submit', saveProduct);

// Fechar modal ao clicar fora dele
window.addEventListener('click', (e) => {
    if (e.target === productModal) {
        closeModal();
    }
});

// Inicializar a tabela quando a página carregar
document.addEventListener('DOMContentLoaded', loadProducts);