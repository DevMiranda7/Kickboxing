package kickboxing.service.modalidade;

import kickboxing.model.modalide.KickLight;
import kickboxing.repository.modalidade.KickLightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
}
