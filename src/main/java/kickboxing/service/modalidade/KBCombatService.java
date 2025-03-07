package kickboxing.service.modalidade;

import kickboxing.model.modalide.KBCombat;
import kickboxing.repository.modalidade.KBCombatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class KBCombatService {

    @Autowired
    private KBCombatRepository kbCombatRepository;

    @Transactional
    public void cadastrarKBCombat(String nomeKBCombat, String pontosKBCombat) {
        try {
            String pontosFormatado = pontosKBCombat.trim().replace(",", ".");
            BigDecimal pontos = new BigDecimal(pontosFormatado).setScale(2, RoundingMode.HALF_UP);

            if (pontos.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Os pontos não podem ser negativos.");
            }

            KBCombat kbCombat = new KBCombat();
            kbCombat.setNomeKBCombat(nomeKBCombat.trim());
            kbCombat.setPontosKBCombat(pontos);

            kbCombatRepository.save(kbCombat);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido para pontos. Use números como 10,00.");
        }
    }
}
