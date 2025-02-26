package kickboxing.controler;

import kickboxing.model.Evento;
import kickboxing.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping("/criarEvento")
    public String criarEvento(@RequestParam("nomeEvento") String nomeEvento,
                              @RequestParam("descricaoEvento") String descricaoEvento,
                              @RequestParam("dataEvento") String dataEvento,
                              @RequestParam("horaEvento") String horaEvento,
                              @RequestParam("imagemEvento") MultipartFile imagemEvento,
                              RedirectAttributes redirectAttributes) {
        try {
            Evento evento = new Evento();
            evento.setNomeEvento(nomeEvento);
            evento.setDescricaoEvento(descricaoEvento);
            evento.setDataEvento(LocalDate.parse(dataEvento));
            evento.setHoraEvento(LocalTime.parse(horaEvento));

            eventoService.salvarEvento(evento, imagemEvento);

            redirectAttributes.addFlashAttribute("successMessage", "Evento cadastrado com sucesso!");
            return "redirect:/eventosAdm?refresh=" + System.currentTimeMillis();

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/eventosAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar evento: " + e.getMessage());
            return "redirect:/eventosAdm";
        }
    }

    public String listarEventos(Model model) {
        List<Evento> eventos = eventoService.listarEventos();
        model.addAttribute("eventos", eventos);
        return "eventosAdm";
    }

    @PostMapping("/eventos/{id}")
    public String excluirEvento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            eventoService.excluirEvento(id);
            redirectAttributes.addFlashAttribute("successMessage", "Evento excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir Evento: " + e.getMessage());
        }
        return "redirect:/eventosAdm";
    }

}