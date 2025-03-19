package kickboxing.controller.modalidade;

import kickboxing.service.modalidade.LightContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LightContactController {

    @Autowired
    private LightContactService lightContactService;

    public LightContactController(LightContactService lightContactService) {
        this.lightContactService = lightContactService;
    }

    @PostMapping("/pontosLightContact")
    public String cadastrarLightContact(@RequestParam String nomeLightContact,
                                       @RequestParam String pontosLightContact,
                                       RedirectAttributes redirectAttributes) {
        try {
            lightContactService.cadastrarLightContact(nomeLightContact, pontosLightContact);
            redirectAttributes.addFlashAttribute("successMessage", "Pontos cadastrados com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/rankingAdm";
    }

    @PostMapping("/lightContacts/{id}")
    public String excluirLightContact(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            lightContactService.excluirLightContact(id);
            redirectAttributes.addFlashAttribute("successMessage", "Excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/rankingAdm";
    }
}
