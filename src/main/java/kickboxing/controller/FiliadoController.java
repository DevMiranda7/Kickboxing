package kickboxing.controller;

import kickboxing.model.Filiado;
import kickboxing.service.FiliadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("/listarFiliadosPub")
    public String listarFiliadosPub(@RequestParam(defaultValue = "0") int pagina, Model model) {
        int tamanhoPagina = 30;
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

        List<Filiado> todosFiliados = filiadoService.listarFiliados();

        List<String> cidades = todosFiliados.stream()
                .map(Filiado::getCidadeFiliado)
                .distinct()
                .collect(Collectors.toList());

        Page<Filiado> paginaFiliados = filiadoService.listarFiliadosPaginados(pageable);

        model.addAttribute("cidades", cidades);
        model.addAttribute("filiados", paginaFiliados.getContent());
        model.addAttribute("paginaAtual", pagina);
        model.addAttribute("totalPaginas", paginaFiliados.getTotalPages());

        return "filiadosPub";
    }

    @GetMapping("/listarFiliados")
    public String listarFiliados(@RequestParam(defaultValue = "0") int pagina, Model model) {
        int tamanhoPagina = 30;
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

        List<Filiado> todosFiliados = filiadoService.listarFiliados();

        List<String> cidades = todosFiliados.stream()
                .map(Filiado::getCidadeFiliado)
                .distinct()
                .collect(Collectors.toList());

        Page<Filiado> paginaFiliados = filiadoService.listarFiliadosPaginados(pageable);

        model.addAttribute("cidades", cidades);
        model.addAttribute("filiados", paginaFiliados.getContent());
        model.addAttribute("paginaAtual", pagina);
        model.addAttribute("totalPaginas", paginaFiliados.getTotalPages());

        return "filiadosAdm";
    }

    @GetMapping("/listarFiliadosBlackBeltsPub")
    public String listarFiliadosBlackBeltsPub(@RequestParam(defaultValue = "0") int pagina, Model model) {
        int tamanhoPagina = 8;
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

        Page<Filiado> paginaFiliados = filiadoService.listarFiliadosBlackBeltsPaginados(pageable);

        model.addAttribute("filiados", paginaFiliados.getContent());
        model.addAttribute("paginaAtual", pagina);
        model.addAttribute("totalPaginas", paginaFiliados.getTotalPages());

        return "blackBeltsPub";
    }

    @PostMapping("/criarFiliado")
    public String criarFiliado(@RequestParam("registroFiliado") String registroFiliado,
                               @RequestParam("nomeFiliado") String nomeFiliado,
                               @RequestParam("cidadeFiliado") String cidadeFiliado,
                               @RequestParam(value = "graduacaoFiliado", required = false) String graduacaoFiliado,
                               @RequestParam("graduadoEm") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate graduadoEm,
                               @RequestParam("academiaFiliado") String academiaFiliado,
                               @RequestParam("responsavelFiliado") String responsavelFiliado,
                               @RequestParam("nascimentoFiliado") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate nascimentoFiliado,
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

            Integer parsedRegistroFiliado;
            try {
                parsedRegistroFiliado = Integer.parseInt(registroFiliado);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("O registro do filiado deve ser um número.");
            }

            Filiado filiado = new Filiado();
            filiado.setRegistroFiliado(registroFiliado);
            filiado.setNomeFiliado(nomeFiliado);
            filiado.setCidadeFiliado(cidadeFiliado);
            filiado.setGraduacaoFiliado(graduacaoFiliado);
            filiado.setGraduadoEm(graduadoEm);
            filiado.setAcademiaFiliado(academiaFiliado);
            filiado.setResponsavelFiliado(responsavelFiliado);
            filiado.setNascimentoFiliado(nascimentoFiliado);
            filiado.setGeneroFiliado(generoFiliado);
            filiado.setStatusFiliado("Ativo");

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

    @GetMapping("/pesquisarFiliados")
    @ResponseBody
    public List<Filiado> pesquisarFiliados(
            @RequestParam(value = "opcoes-cidades-filiados", required = false) String cidade,
            @RequestParam(value = "nome-filiado", required = false) String nome,
            @RequestParam(value = "registro-filiado", required = false) Integer registro,
            @RequestParam(value = "tipoFaixa", required = false) String tipoFaixa) {

        System.out.println("Cidade: " + cidade);
        System.out.println("Nome: " + nome);
        System.out.println("Registro: " + registro);
        System.out.println("Tipo Faixa: " + tipoFaixa);

        return filiadoService.pesquisarFiliados(cidade, nome, registro, tipoFaixa);
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

    @GetMapping("/filiado/{id}")
    public String exibirDetalhesFiliado(@PathVariable Long id, Model model) {
        Filiado filiado = filiadoService.buscarFiliadoPorId(id);
        model.addAttribute("filiado", filiado);
        return "detalhesFiliado";
    }

    @PostMapping("/editarFiliado")
    public String editarFiliado(@RequestParam("idFiliado") Long idFiliado,
                                @RequestParam("registroFiliado") String registroFiliado,
                                @RequestParam("nomeFiliado") String nomeFiliado,
                                @RequestParam("cidadeFiliado") String cidadeFiliado,
                                @RequestParam(value = "graduacaoFiliado", required = false) String graduacaoFiliado,
                                @RequestParam("graduadoEm") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate graduadoEm,
                                @RequestParam("academiaFiliado") String academiaFiliado,
                                @RequestParam("responsavelFiliado") String responsavelFiliado,
                                @RequestParam("generoFiliado") String generoFiliado,
                                @RequestParam(value = "imagemFiliado", required = false) MultipartFile imagemFiliado,
                                @RequestParam("statusFiliado") String statusFiliado,
                                RedirectAttributes redirectAttributes) {
        try {

            if (graduacaoFiliado == null || graduacaoFiliado.trim().isEmpty()) {
                throw new IllegalArgumentException("É obrigatório selecionar uma faixa de graduação.");
            }

            if (graduacaoFiliado != null && (graduacaoFiliado.contains(",") || graduacaoFiliado.isEmpty())) {
                throw new IllegalArgumentException("Selecione apenas uma faixa de graduação.");
            }

            Integer parsedRegistroFiliado;
            try {
                parsedRegistroFiliado = Integer.parseInt(registroFiliado);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("O registro do filiado deve ser um número.");
            }

            Filiado filiado = filiadoService.buscarFiliadoPorId(idFiliado);

            filiado.setRegistroFiliado(registroFiliado);
            filiado.setNomeFiliado(nomeFiliado);
            filiado.setCidadeFiliado(cidadeFiliado);
            filiado.setGraduacaoFiliado(graduacaoFiliado);
            filiado.setGraduadoEm(graduadoEm);
            filiado.setAcademiaFiliado(academiaFiliado);
            filiado.setResponsavelFiliado(responsavelFiliado);
            filiado.setGeneroFiliado(generoFiliado);
            filiado.setStatusFiliado(statusFiliado);

            filiadoService.salvarFiliado(filiado, imagemFiliado);

            redirectAttributes.addFlashAttribute("successMessage", "Filiado atualizado com sucesso!");
            return "redirect:/filiadosAdm?refresh=" + System.currentTimeMillis();

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/filiadosAdm";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/filiadosAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/filiadosAdm";
        }
    }
}