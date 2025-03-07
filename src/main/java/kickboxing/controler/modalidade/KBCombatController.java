package kickboxing.controler.modalidade;

import kickboxing.service.modalidade.KBCombatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class KBCombatController {

    @Autowired
    private KBCombatService kbCombatService;

    public KBCombatController(KBCombatService kbCombatService) {
        this.kbCombatService = kbCombatService;
    }

    @PostMapping("/pontosKBCombat")
    public String cadastrarKBCombat(@RequestParam String nomeKBCombat,
                                       @RequestParam String pontosKBCombat,
                                       RedirectAttributes redirectAttributes) {
        try {
            kbCombatService.cadastrarKBCombat(nomeKBCombat, pontosKBCombat);
            redirectAttributes.addFlashAttribute("successMessage", "Pontos cadastrados com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/rankingAdm";
    }

    @PostMapping("/kbCombats/{id}")
    public String excluirKBCombat(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            kbCombatService.excluirKBCombat(id);
            redirectAttributes.addFlashAttribute("successMessage", "Excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/rankingAdm";
    }
}
