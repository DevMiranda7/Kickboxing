package kickboxing.controller;

import kickboxing.model.Patrocinador;
import kickboxing.service.PatrocinadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class PatrocinadorController {

    @Autowired
    private PatrocinadorService patrocinadorService;

    public String listarPatrocinadores(Model model) {
        List<Patrocinador> patrocinadores = patrocinadorService.listarPatrocinadores();
        model.addAttribute("patrocinadores", patrocinadores);
        return "patrocinadoresAdm";
    }

    @PostMapping("/criarPatrocinador")
    public String criarPatrocinador(@RequestParam("linkPatrocinador") String linkPatrocinador,
                                    @RequestParam("imagemPatrocinador") MultipartFile imagemPatrocinador,
                                    RedirectAttributes redirectAttributes) {
        try {
            Patrocinador patrocinador = new Patrocinador();
            patrocinador.setLinkPatrocinador(linkPatrocinador);
            patrocinadorService.salvarPatrocinador(patrocinador, imagemPatrocinador);

            redirectAttributes.addFlashAttribute("successMessage", "Patrocinador cadastrado com sucesso!");
            return "redirect:/patrocinadoresAdm?refresh=" + System.currentTimeMillis();

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/patrocinadoresAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar patrocinador: " + e.getMessage());
            return "redirect:/patrocinadoresAdm";
        }
    }

    @PostMapping("/patrocinadores/{id}")
    public String excluirPatrocinador(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            patrocinadorService.excluirPatrocinador(id);
            redirectAttributes.addFlashAttribute("successMessage", "Patrocinador excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir patrocinador: " + e.getMessage());
        }
        return "redirect:/patrocinadoresAdm";
    }
}