package kickboxing.service;

import kickboxing.model.Professor;
import kickboxing.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/upload/professores";

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

    public List<Professor> listarProfessores() {
        return professorRepository.findAll();
    }

    public void excluirProfessor(Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        // Remover imagem
        File imagem = new File(System.getProperty("user.dir") + "/src/main/resources/static" + professor.getImagemProfessor());
        if (imagem.exists()) {
            imagem.delete();
        }

        professorRepository.deleteById(id);
    }

    //* AMBIENTE DE PRODUÇÃO ACESSE O ARQUIVO ---- "PRODUCAO.MD" ---- *//
    //* IMPORTANTE PARA ENTENDER COMO VAI FUNCIONAR!!!!!!! *//
}