document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("login-form");
  
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;
    const btn = form.querySelector("button");
    
    // Animação de loading
    btn.disabled = true;
    btn.innerHTML = '<span class="btn-text">Carregando...</span><span class="btn-icon">⏳</span>';
    
    try {
      await firebase.auth().signInWithEmailAndPassword(email, senha);
      window.location.href = "admin.html";
    } catch (error) {
      alert(`Erro: ${error.message}`);
      btn.disabled = false;
      btn.innerHTML = '<span class="btn-text">Entrar</span><span class="btn-icon">→</span>';
    }
  });
});