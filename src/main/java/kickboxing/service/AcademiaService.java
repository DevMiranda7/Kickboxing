package kickboxing.service;

import kickboxing.model.Academia;
import kickboxing.repository.AcademiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AcademiaService {

    @Autowired
    private AcademiaRepository academiaRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/upload/academias";

    public List<Academia> listarAcademias() {
        return academiaRepository.findAll();
    }

    public List<String> listarCidades() {
        return academiaRepository.findDistinctCidades();
    }

    public List<Academia> pesquisarAcademias(String cidade) {
        return academiaRepository.findByCidadeAcademia(cidade);
    }

    public List<Academia> pesquisarAcademiasPorNome(String nome) {
        return academiaRepository.findByNomeAcademia(nome);
    }

    public List<Academia> pesquisarAcademiasPorCidadeENome(String cidade, String nome) {
        return academiaRepository.findByCidadeAcademiaAndNomeAcademia(cidade, nome);
    }

    public void salvarAcademia(Academia academia, MultipartFile imagemAcademia) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (!imagemAcademia.isEmpty()) {
            String nomeArquivo = System.currentTimeMillis() + "_" + imagemAcademia.getOriginalFilename();
            File destino = new File(uploadDir, nomeArquivo);
            imagemAcademia.transferTo(destino);

            academia.setImagemAcademia("/upload/academias/" + nomeArquivo);
        }

        academiaRepository.save(academia);
    }

    public void excluirAcademia(Long id) {
        Academia academia = academiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Academia não encontrada"));

        String caminhoImagem = UPLOAD_DIR + "/" + academia.getImagemAcademia().substring(academia.getImagemAcademia().lastIndexOf("/") + 1);
        File imagem = new File(caminhoImagem);
        if (imagem.exists()) {
            imagem.delete();
        }

        academiaRepository.deleteById(id);
    }
}