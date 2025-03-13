package kickboxing.controller;

import jakarta.servlet.http.HttpSession;
import kickboxing.exception.AutenticacaoException;
import kickboxing.exception.EmailJaCriadoException;
import kickboxing.exception.TelefoneJaCriadoException;
import kickboxing.model.Admin;
import kickboxing.service.AdminService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/index")
    public String indexRedirecionar() {
        return "redirect:/index";
    }

    @PostMapping("/admin")
    public String adminRedirecionar() {
        return "redirect:/admin";
    }

    @PostMapping("/criarConta")
    public String criarConta(@ModelAttribute Admin admin, RedirectAttributes redirectAttributes) {
        try {
            Admin novoAdmin = adminService.criarContaAdmin(admin);
            redirectAttributes.addFlashAttribute("successMessage", "Cadastro realizado com sucesso!");
            return "redirect:/admin";

        } catch (EmailJaCriadoException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin";

        } catch (TelefoneJaCriadoException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar adminstrador: " + e.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/recuperarConta")
    public String recuperarConta(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        try {
            if (!adminService.isEmailCadastrado(email)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Esse e-mail não está cadastrado!");
                return "redirect:/admin";
            }

            String token = adminService.gerarTokenRecuperacao(email);

            String urlRecuperacao = "http://localhost:8080/recuperarSenha?token=" + token;

            adminService.enviarEmailRecuperacao(email, "Clique no link para recuperar sua senha: " + urlRecuperacao);

            redirectAttributes.addFlashAttribute("successMessage", "Um e-mail foi enviado com as instruções.");
            return "redirect:/admin";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao processar a solicitação: " + e.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/atualizarSenha")
    public String atualizarSenha(@RequestParam("token") String token, @RequestParam("senha") String novaSenha, RedirectAttributes redirectAttributes) {
        try {
            if (adminService.isTokenValido(token)) {
                adminService.atualizarSenha(token, novaSenha);
                redirectAttributes.addFlashAttribute("successMessage", "Senha atualizada com sucesso.");
                return "redirect:/admin";

            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Token inválido ou expirado.");
                return "redirect:/admin";
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar a senha: " + e.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/entrar")
    public String login(@RequestParam("email") String email,
                        @RequestParam("senha") String senha,
                        HttpSession session, RedirectAttributes redirectAttributes) {

        try {
            Admin admin = adminService.autenticarAdmin(email, senha);

            session.setAttribute("adminLogado", admin);
            redirectAttributes.addFlashAttribute("successMessage", "Login efetuado com sucesso.");

            return "redirect:/administracao";
        } catch (AutenticacaoException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());

            return "redirect:/admin";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();

        redirectAttributes.addFlashAttribute("successMessage", "Você saiu da sua conta.");
        return "redirect:/index";
    }

    @PostMapping("/editarConta")
    public String editarConta(@ModelAttribute Admin adminAtualizado,
                              @RequestParam("senha-conta") String senhaConfirmacao,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        try {
            Admin adminLogado = (Admin) session.getAttribute("adminLogado");

            if (adminLogado == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Usuário não está logado.");
                return "redirect:/administracao";
            }

            // Valida a senha antes de permitir a edição
            if (!BCrypt.checkpw(senhaConfirmacao, adminLogado.getSenha())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Senha incorreta.");
                return "redirect:/administracao";
            }

            adminAtualizado.setId(adminLogado.getId());
            Admin adminAtualizadoFinal = adminService.editarPerfil(adminAtualizado, adminLogado);
            session.setAttribute("adminLogado", adminAtualizadoFinal);

            redirectAttributes.addFlashAttribute("successMessage", "Perfil atualizado com sucesso!");
            return "redirect:/administracao";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar o perfil: " + e.getMessage());
            return "redirect:/administracao";
        }
    }

}