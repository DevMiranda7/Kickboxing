package kickboxing.controler;

import kickboxing.model.Filiado;
import kickboxing.service.FiliadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FiliadoController {

    @Autowired
    private FiliadoService filiadoService;

    @PostMapping("/criarFiliado")
    public String criarFiliado(@RequestParam("registroFiliado") String registroFiliado,
                               @RequestParam("nomeFiliado") String nomeFiliado,
                               @RequestParam("cidadeFiliado") String cidadeFiliado,
                               @RequestParam(value = "graduacaoFiliado", required = false) String graduacaoFiliado,
                               @RequestParam(value = "graduadoEm") String graduadoEm,
                               @RequestParam("academiaFiliado") String academiaFiliado,
                               @RequestParam("responsavelFiliado") String responsavelFiliado,
                               @RequestParam("nascimentoFiliado") String nascimentoFiliado,
                               @RequestParam("generoFiliado") String generoFiliado,
                               @RequestParam("imagemFiliado") MultipartFile imagemFiliado,
                               RedirectAttributes redirectAttributes) {
        try {
            if (graduacaoFiliado == null || graduacaoFiliado.trim().isEmpty()) {
                    throw new IllegalArgumentException("É obrigatório selecionar uma faixa de graduação.");
            }
            if (graduacaoFiliado != null && (graduacaoFiliado.contains(",") || graduacaoFiliado.isEmpty())) {
                throw new IllegalArgumentException("Selecione apenas uma faixa de graduação.");
            }


            Filiado filiado = new Filiado();
            filiado.setRegistroFiliado(registroFiliado);
            filiado.setNomeFiliado(nomeFiliado);
            filiado.setCidadeFiliado(cidadeFiliado);
            filiado.setGraduacaoFiliado(graduacaoFiliado);
            filiado.setGraduadoEm(graduadoEm);
            filiado.setAcademiaFiliado(academiaFiliado);
            filiado.setResponsavelFiliado(responsavelFiliado);
            filiado.setNascimentoFiliado(LocalDate.parse(nascimentoFiliado));
            filiado.setGeneroFiliado(generoFiliado);

            filiadoService.salvarFiliado(filiado, imagemFiliado);

            redirectAttributes.addFlashAttribute("successMessage", "Filiado cadastrado com sucesso!");
            return "redirect:/filiadosAdm?refresh=" + System.currentTimeMillis();
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/filiadosAdm";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/filiadosAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar Filiado: " + e.getMessage());
            return "redirect:/filiadosAdm";
        }
    }



    public String listarFiliados(Model model) {
        List<Filiado> filiados = filiadoService.listarFiliados();

        List<String> cidades = filiados.stream()
                .map(Filiado::getCidadeFiliado)
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("cidades", cidades);
        model.addAttribute("filiados", filiados);
        return "filiadosAdm";
    }

    @GetMapping("/pesquisarFiliados")
    @ResponseBody
    public List<Filiado> pesquisarFiliados(@RequestParam("opcoes-cidades-filiados") String cidadeFiliado) {
        if (cidadeFiliado == null || cidadeFiliado.isEmpty()) {
            return filiadoService.listarFiliados();
        } else {
            return filiadoService.pesquisarFiliadosPorCidade(cidadeFiliado);
        }
    }

    @PostMapping("/filiados/{id}")
    public String excluirFiliados(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            filiadoService.excluirFiliado(id);
            redirectAttributes.addFlashAttribute("successMessage", "Filiado excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir Filiado: " + e.getMessage());
        }
        return "redirect:/filiadosAdm";
    }

    @PostMapping("/editarFiliado")
    public String editarFiliado(@RequestParam("idFiliado") Long idFiliado,
                                  @RequestParam("registroFiliado") String registroFiliado,
                                  @RequestParam("nomeFiliado") String nomeFiliado,
                                  @RequestParam("cidadeFiliado") String cidadeFiliado,
                                  @RequestParam(value = "graduacaoFiliado", required = false) String graduacaoFiliado,
                                  @RequestParam(value = "graduadoEm") String graduadoEm,
                                  @RequestParam("academiaFiliado") String academiaFiliado,
                                  @RequestParam("responsavelFiliado") String responsavelFiliado,
                                  @RequestParam("generoFiliado") String generoFiliado,
                                  @RequestParam(value = "imagemFiliado", required = false) MultipartFile imagemFiliado,
                                  RedirectAttributes redirectAttributes) {
        try {
            Filiado filiado = filiadoService.buscarFiliadoPorId(idFiliado);

            filiado.setRegistroFiliado(registroFiliado);
            filiado.setNomeFiliado(nomeFiliado);
            filiado.setCidadeFiliado(cidadeFiliado);
            filiado.setGraduacaoFiliado(graduacaoFiliado);
            filiado.setGraduadoEm(graduadoEm);
            filiado.setAcademiaFiliado(academiaFiliado);
            filiado.setResponsavelFiliado(responsavelFiliado);
            filiado.setGeneroFiliado(generoFiliado);

            filiadoService.salvarFiliado(filiado, imagemFiliado);

            redirectAttributes.addFlashAttribute("successMessage", "Filiado atualizado com sucesso!");
            return "redirect:/filiadosAdm?refresh=" + System.currentTimeMillis();

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/filiadosAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar filiado: " + e.getMessage());
            return "redirect:/filiadosAdm";
        }
    }
}