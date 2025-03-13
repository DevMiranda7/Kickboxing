package kickboxing.service;

import kickboxing.model.Professor;
import kickboxing.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/upload/professores";

    public List<Professor> listarProfessores() {
        return professorRepository.findAll(Sort.by(Sort.Order.asc("nomeProfessor")));
    }

    public List<Professor> pesquisarProfessoresPorCidade(String cidade) {
        return professorRepository.findByCidadeProfessor(cidade)
                .stream()
                .sorted(Comparator.comparing(Professor::getNomeProfessor))
                .collect(Collectors.toList());
    }

    public List<Professor> pesquisarProfessoresPorNome(String nomeProfessor) {
        return professorRepository.findByNomeProfessor(nomeProfessor)
                .stream()
                .sorted(Comparator.comparing(Professor::getNomeProfessor))
                .collect(Collectors.toList());
    }

    public List<Professor> pesquisarProfessoresPorCidadeENome(String cidadeProfessor, String nomeProfessor) {
        return professorRepository.findByCidadeProfessorAndNomeProfessor(cidadeProfessor, nomeProfessor)
                .stream()
                .sorted(Comparator.comparing(Professor::getNomeProfessor))
                .collect(Collectors.toList());
    }

    public List<Professor> pesquisarProfessoresPorRegistro(String registroProfessor) {
        return professorRepository.findByRegistroProfessor(registroProfessor);
    }

    public Page<Professor> listarProfessoresPaginados(Pageable pageable) {
        Pageable pageableComOrdenacao = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.asc("nomeProfessor")));
        return professorRepository.findAll(pageableComOrdenacao);
    }

    public Professor buscarProfessorPorId(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    public void salvarProfessor(Professor professor, MultipartFile imagemProfessor) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (!imagemProfessor.isEmpty()) {
            String nomeArquivo = System.currentTimeMillis() + "_" + imagemProfessor.getOriginalFilename();
            File destino = new File(uploadDir, nomeArquivo);
            imagemProfessor.transferTo(destino);

            professor.setImagemProfessor("/upload/professores/" + nomeArquivo);
        }

        professorRepository.save(professor);
    }

    public void excluirProfessor(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        String caminhoImagem = UPLOAD_DIR + "/" + professor.getImagemProfessor().substring(professor.getImagemProfessor().lastIndexOf("/") + 1);
        File imagem = new File(caminhoImagem);
        if (imagem.exists()) {
            imagem.delete();
        }

        professorRepository.deleteById(id);
    }
}