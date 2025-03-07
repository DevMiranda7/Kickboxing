package kickboxing.controler.modalidade;

import kickboxing.service.modalidade.LightCombatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LightCombatController {

    @Autowired
    private LightCombatService lightCombatService;

    public LightCombatController(LightCombatService lightCombatService) {
        this.lightCombatService = lightCombatService;
    }

    @PostMapping("/pontosLightCombat")
    public String cadastrarLightCombat(@RequestParam String nomeLightCombat,
                                       @RequestParam String pontosLightCombat,
                                       RedirectAttributes redirectAttributes) {
        try {
            lightCombatService.cadastrarLightCombat(nomeLightCombat, pontosLightCombat);
            redirectAttributes.addFlashAttribute("successMessage", "Pontos cadastrados com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/rankingAdm";
    }

    @PostMapping("/lightCombats/{id}")
    public String excluirLightCombat(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            lightCombatService.excluirLightCombat(id);
            redirectAttributes.addFlashAttribute("successMessage", "Excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/rankingAdm";
    }
}
