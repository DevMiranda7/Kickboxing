package kickboxing.controler.modalidade;

import kickboxing.service.modalidade.KickLightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class KickLightController {

    @Autowired
    private KickLightService kickLightService;

    public KickLightService getKickLightService() {
        return kickLightService;
    }

    public void setKickLightService(KickLightService kickLightService) {
        this.kickLightService = kickLightService;
    }

    @PostMapping("/pontosKickLight")
    public String cadastrarKickLight(@RequestParam String nomeKickLight,
                                       @RequestParam String pontosKickLight,
                                       RedirectAttributes redirectAttributes) {
        try {
            kickLightService.cadastrarKickLight(nomeKickLight, pontosKickLight);
            redirectAttributes.addFlashAttribute("successMessage", "Pontos cadastrados com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/rankingAdm";
    }

    @PostMapping("/kickLights/{id}")
    public String excluirKickLight(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            kickLightService.excluirKickLight(id);
            redirectAttributes.addFlashAttribute("successMessage", "Excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/rankingAdm";
    }
}
