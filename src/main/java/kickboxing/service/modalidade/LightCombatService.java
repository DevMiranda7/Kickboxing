package kickboxing.service.modalidade;

import kickboxing.model.modalide.LightCombat;
import kickboxing.repository.modalidade.LightCombatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class LightCombatService {

    @Autowired
    private LightCombatRepository lightCombatRepository;

    @Transactional
    public void cadastrarLightCombat(String nomeLightCombat, String pontosLightCombat) {
        try {
            String pontosFormatado = pontosLightCombat.trim().replace(",", ".");
            BigDecimal pontos = new BigDecimal(pontosFormatado).setScale(2, RoundingMode.HALF_UP);

            if (pontos.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Os pontos não podem ser negativos.");
            }

            LightCombat lightCombat = new LightCombat();
            lightCombat.setNomeLightCombat(nomeLightCombat.trim());
            lightCombat.setPontosLightCombat(pontos);

            lightCombatRepository.save(lightCombat);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido para pontos. Use números como 10,00.");
        }
    }
}
