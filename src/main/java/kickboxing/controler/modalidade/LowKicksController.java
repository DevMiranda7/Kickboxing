package kickboxing.controler.modalidade;

import kickboxing.service.modalidade.LowKicksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
