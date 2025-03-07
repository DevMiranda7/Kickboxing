package kickboxing.service.modalidade;

import kickboxing.model.modalide.FullContact;
import kickboxing.model.modalide.KickLight;
import kickboxing.model.modalide.LightCombat;
import kickboxing.repository.modalidade.KickLightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KickLightService {

    @Autowired
    private KickLightRepository kickLightRepository;

    @Transactional
    public void cadastrarKickLight(String nomeKickLight, String pontosKickLight) {
        try {
            String pontosFormatado = pontosKickLight.trim().replace(",", ".");
            BigDecimal pontos = new BigDecimal(pontosFormatado).setScale(2, RoundingMode.HALF_UP);

            if (pontos.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Os pontos não podem ser negativos.");
            }

            KickLight kickLight = new KickLight();
            kickLight.setNomeKickLight(nomeKickLight.trim());
            kickLight.setPontosKickLight(pontos);

            kickLightRepository.save(kickLight);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido para pontos. Use números como 10,00.");
        }
    }

    public List<KickLight> listarKickLight() {
        List<KickLight> kickLights = kickLightRepository.findAll();
        return kickLights.stream()
                .sorted((lc1, lc2) -> lc2.getPontosKickLight().compareTo(lc1.getPontosKickLight()))
                .collect(Collectors.toList());
    }

    public void excluirKickLight(Long id) {
        KickLight kickLight = kickLightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado"));

        kickLightRepository.deleteById(id);
    }
}
