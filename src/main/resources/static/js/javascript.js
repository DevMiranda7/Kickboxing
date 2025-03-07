
document.addEventListener("DOMContentLoaded", function () {
    const modalidades = {
        "btn-modalidade-light": "cabecalho-light",
        "btn-modalidade-kick": "cabecalho-kick",
        "btn-modalidade-point": "cabecalho-point",
        "btn-modalidade-full": "cabecalho-full",
        "btn-modalidade-low": "cabecalho-low",
        "btn-modalidade-k1": "cabecalho-k1",
        "btn-modalidade-kb": "cabecalho-kb"
    };

    const botoes = document.querySelectorAll(".conteiner-modalidades button");
    const cabecalhos = document.querySelectorAll(".div-cabecalho-modalidade");

    // Oculta todos os cabeçalhos e remove 'active' dos botões no início
    cabecalhos.forEach(cabecalho => cabecalho.style.display = "none");
    botoes.forEach(botao => botao.classList.remove("active"));

    // Exibe a seção "Light Combat" e marca o botão como ativo ao carregar a página
    document.getElementById("cabecalho-light").style.display = "flex";
    document.querySelector(".btn-modalidade-light").classList.add("active");

    botoes.forEach(botao => {
        botao.addEventListener("click", function () {
            // Remove a classe 'active' de todos os botões
            botoes.forEach(btn => btn.classList.remove("active"));

            // Adiciona a classe 'active' ao botão clicado
            botao.classList.add("active");

            // Esconde todos os cabeçalhos antes de mostrar o correto
            cabecalhos.forEach(cabecalho => cabecalho.style.display = "none");

            // Obtém o ID do cabeçalho correspondente e exibe
            const idCabecalho = modalidades[botao.classList[1]];
            document.getElementById(idCabecalho).style.display = "flex";
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    // Seleciona todos os inputs de pontos
    let inputsPontos = document.querySelectorAll("input[name^='pontos']");

    inputsPontos.forEach(input => {
        input.addEventListener("input", function () {
            // Permite apenas números e vírgulas
            this.value = this.value.replace(/[^0-9,]/g, '');

            // Impede mais de uma vírgula
            let partes = this.value.split(',');
            if (partes.length > 2) {
                this.value = partes[0] + ',' + partes[1]; // Mantém apenas uma vírgula
            }
        });
    });

    // Seleciona todos os formulários e adiciona o evento de submit
    let forms = document.querySelectorAll("form[id$='Form']");

    forms.forEach(form => {
        form.addEventListener("submit", function () {
            let inputPontos = form.querySelector("input[name^='pontos']");
            if (inputPontos) {
                inputPontos.value = inputPontos.value.replace(',', '.'); // Converte vírgula para ponto antes de enviar
            }
        });
    });
});

function openModalCriarConta() {
    document.getElementById("criarContaModal").style.display = "block";
}

function closeModalCriarConta() {
    document.getElementById("criarContaModal").style.display = "none";
}

function openModalRecuperarConta() {
    document.getElementById("recuperarContaModal").style.display = "block";
}

function closeModalRecuperarConta() {
    document.getElementById("recuperarContaModal").style.display = "none";
}

function openModalEditarConta() {
    document.getElementById("editarContaModal").style.display = "block";
}

function closeModalEditarConta() {
    document.getElementById("editarContaModal").style.display = "none";
}

function openModalSenhaConta() {
    closeModalEditarConta();

    document.getElementById("nome-senha").value = document.getElementById("nome-editar").value;
    document.getElementById("sobrenome-senha").value = document.getElementById("sobrenome-editar").value;
    document.getElementById("telefone-senha").value = document.getElementById("telefone-editar").value;
    document.getElementById("email-senha").value = document.getElementById("email-editar").value;

    document.getElementById("senhaContaModal").style.display = "block";
}

function closeModalSenhaConta() {
    document.getElementById("senhaContaModal").style.display = "none";
}

window.onclick = function (event) {
    let criarContaModal = document.getElementById("criarContaModal");
    let recuperarContaModal = document.getElementById("recuperarContaModal");
    let editarContaModal = document.getElementById("editarContaModal");
    let senhaContaModal = document.getElementById("senhaContaModal");

    if (event.target === criarContaModal) {
        closeModalCriarConta();
    }

    if (event.target === recuperarContaModal) {
        closeModalRecuperarConta();
    }

    if (event.target === editarContaModal) {
        closeModalEditarConta();
    }

    if (event.target === senhaContaModal) {
        closeModalSenhaConta();
    }
};

function formatarTelefone(input) {
    var telefone = input.value.replace(/\D/g, '');
    telefone = telefone.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    input.value = telefone;
}

document.getElementById("telefone-cadastro").addEventListener("input", function () {
    var telefone = this.value.replace(/\D/g, '');

    if (telefone.length !== 11) {
        this.setCustomValidity("Número inválido.");
    } else {
        this.setCustomValidity("");
    }
});

function formatarContato(input) {
    var contato = input.value.replace(/\D/g, '');
    contato = contato.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    input.value = contato;
}

document.getElementById("contato-academia").addEventListener("input", function () {
    var contato = this.value.replace(/\D/g, '');

    if (contato.length !== 11) {
        this.setCustomValidity("Número inválido.");
    } else {
        this.setCustomValidity("");
    }
});

function openModalImagemEvento(imgSrc) {
    const modal = document.getElementById("imagemEventoModal");
    const modalImage = document.getElementById("imagemEventoModalImage");

    modalImage.src = imgSrc;

    modal.style.display = "block";
}

function closeModalImagemEvento() {
    const modal = document.getElementById("imagemEventoModal");
    modal.style.display = "none";
}

window.onclick = function (event) {
    let modal = document.getElementById("imagemEventoModal");
    if (event.target === modal) {
        closeModalImagemEvento();
    }
};

function filtrarEventos(mes) {
    // Adiciona a classe 'active' ao botão clicado e remove dos outros
    let botoes = document.querySelectorAll(".conteiner-calendario button");
    botoes.forEach(botao => {
        if (botao.textContent === getMesNome(mes)) {
            botao.classList.add("active");
        } else {
            botao.classList.remove("active");
        }
    });

    // Agora faz o fetch e exibe os eventos
    fetch('/filtrar?mes=' + mes)
        .then(response => response.json())
        .then(eventos => {
            let containerEventos = document.querySelector(".conteiner-eventos ul");
            containerEventos.innerHTML = ""; // Limpa a lista de eventos

            eventos.forEach(evento => {
                let eventoItem = document.createElement("li");

                let img = document.createElement("img");
                img.src = evento.imagemEvento;
                img.alt = "Imagem do evento";
                img.width = 200;
                img.onclick = () => openModalImagemEvento(img.src);

                let nomeSpan = document.createElement("span");
                nomeSpan.textContent = evento.nomeEvento;
                nomeSpan.classList.add("span-nomeEvento");

                let descSpan = document.createElement("span");
                descSpan.textContent = evento.descricaoEvento;
                descSpan.classList.add("span-descricaoEvento");

                let dataDiv = document.createElement("div");
                dataDiv.classList.add("data-evento");
                dataDiv.innerHTML = `<span>Data:</span> <span>${evento.dataEvento}</span>`;

                let horaDiv = document.createElement("div");
                horaDiv.classList.add("hora-evento");
                horaDiv.innerHTML = `<span>Hora:</span> <span>${evento.horaEvento}</span>`;

                let formExcluir = document.createElement("form");
                formExcluir.id = `formExcluir_${evento.idEvento}`;
                formExcluir.action = `/eventos/${evento.idEvento}`;
                formExcluir.method = "post";

                let inputHidden = document.createElement("input");
                inputHidden.type = "hidden";
                inputHidden.name = "_method";
                inputHidden.value = "DELETE";

                let divExcluir = document.createElement("div");
                let btnExcluir = document.createElement("button");
                btnExcluir.type = "button";
                btnExcluir.classList.add("icon-lixo-eventos");
                btnExcluir.setAttribute("data-id", evento.idEvento);
                btnExcluir.innerHTML = `<i class="fa-solid fa-trash"></i>`;

                btnExcluir.addEventListener("click", function () {
                    Swal.fire({
                        title: "Tem certeza?",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#d33",
                        cancelButtonColor: "#3085d6",
                        confirmButtonText: "Sim, excluir!",
                        cancelButtonText: "Cancelar"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            document.getElementById(`formExcluir_${evento.idEvento}`).submit();
                        }
                    });
                });

                divExcluir.appendChild(btnExcluir);
                formExcluir.appendChild(inputHidden);
                formExcluir.appendChild(divExcluir);

                eventoItem.appendChild(img);
                eventoItem.appendChild(nomeSpan);
                eventoItem.appendChild(descSpan);
                eventoItem.appendChild(dataDiv);
                eventoItem.appendChild(horaDiv);
                eventoItem.appendChild(formExcluir);

                containerEventos.appendChild(eventoItem);
            });
        })
    .catch(error => console.error("Erro ao buscar eventos:", error));
}

// Função auxiliar para mapear o número do mês para o nome
function getMesNome(mes) {
    const meses = [
        "JAN", "FEV", "MAR", "ABR", "MAI", "JUN",
        "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"
    ];
    return meses[mes - 1];
}


function openModalImagemAcademia(imgSrc) {
    const modalImagemAcademia = document.getElementById("imagemAcademiaModal");
    const modalImageAcademia = document.getElementById("imagemAcademiaModalImage");

    modalImageAcademia.src = imgSrc;
    modalImagemAcademia.style.display = "block";
}

function closeModalAcademiaEvento() {
    const modalImagemAcademia = document.getElementById("imagemAcademiaModal");
    modalImagemAcademia.style.display = "none";
}

window.onclick = function (event) {
    let modalImagemAcademia = document.getElementById("imagemAcademiaModal");
    if (event.target === modalImagemAcademia) {
        closeModalAcademiaEvento();
    }
};

function pesquisarAcademias() {
    const cidade = document.getElementById('select-cidade').value;

    fetch('/pesquisarAcademias?opcoes-cidades=' + cidade)
        .then(response => response.json())
        .then(academias => {
            let containerAcademias = document.querySelector(".conteiner-academias ul");
            containerAcademias.innerHTML = "";

            academias.forEach(academia => {
                let academiaItem = document.createElement("li");

                let img = document.createElement("img");
                img.src = academia.imagemAcademia;
                img.alt = "Imagem da academia";
                img.width = 200;
                img.onclick = function() { openModalImagemAcademia(this.src); };

                let divInfoAcademias = document.createElement("div");
                divInfoAcademias.classList.add("div-info-academias");

                let nomeSpan = document.createElement("span");
                nomeSpan.textContent = academia.nomeAcademia;
                nomeSpan.classList.add("span-nomeAcademia");

                let divResponsavel = document.createElement("div");
                divResponsavel.classList.add("div-tecnico");
                divResponsavel.innerHTML = `<span>Responsável Técnico:</span> <span class="academia-responsavel-tecnico">${academia.responsavelAcademia}</span>`;

                let divEndereco = document.createElement("div");
                divEndereco.classList.add("div-endereco");
                divEndereco.innerHTML = `<span>${academia.enderecoAcademia}</span>`;

                let cidadeSpan = document.createElement("span");
                cidadeSpan.textContent = academia.cidadeAcademia;

                let divContato = document.createElement("div");
                divContato.classList.add("div-contato-academia");
                divContato.innerHTML = `<span class="academia-contato">${academia.contatoAcademia}</span>`;

                divInfoAcademias.appendChild(nomeSpan);
                divInfoAcademias.appendChild(divResponsavel);
                divInfoAcademias.appendChild(divEndereco);
                divInfoAcademias.appendChild(cidadeSpan);
                divInfoAcademias.appendChild(divContato);

                let formExcluir = document.createElement("form");
                formExcluir.id = `formExcluir_${academia.idAcademia}`;
                formExcluir.action = `/academias/${academia.idAcademia}`;
                formExcluir.method = "post";

                let inputHidden = document.createElement("input");
                inputHidden.type = "hidden";
                inputHidden.name = "_method";
                inputHidden.value = "DELETE";

                let btnExcluir = document.createElement("button");
                btnExcluir.type = "button";
                btnExcluir.classList.add("icon-lixo-eventos");
                btnExcluir.setAttribute("data-id", academia.idAcademia);
                btnExcluir.innerHTML = `<i class="fa-solid fa-trash"></i>`;

                btnExcluir.style.border = "none";
                btnExcluir.style.backgroundColor = "transparent";
                btnExcluir.style.cursor = "pointer";

                btnExcluir.addEventListener("click", function () {
                    Swal.fire({
                        title: "Tem certeza?",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#d33",
                        cancelButtonColor: "#3085d6",
                        confirmButtonText: "Sim, excluir!",
                        cancelButtonText: "Cancelar"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            document.getElementById(`formExcluir_${academia.idAcademia}`).submit();
                        }
                    });
                });

                formExcluir.appendChild(inputHidden);
                formExcluir.appendChild(btnExcluir);

                academiaItem.appendChild(img);
                academiaItem.appendChild(divInfoAcademias);
                academiaItem.appendChild(formExcluir);

                containerAcademias.appendChild(academiaItem);
            });
        })
        .catch(error => console.error("Erro ao buscar academias:", error));
}

function openModalImagemProfessor(imgElement) {
    const modalImagemProfessor = document.getElementById("imagemProfessorModal");
    const modalImageProfessor = document.getElementById("imagemProfessorModalImage");

    modalImageProfessor.src = imgElement.src;

    document.getElementById("professorId").value = imgElement.getAttribute("data-id");
    document.getElementById("professorNome").value = imgElement.getAttribute("data-nome");
    document.getElementById("professorRegistro").value = imgElement.getAttribute("data-registro");
    document.getElementById("professorCidade").value = imgElement.getAttribute("data-cidade");
    document.getElementById("professorGraduacao").value = imgElement.getAttribute("data-graduacao");
    document.getElementById("professorEquipe").value = imgElement.getAttribute("data-equipe");
//    document.getElementById("professorNascimento").value = imgElement.getAttribute("data-nascimento");

    modalImagemProfessor.style.display = "flex";
}

function closeModalImagemProfessor() {
    const modalImagemProfessor = document.getElementById("imagemProfessorModal");
    modalImagemProfessor.style.display = "none";
}

window.onclick = function(event) {
    let modalImagemProfessor = document.getElementById("imagemProfessorModal");
    if (event.target === modalImagemProfessor) {
        closeModalImagemProfessor();
    }
};

function pesquisarProfessores() {
    const cidade = document.getElementById('select-cidade-professores').value;

    event.preventDefault();

    fetch('/pesquisarProfessores?opcoes-cidades-professores=' + cidade)
        .then(response => response.json())
        .then(professores => {
            let tbody = document.querySelector(".conteiner-professores tbody");
            tbody.innerHTML = "";

            professores.forEach(professor => {
                let tr = document.createElement("tr");

                let tdImg = document.createElement("td");
                let img = document.createElement("img");
                img.src = professor.imagemProfessor;
                img.alt = "Imagem do professor";
                img.style.borderRadius = "10px";
                img.setAttribute("data-id", professor.idProfessor);
                img.setAttribute("data-nome", professor.nomeProfessor);
                img.setAttribute("data-registro", professor.registroProfessor);
                img.setAttribute("data-cidade", professor.cidadeProfessor);
                img.setAttribute("data-graduacao", professor.graduacaoProfessor);
                img.setAttribute("data-equipe", professor.equipeProfessor);
                img.setAttribute("data-nascimento", professor.nascimentoProfessor);
                img.onclick = function () {
                    openModalImagemProfessor(this);
                };
                tdImg.appendChild(img);

                let tdRegistro = document.createElement("td");
                tdRegistro.textContent = professor.registroProfessor;

                let tdNome = document.createElement("td");
                tdNome.textContent = professor.nomeProfessor;

                let tdCidade = document.createElement("td");
                tdCidade.textContent = professor.cidadeProfessor;

                let tdGraduacao = document.createElement("td");
                let spanGraduacao = document.createElement("span");
                spanGraduacao.textContent = professor.graduacaoProfessor;
                spanGraduacao.classList.add(professor.graduacaoProfessor);
                tdGraduacao.appendChild(spanGraduacao);

                let tdEquipe = document.createElement("td");
                tdEquipe.textContent = professor.equipeProfessor;

                let tdNascimento = document.createElement("td");
                tdNascimento.textContent = professor.nascimentoProfessor;

                let tdExcluir = document.createElement("td");
                let formExcluir = document.createElement("form");
                formExcluir.id = `formExcluir_${professor.idProfessor}`;
                formExcluir.action = `/professores/${professor.idProfessor}`;
                formExcluir.method = "post";

                let inputHidden = document.createElement("input");
                inputHidden.type = "hidden";
                inputHidden.name = "_method";
                inputHidden.value = "DELETE";

                let btnExcluir = document.createElement("button");
                btnExcluir.type = "button";
                btnExcluir.classList.add("icon-lixo-professores");
                btnExcluir.setAttribute("data-id", professor.idProfessor);
                btnExcluir.innerHTML = `<i class="fa-solid fa-trash"></i>`;
                btnExcluir.style.border = "none";
                btnExcluir.style.backgroundColor = "transparent";
                btnExcluir.style.cursor = "pointer";

                btnExcluir.addEventListener("click", function () {
                    Swal.fire({
                        title: "Tem certeza?",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#d33",
                        cancelButtonColor: "#3085d6",
                        confirmButtonText: "Sim, excluir!",
                        cancelButtonText: "Cancelar"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            document.getElementById(`formExcluir_${professor.idProfessor}`).submit();
                        }
                    });
                });

                formExcluir.appendChild(inputHidden);
                formExcluir.appendChild(btnExcluir);
                tdExcluir.appendChild(formExcluir);

                tr.appendChild(tdImg);
                tr.appendChild(tdRegistro);
                tr.appendChild(tdNome);
                tr.appendChild(tdCidade);
                tr.appendChild(tdGraduacao);
                tr.appendChild(tdEquipe);
                tr.appendChild(tdNascimento);
                tr.appendChild(tdExcluir);

                tbody.appendChild(tr);
            });
        })
        .catch(error => console.error("Erro ao buscar professores:", error));
}

function openModalImagemAluno(imgElement) {
    const modalImagemAluno = document.getElementById("imagemAlunoModal");
    const modalImageAluno = document.getElementById("imagemAlunoModalImage");

    modalImageAluno.src = imgElement.src;

    document.getElementById("alunoId").value = imgElement.getAttribute("data-id");
    document.getElementById("alunoNome").value = imgElement.getAttribute("data-nome");
    document.getElementById("alunoRegistro").value = imgElement.getAttribute("data-registro");
    document.getElementById("alunoCidade").value = imgElement.getAttribute("data-cidade");
    document.getElementById("alunoGraduacao").value = imgElement.getAttribute("data-graduacao");
    document.getElementById("alunoAcademia").value = imgElement.getAttribute("data-academia");
    document.getElementById("alunoResponsavel").value = imgElement.getAttribute("data-responsavel");
//    document.getElementById("professorNascimento").value = imgElement.getAttribute("data-nascimento");

    modalImagemAluno.style.display = "flex";
}

function closeModalImagemAluno() {
    const modalImagemAluno = document.getElementById("imagemAlunoModal");
    modalImagemAluno.style.display = "none";
}

window.onclick = function(event) {
    let modalImagemAluno = document.getElementById("imagemAlunoModal");
    if (event.target === modalImagemAluno) {
        closeModalImagemAluno();
    }
};

function pesquisarAlunos() {
    const cidade = document.getElementById('select-cidade-alunos').value;

    event.preventDefault();

    fetch('/pesquisarAlunos?opcoes-cidades-alunos=' + cidade)
        .then(response => response.json())
        .then(alunos => {
            let tbody = document.querySelector(".conteiner-alunos tbody");
            tbody.innerHTML = "";

            alunos.forEach(aluno => {
                let tr = document.createElement("tr");

                let tdImg = document.createElement("td");
                let img = document.createElement("img");
                img.src = aluno.imagemAluno;
                img.alt = "Imagem do aluno";
                img.style.borderRadius = "10px";
                img.setAttribute("data-id", aluno.idAluno);
                img.setAttribute("data-nome", aluno.nomeAluno);
                img.setAttribute("data-registro", aluno.registroAluno);
                img.setAttribute("data-cidade", aluno.cidadeAluno);
                img.setAttribute("data-graduacao", aluno.graduacaoAluno);
                img.setAttribute("data-academia", aluno.academiaAluno);
                img.setAttribute("data-responsavel", aluno.responsavelAluno);
                img.setAttribute("data-nascimento", aluno.nascimentoAluno);
                img.onclick = function () {
                    openModalImagemAluno(this);
                };
                tdImg.appendChild(img);

                let tdRegistro = document.createElement("td");
                tdRegistro.textContent = aluno.registroAluno;

                let tdNome = document.createElement("td");
                tdNome.textContent = aluno.nomeAluno;

                let tdCidade = document.createElement("td");
                tdCidade.textContent = aluno.cidadeAluno;

                let tdGraduacao = document.createElement("td");
                let spanGraduacao = document.createElement("span");
                spanGraduacao.textContent = aluno.graduacaoAluno;
                spanGraduacao.classList.add(aluno.graduacaoAluno);
                tdGraduacao.appendChild(spanGraduacao);

                let tdAcademia = document.createElement("td");
                tdAcademia.textContent = aluno.academiaAluno;

                let tdResponsavel = document.createElement("td");
                tdResponsavel.textContent = aluno.responsavelAluno;

                let tdNascimento = document.createElement("td");
                tdNascimento.textContent = aluno.nascimentoAluno;

                let tdExcluir = document.createElement("td");
                let formExcluir = document.createElement("form");
                formExcluir.id = `formExcluir_${aluno.idAluno}`;
                formExcluir.action = `/alunos/${aluno.idAluno}`;
                formExcluir.method = "post";

                let inputHidden = document.createElement("input");
                inputHidden.type = "hidden";
                inputHidden.name = "_method";
                inputHidden.value = "DELETE";

                let btnExcluir = document.createElement("button");
                btnExcluir.type = "button";
                btnExcluir.classList.add("icon-lixo-alunos");
                btnExcluir.setAttribute("data-id", aluno.idAluno);
                btnExcluir.innerHTML = `<i class="fa-solid fa-trash"></i>`;
                btnExcluir.style.border = "none";
                btnExcluir.style.backgroundColor = "transparent";
                btnExcluir.style.cursor = "pointer";

                btnExcluir.addEventListener("click", function () {
                    Swal.fire({
                        title: "Tem certeza?",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#d33",
                        cancelButtonColor: "#3085d6",
                        confirmButtonText: "Sim, excluir!",
                        cancelButtonText: "Cancelar"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            document.getElementById(`formExcluir_${aluno.idAluno}`).submit();
                        }
                    });
                });

                formExcluir.appendChild(inputHidden);
                formExcluir.appendChild(btnExcluir);
                tdExcluir.appendChild(formExcluir);

                tr.appendChild(tdImg);
                tr.appendChild(tdRegistro);
                tr.appendChild(tdNome);
                tr.appendChild(tdCidade);
                tr.appendChild(tdGraduacao);
                tr.appendChild(tdAcademia);
                tr.appendChild(tdResponsavel);
                tr.appendChild(tdNascimento);
                tr.appendChild(tdExcluir);

                tbody.appendChild(tr);
            });
        })
        .catch(error => console.error("Erro ao buscar alunos:", error));
}

function openModalLightCombat() {
    document.getElementById("lightCombatModal").style.display = "block";
}

function closeModalLightCombat() {
    document.getElementById("lightCombatModal").style.display = "none";
}

function openModalKickLight() {
    document.getElementById("kickLightModal").style.display = "block";
}

function closeModalKickLight() {
    document.getElementById("kickLightModal").style.display = "none";
}

function openModalPointFight() {
    document.getElementById("pointFightModal").style.display = "block";
}

function closeModalPointFight() {
    document.getElementById("pointFightModal").style.display = "none";
}

function openModalFullContact() {
    document.getElementById("fullContactModal").style.display = "block";
}

function closeModalFullContact() {
    document.getElementById("fullContactModal").style.display = "none";
}

function openModalLowKicks() {
    document.getElementById("lowKicksModal").style.display = "block";
}

function closeModalLowKicks() {
    document.getElementById("lowKicksModal").style.display = "none";
}

function openModalK1() {
    document.getElementById("k1Modal").style.display = "block";
}

function closeModalK1() {
    document.getElementById("k1Modal").style.display = "none";
}

function openModalKBCombat() {
    document.getElementById("KBCombatModal").style.display = "block";
}

function closeModalKBCombat() {
    document.getElementById("KBCombatModal").style.display = "none";
}

window.onclick = function (event) {
    let lightCombatModal = document.getElementById("lightCombatModal");
    let kickLightModal = document.getElementById("kickLightModal");
    let pointFightModal = document.getElementById("pointFightModal");
    let fullContactModal = document.getElementById("fullContactModal");
    let lowKicksModal = document.getElementById("lowKicksModal");
    let k1Modal = document.getElementById("k1Modal");
    let KBCombatModal = document.getElementById("KBCombatModal");

    if (event.target === pointFightModal) {
        closeModalLightCombat();
    }

    if (event.target === kickLightModal) {
        closeModalKickLight();
    }

    if (event.target === pointFightModal) {
        closeModalPointFight();
    }

    if (event.target === fullContactModal) {
        closeModalFullContact();
    }

    if (event.target === lowKicksModal) {
        closeModalLowKicks();
    }

    if (event.target === k1Modal) {
        closeModalK1();
    }

    if (event.target === KBCombatModal) {
        closeModalKBCombat();
    }
};