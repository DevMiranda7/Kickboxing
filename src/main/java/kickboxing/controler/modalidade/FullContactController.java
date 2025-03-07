package kickboxing.controler.modalidade;

import kickboxing.service.modalidade.FullContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FullContactController {

    @Autowired
    private FullContactService fullContactService;

    public FullContactController(FullContactService fullContactService) {
        this.fullContactService = fullContactService;
    }

    @PostMapping("/pontosFullContact")
    public String cadastrarFullContact(@RequestParam String nomeFullContact,
                                       @RequestParam String pontosFullContact,
                                       RedirectAttributes redirectAttributes) {
        try {
            fullContactService.cadastrarFullContact(nomeFullContact, pontosFullContact);
            redirectAttributes.addFlashAttribute("successMessage", "Pontos cadastrados com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/rankingAdm";
    }
}
