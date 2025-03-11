package kickboxing.service;

import kickboxing.model.Filiado;
import kickboxing.repository.FiliadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Filiado> pesquisarFiliadosPorCidade(String cidade) {
        return filiadoRepository.findByCidadeFiliado(cidade);
    }

    public List<Filiado> pesquisarFiliadosPorNome(String nomeFiliado) {
        return filiadoRepository.findByNomeFiliado(nomeFiliado);
    }

    public List<Filiado> pesquisarFiliadosPorCidadeENome(String cidadeFiliado, String nomeFiliado) {
        return filiadoRepository.findByCidadeFiliadoAndNomeFiliado(cidadeFiliado, nomeFiliado);
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