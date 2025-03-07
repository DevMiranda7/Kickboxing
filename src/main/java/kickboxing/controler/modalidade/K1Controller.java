package kickboxing.controler.modalidade;

import kickboxing.service.modalidade.K1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class K1Controller {

    @Autowired
    private K1Service k1Service;

    public K1Controller(K1Service k1Service) {
        this.k1Service = k1Service;
    }

    @PostMapping("/pontosK1")
    public String cadastrarK1(@RequestParam String nomeK1,
                                       @RequestParam String pontosK1,
                                       RedirectAttributes redirectAttributes) {
        try {
            k1Service.cadastrarK1(nomeK1, pontosK1);
            redirectAttributes.addFlashAttribute("successMessage", "Pontos cadastrados com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/rankingAdm";
    }

    @PostMapping("/k1s/{id}")
    public String excluirK1(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            k1Service.excluirK1(id);
            redirectAttributes.addFlashAttribute("successMessage", "Excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/rankingAdm";
    }
}
