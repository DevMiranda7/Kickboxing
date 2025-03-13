package kickboxing.controller.modalidade;

import kickboxing.service.modalidade.LowKicksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LowKicksController {

    @Autowired
    private LowKicksService lowKicksService;

    public LowKicksController(LowKicksService lowKicksService) {
        this.lowKicksService = lowKicksService;
    }

    @PostMapping("/pontosLowKicks")
    public String cadastrarLowKicks(@RequestParam String nomeLowKicks,
                                       @RequestParam String pontosLowKicks,
                                       RedirectAttributes redirectAttributes) {
        try {
            lowKicksService.cadastrarLowKicks(nomeLowKicks, pontosLowKicks);
            redirectAttributes.addFlashAttribute("successMessage", "Pontos cadastrados com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/rankingAdm";
    }

    @PostMapping("/lowKickss/{id}")
    public String excluirLowKicks(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            lowKicksService.excluirLowKicks(id);
            redirectAttributes.addFlashAttribute("successMessage", "Excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/rankingAdm";
    }
}
