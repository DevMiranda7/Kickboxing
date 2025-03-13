package kickboxing.service;

import kickboxing.model.Patrocinador;
import kickboxing.repository.PatrocinadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PatrocinadorService {

    @Autowired
    private PatrocinadorRepository patrocinadorRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/upload/patrocinadores";

    public List<Patrocinador> listarPatrocinadores() {
        return patrocinadorRepository.findAll();
    }

    public void salvarPatrocinador(Patrocinador patrocinador, MultipartFile imagemPatrocinador) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (!imagemPatrocinador.isEmpty()) {
            String nomeArquivo = System.currentTimeMillis() + "_" + imagemPatrocinador.getOriginalFilename();
            File destino = new File(uploadDir, nomeArquivo);
            imagemPatrocinador.transferTo(destino);

            patrocinador.setImagemPatrocinador("/upload/patrocinadores/" + nomeArquivo);
        }

        patrocinadorRepository.save(patrocinador);
    }

    public void excluirPatrocinador(Long id) {
        Patrocinador patrocinador = patrocinadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patrocinador não encontrado"));

        String caminhoImagem = UPLOAD_DIR + "/" + patrocinador.getImagemPatrocinador().substring(patrocinador.getImagemPatrocinador().lastIndexOf("/") + 1);
        File imagem = new File(caminhoImagem);
        if (imagem.exists()) {
            imagem.delete();
        }

        patrocinadorRepository.deleteById(id);
    }
}