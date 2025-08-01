document.addEventListener('DOMContentLoaded', function() {
            const abas = document.querySelectorAll('[data-aba]');
            
            abas.forEach(aba => {
                aba.addEventListener('click', function() {
                    document.querySelectorAll('.abas-navegacao button').forEach(b => {
                        b.classList.remove('aba-ativo');
                    });
                    
                    document.querySelectorAll('.aba-conteudo').forEach(conteudo => {
                        conteudo.classList.remove('aba-ativa');
                    });
                    
                    this.classList.add('aba-ativo');
                    const abaAlvo = this.getAttribute('data-aba');
                    document.getElementById(abaAlvo).classList.add('aba-ativa');
                });
            });
        });