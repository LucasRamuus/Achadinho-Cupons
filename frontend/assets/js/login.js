document.addEventListener("DOMContentLoaded", () => {
  const loginForm = document.getElementById("loginForm");
  const loginError = document.getElementById("loginError");

  // -------------------- FUNÇÃO DE VALIDAÇÃO DE TOKEN --------------------
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

  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    loginError.style.display = "none";

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;

    if (!username || !password) {
      loginError.textContent = "Preencha usuário e senha!";
      loginError.style.display = "block";
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
      });

      if (!response.ok) {
        loginError.textContent = response.status === 401 
          ? "Usuário ou senha incorretos!" 
          : "Erro ao autenticar, tente novamente!";
        loginError.style.display = "block";
        return;
      }

      const data = await response.json();
      const token = data.token;

      if (!token || !isTokenValid(token)) {
        loginError.textContent = "Token inválido recebido do servidor!";
        loginError.style.display = "block";
        return;
      }

      // Salvar token na sessionStorage (expira quando o navegador fecha)
      sessionStorage.setItem("jwtToken", token);
      window.location.href = "/frontend/public/admin.html";

    } catch (error) {
      console.error(error);
      loginError.textContent = "Erro ao conectar ao servidor!";
      loginError.style.display = "block";
    }
  });
});
