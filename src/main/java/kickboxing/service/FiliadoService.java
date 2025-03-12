package kickboxing.service;

import kickboxing.model.Filiado;
import kickboxing.repository.FiliadoRepository;
import kickboxing.specifications.FiliadoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FiliadoService {

    @Autowired
    private FiliadoRepository filiadoRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/upload/filiados";

    @Transactional
    public void salvarFiliado(Filiado filiado, MultipartFile imagemFiliado) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (!imagemFiliado.isEmpty()) {
            String nomeArquivo = System.currentTimeMillis() + "_" + imagemFiliado.getOriginalFilename();
            File destino = new File(uploadDir, nomeArquivo);
            imagemFiliado.transferTo(destino);

            filiado.setImagemFiliado("/upload/filiados/" + nomeArquivo);
        }

        filiadoRepository.save(filiado);
    }

    public List<Filiado> listarFiliados() {
        return filiadoRepository.findAll();
    }

    public List<Filiado> pesquisarFiliados(String cidade, String nome, String registro, String tipoFaixa) {
        Specification<Filiado> spec = FiliadoSpecification.filtrarFiliados(cidade, nome, registro, tipoFaixa);
        return filiadoRepository.findAll(spec);
    }

    public void excluirFiliado(Long id) {
        Filiado filiado = filiadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filiado não encontrado"));

        // Remover imagem
        File imagem = new File(System.getProperty("user.dir") + "/src/main/resources/static" + filiado.getImagemFiliado());
        if (imagem.exists()) {
            imagem.delete();
        }

        filiadoRepository.deleteById(id);
    }

    public Filiado buscarFiliadoPorId(Long id) {
        return filiadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filiado não encontrado"));
    }

    //* AMBIENTE DE PRODUÇÃO ACESSE O ARQUIVO ---- "PRODUCAO.MD" ---- *//
    //* IMPORTANTE PARA ENTENDER COMO VAI FUNCIONAR!!!!!!! *//
}