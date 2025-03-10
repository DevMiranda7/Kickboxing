package kickboxing.controler;

import kickboxing.model.Professor;
import kickboxing.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/criarProfessor")
    public String criarProfessor(@RequestParam("registroProfessor") String registroProfessor,
                                 @RequestParam("nomeProfessor") String nomeProfessor,
                                 @RequestParam("cidadeProfessor") String cidadeProfessor,
                                 @RequestParam(value = "graduacaoProfessor", required = false) String graduacaoProfessor,
                                 @RequestParam("graduadoEm") LocalDate graduadoEm,
                                 @RequestParam("equipeProfessor") String equipeProfessor,
                                 @RequestParam("nascimentoProfessor") String nascimentoProfessor,
                                 @RequestParam("generoProfessor") String generoProfessor,
                                 @RequestParam("imagemProfessor") MultipartFile imagemProfessor,
                                 RedirectAttributes redirectAttributes) {
        try {

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
            professor.setNascimentoProfessor(LocalDate.parse(nascimentoProfessor));
            professor.setGeneroProfessor(generoProfessor);

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

    public String listarProfessores(Model model) {
        List<Professor> professores = professorService.listarProfessores();

        List<String> cidades = professores.stream()
                .map(Professor::getCidadeProfessor)
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("cidades", cidades);
        model.addAttribute("professores", professores);
        return "professoresAdm";
    }

    @GetMapping("/pesquisarProfessores")
    @ResponseBody
    public List<Professor> pesquisarProfessores(@RequestParam("opcoes-cidades-professores") String cidadeProfessor) {
        if (cidadeProfessor == null || cidadeProfessor.isEmpty()) {
            return professorService.listarProfessores();
        } else {
            return professorService.pesquisarProfessoresPorCidade(cidadeProfessor);
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
                                  @RequestParam("graduadoEm") LocalDate graduadoEm,
                                  @RequestParam("equipeProfessor") String equipeProfessor,
                                  @RequestParam("generoProfessor") String generoProfessor,
                                  @RequestParam(value = "imagemProfessor", required = false) MultipartFile imagemProfessor,
                                  RedirectAttributes redirectAttributes) {
        try {

            if (graduacaoProfessor == null || graduacaoProfessor.trim().isEmpty()) {
                throw new IllegalArgumentException("É obrigatório selecionar uma faixa de graduação.");
            }

            Professor professor = professorService.buscarProfessorPorId(idProfessor);

            professor.setRegistroProfessor(registroProfessor);
            professor.setNomeProfessor(nomeProfessor);
            professor.setCidadeProfessor(cidadeProfessor);
            professor.setGraduacaoProfessor(graduacaoProfessor);
            professor.setGraduadoEm(graduadoEm);
            professor.setGeneroProfessor(generoProfessor);
            professor.setEquipeProfessor(equipeProfessor);

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