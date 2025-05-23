package kickboxing.controller;

import jakarta.servlet.http.HttpSession;
import kickboxing.model.Admin;
import kickboxing.model.modalide.*;
import kickboxing.service.AdminService;
import kickboxing.service.modalidade.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class MapearRotasController {

    @Autowired
    private EventoController eventoController;

    @Autowired
    private FiliadoController filiadoController;

    @Autowired
    private ProfessorController professorController;

    @Autowired
    private AcademiaController academiaController;

    @Autowired
    private PatrocinadorController patrocinadorController;

    @Autowired
    private LightContactService lightContactService;

    @Autowired
    private KickLightService kickLightService;

    @Autowired
    private PointFightService pointFightService;

    @Autowired
    private FullContactService fullContactService;

    @Autowired
    private LowKicksService lowKicksService;

    @Autowired
    private K1Service k1Service;

    @Autowired
    private KBCombatService kbCombatService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/index")
    public String indexPage(Model model) {
        return patrocinadorController.listarPatrocinadoresIndex(model);
    }

    @GetMapping("/filiePub")
    public String filiePubPage() {
        return "filiePub";
    }

    @GetMapping("/eventosPub")
    public String eventosPub(Model model) {
        return eventoController.listarEventosPub(model);
    }

    @GetMapping("/eventosAdm")
    public String eventosAdm(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        if (redirecionamento != null) {
            return redirecionamento;
        }

        return eventoController.listarEventos(model);
    }

    @GetMapping("/filiadosPub")
    public String filiadosPub(@RequestParam(defaultValue = "0") int pagina, Model model) {
        return filiadoController.listarFiliadosPub(pagina, model);
    }

    @GetMapping("/filiadosAdm")
    public String filiadosAdmPage(@RequestParam(defaultValue = "0") int pagina, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        if (redirecionamento != null) {
            return redirecionamento;
        }

        return filiadoController.listarFiliados(pagina, model);
    }

    @GetMapping("/professoresPub")
    public String professoresPub(@RequestParam(defaultValue = "0") int pagina, Model model) {
        return professorController.listarProfessoresPub(pagina, model);
    }

    @GetMapping("/professoresAdm")
    public String professoresAdmPage(@RequestParam(defaultValue = "0") int pagina, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        if (redirecionamento != null) {
            return redirecionamento;
        }

        return professorController.listarProfessores(pagina, model);
    }

    @GetMapping("/academiasPub")
    public String academiasPub(Model model) {
        return academiaController.listarAcademiasPub(model);
    }

    @GetMapping("/academiasAdm")
    public String academiasAdmPage(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        if (redirecionamento != null) {
            return redirecionamento;
        }

        return academiaController.listarAcademias(model);
    }

    @GetMapping("/rankingPub")
    public String rankingPub(Model model) {
        List<LightContact> lightContacts = lightContactService.listarLightContact();
        model.addAttribute("lightContacts", lightContacts);

        List<KickLight> kicklights = kickLightService.listarKickLight();
        model.addAttribute("kicklights", kicklights);

        List<PointFight> pointFights = pointFightService.listarPointFight();
        model.addAttribute("pointFights", pointFights);

        List<FullContact> fullContacts = fullContactService.listarFullContact();
        model.addAttribute("fullContacts", fullContacts);

        List<LowKicks> lowKickss = lowKicksService.listarLowKicks();
        model.addAttribute("lowKickss", lowKickss);

        List<K1> k1s = k1Service.listarK1();
        model.addAttribute("k1s", k1s);

        List<KBCombat> kbCombats = kbCombatService.listarKBCombat();
        model.addAttribute("kbCombats", kbCombats);

        return "rankingPub";
    }

    @GetMapping("/rankingAdm")
    public String rankingAdmPage(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        if (redirecionamento != null) {
            return redirecionamento;
        }

        List<LightContact> lightContacts = lightContactService.listarLightContact();
        model.addAttribute("lightContacts", lightContacts);

        List<KickLight> kicklights = kickLightService.listarKickLight();
        model.addAttribute("kicklights", kicklights);

        List<PointFight> pointFights = pointFightService.listarPointFight();
        model.addAttribute("pointFights", pointFights);

        List<FullContact> fullContacts = fullContactService.listarFullContact();
        model.addAttribute("fullContacts", fullContacts);

        List<LowKicks> lowKickss = lowKicksService.listarLowKicks();
        model.addAttribute("lowKickss", lowKickss);

        List<K1> k1s = k1Service.listarK1();
        model.addAttribute("k1s", k1s);

        List<KBCombat> kbCombats = kbCombatService.listarKBCombat();
        model.addAttribute("kbCombats", kbCombats);

        return "rankingAdm";
    }

    @GetMapping("/blackBeltsPub")
    public String blackBeltsPubPage(@RequestParam(defaultValue = "0") int pagina, Model model) {
        return filiadoController.listarFiliadosBlackBeltsPub(pagina, model);
    }

    @GetMapping("/patrocinadoresAdm")
    public String patrocinadoresAdmPage(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        if (redirecionamento != null) {
            return redirecionamento;
        }

        return patrocinadorController.listarPatrocinadores(model);
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/administracao")
    public String logado(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        if (redirecionamento != null) {
            return redirecionamento;
        }

        Admin admin = (Admin) session.getAttribute("adminLogado");
        if (admin != null && admin.getDataRegistro() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = admin.getDataRegistro().format(formatter);
            model.addAttribute("dataRegistroFormatada", dataFormatada);
        }

        model.addAttribute("admin", admin);
        return "administracao";
    }

    @GetMapping("/recuperarSenha")
    public String mostrarFormularioRecuperacao(@RequestParam("token") String token, Model model) {
        if (adminService.isTokenValido(token)) {
            model.addAttribute("token", token);
            return "recuperarSenha";

        } else {
            model.addAttribute("errorMessage", "Token de recuperação inválido ou expirado.");
            return "redirect:/index";
        }
    }

    private String verificarSessao(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdminLogado(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Você precisa ser um administrador autenticado.");
            return "redirect:/index";
        }
        return null;
    }

    private boolean isAdminLogado(HttpSession session) {
        return session.getAttribute("adminLogado") != null;
    }
}