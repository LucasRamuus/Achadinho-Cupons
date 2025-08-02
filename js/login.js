document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("login-form");
  const emailInput = document.getElementById("email");
  const senhaInput = document.getElementById("senha");
  const btn = form.querySelector("button");
  
  // Verifica se já está logado ao carregar a página
  firebase.auth().onAuthStateChanged((user) => {
    if (user) {
      window.location.href = "admin.html";
    }
  });

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    
    // Validação básica dos campos
    if (!emailInput.value || !senhaInput.value) {
      alert("Por favor, preencha todos os campos!");
      return;
    }

    // Animação de loading
    btn.disabled = true;
    btn.innerHTML = '<span class="btn-text">Carregando...</span><span class="btn-icon">⏳</span>';
    
    try {
      await firebase.auth().signInWithEmailAndPassword(emailInput.value, senhaInput.value);
      // Redirecionamento já acontece pelo onAuthStateChanged
    } catch (error) {
      let errorMessage = "Erro ao fazer login";
      
      // Mensagens de erro mais amigáveis
      switch (error.code) {
        case "auth/invalid-email":
          errorMessage = "E-mail inválido";
          break;
        case "auth/user-disabled":
          errorMessage = "Esta conta foi desativada";
          break;
        case "auth/user-not-found":
        case "auth/wrong-password":
          errorMessage = "E-mail ou senha incorretos";
          break;
        default:
          errorMessage = error.message;
      }
      
      alert(errorMessage);
      senhaInput.value = ""; // Limpa a senha
      senhaInput.focus(); // Foca no campo de senha
    } finally {
      btn.disabled = false;
      btn.innerHTML = '<span class="btn-text">Entrar</span><span class="btn-icon">→</span>';
    }
  });
});