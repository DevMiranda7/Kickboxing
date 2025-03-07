package kickboxing.controler.modalidade;

import kickboxing.service.modalidade.PointFightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PointFightController {

    @Autowired
    private PointFightService pointFightService;

    public PointFightController(PointFightService pointFightService) {
        this.pointFightService = pointFightService;
    }

    @PostMapping("/pontosPointFight")
    public String cadastrarPointFight(@RequestParam String nomePointFight,
                                       @RequestParam String pontosPointFight,
                                       RedirectAttributes redirectAttributes) {
        try {
            pointFightService.cadastrarPointFight(nomePointFight, pontosPointFight);
            redirectAttributes.addFlashAttribute("successMessage", "Pontos cadastrados com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/rankingAdm";
    }

    @PostMapping("/pointFights/{id}")
    public String excluirPointFight(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            pointFightService.excluirPointFight(id);
            redirectAttributes.addFlashAttribute("successMessage", "Excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/rankingAdm";
    }
}
