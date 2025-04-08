package kickboxing.controller;

import kickboxing.model.Professor;
import kickboxing.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/listarProfessoresPub")
    public String listarProfessoresPub(@RequestParam(defaultValue = "0") int pagina, Model model) {
        int tamanhoPagina = 30;
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

        List<Professor> todosProfessores = professorService.listarProfessores();

        List<String> cidades = todosProfessores.stream()
                .map(Professor::getCidadeProfessor)
                .distinct()
                .collect(Collectors.toList());

        Page<Professor> paginaProfessores = professorService.listarProfessoresPaginados(pageable);

        model.addAttribute("cidades", cidades);
        model.addAttribute("professores", paginaProfessores.getContent());
        model.addAttribute("paginaAtual", pagina);
        model.addAttribute("totalPaginas", paginaProfessores.getTotalPages());

        return "professoresPub";
    }

    @GetMapping("/listarProfessores")
    public String listarProfessores(@RequestParam(defaultValue = "0") int pagina, Model model) {
        int tamanhoPagina = 30;
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

        List<Professor> todosProfessores = professorService.listarProfessores();

        List<String> cidades = todosProfessores.stream()
                .map(Professor::getCidadeProfessor)
                .distinct()
                .collect(Collectors.toList());

        Page<Professor> paginaProfessores = professorService.listarProfessoresPaginados(pageable);

        model.addAttribute("cidades", cidades);
        model.addAttribute("professores", paginaProfessores.getContent());
        model.addAttribute("paginaAtual", pagina);
        model.addAttribute("totalPaginas", paginaProfessores.getTotalPages());

        return "professoresAdm";
    }

    @PostMapping("/criarProfessor")
    public String criarProfessor(@RequestParam("registroProfessor") String registroProfessor,
                                 @RequestParam("nomeProfessor") String nomeProfessor,
                                 @RequestParam("cidadeProfessor") String cidadeProfessor,
                                 @RequestParam(value = "graduacaoProfessor", required = false) String graduacaoProfessor,
                                 @RequestParam("graduadoEm") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate graduadoEm,
                                 @RequestParam("equipeProfessor") String equipeProfessor,
                                 @RequestParam("nascimentoProfessor") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate nascimentoProfessor,
                                 @RequestParam("generoProfessor") String generoProfessor,
                                 @RequestParam("imagemProfessor") MultipartFile imagemProfessor,
                                 RedirectAttributes redirectAttributes) {
        try {
            Integer parsedRegistroProfessor;
            try {
                parsedRegistroProfessor = Integer.parseInt(registroProfessor);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("O registro do professor deve ser um número.");
            }

            if (graduacaoProfessor == null || graduacaoProfessor.trim().isEmpty()) {
                throw new IllegalArgumentException("É obrigatório selecionar uma faixa de graduação.");
            }

            Professor professor = new Professor();
            professor.setRegistroProfessor(registroProfessor);
            professor.setNomeProfessor(nomeProfessor);
            professor.setCidadeProfessor(cidadeProfessor);
            professor.setGraduacaoProfessor(graduacaoProfessor);
            professor.setGraduadoEm(graduadoEm);
            professor.setEquipeProfessor(equipeProfessor);
            professor.setNascimentoProfessor(nascimentoProfessor);
            professor.setGeneroProfessor(generoProfessor);
            professor.setStatusProfessor("Ativo");

            professorService.salvarProfessor(professor, imagemProfessor);

            redirectAttributes.addFlashAttribute("successMessage", "Professor cadastrado com sucesso!");
            return "redirect:/professoresAdm?refresh=" + System.currentTimeMillis();

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/professoresAdm";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/professoresAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar professor: " + e.getMessage());
            return "redirect:/professoresAdm";
        }
    }

    @GetMapping("/pesquisarProfessores")
    @ResponseBody
    public List<Professor> pesquisarProfessores(
            @RequestParam(value = "opcoes-cidades-professores", required = false) String cidadeProfessor,
            @RequestParam(value = "nome-professor", required = false) String nomeProfessor,
            @RequestParam(value = "registro-professor", required = false) Integer registroProfessor) {

        if ((cidadeProfessor == null || cidadeProfessor.isEmpty())
                && (nomeProfessor == null || nomeProfessor.isEmpty())
                && (registroProfessor == null)) {
            return professorService.listarProfessores();

        } else if (registroProfessor != null) {
            return professorService.pesquisarProfessoresPorRegistro(registroProfessor);

        } else if (cidadeProfessor != null && !cidadeProfessor.isEmpty() && (nomeProfessor == null || nomeProfessor.isEmpty())) {
            return professorService.pesquisarProfessoresPorCidade(cidadeProfessor);

        } else if ((cidadeProfessor == null || cidadeProfessor.isEmpty()) && nomeProfessor != null && !nomeProfessor.isEmpty()) {
            return professorService.pesquisarProfessoresPorNome(nomeProfessor);

        } else {
            return professorService.pesquisarProfessoresPorCidadeENome(cidadeProfessor, nomeProfessor);
        }
    }

    @PostMapping("/professores/{id}")
    public String excluirProfessor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            professorService.excluirProfessor(id);
            redirectAttributes.addFlashAttribute("successMessage", "Professor excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir Professor: " + e.getMessage());
        }
        return "redirect:/professoresAdm";
    }

    @PostMapping("/editarProfessor")
    public String editarProfessor(@RequestParam("idProfessor") Long idProfessor,
                                  @RequestParam("registroProfessor") String registroProfessor,
                                  @RequestParam("nomeProfessor") String nomeProfessor,
                                  @RequestParam("cidadeProfessor") String cidadeProfessor,
                                  @RequestParam(value = "graduacaoProfessor", required = false) String graduacaoProfessor,
                                  @RequestParam("graduadoEm") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate graduadoEm,
                                  @RequestParam("equipeProfessor") String equipeProfessor,
                                  @RequestParam("generoProfessor") String generoProfessor,
                                  @RequestParam(value = "imagemProfessor", required = false) MultipartFile imagemProfessor,
                                  @RequestParam("statusProfessor") String statusProfessor,
                                  RedirectAttributes redirectAttributes) {
        try {

            if (graduacaoProfessor == null || graduacaoProfessor.trim().isEmpty()) {
                throw new IllegalArgumentException("É obrigatório selecionar uma faixa de graduação.");
            }

            Integer parsedRegistroProfessor;
            try {
                parsedRegistroProfessor = Integer.parseInt(registroProfessor);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("O registro do professor deve ser um número.");
            }

            Professor professor = professorService.buscarProfessorPorId(idProfessor);

            professor.setRegistroProfessor(registroProfessor);
            professor.setNomeProfessor(nomeProfessor);
            professor.setCidadeProfessor(cidadeProfessor);
            professor.setGraduacaoProfessor(graduacaoProfessor);
            professor.setGraduadoEm(graduadoEm);
            professor.setGeneroProfessor(generoProfessor);
            professor.setEquipeProfessor(equipeProfessor);
            professor.setStatusProfessor(statusProfessor);

            professorService.salvarProfessor(professor, imagemProfessor);

            redirectAttributes.addFlashAttribute("successMessage", "Professor atualizado com sucesso!");
            return "redirect:/professoresAdm?refresh=" + System.currentTimeMillis();

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/professoresAdm";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/professoresAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar professor: " + e.getMessage());
            return "redirect:/professoresAdm";
        }
    }
}