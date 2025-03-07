package kickboxing.service.modalidade;

import kickboxing.model.modalide.PointFight;
import kickboxing.repository.modalidade.PointFightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PointFightService {

    @Autowired
    private PointFightRepository pointFightRepository;

    @Transactional
    public void cadastrarPointFight(String nomePointFight, String pontosPointFight) {
        try {
            String pontosFormatado = pontosPointFight.trim().replace(",", ".");
            BigDecimal pontos = new BigDecimal(pontosFormatado).setScale(2, RoundingMode.HALF_UP);

            if (pontos.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Os pontos não podem ser negativos.");
            }

            PointFight pointFight = new PointFight();
            pointFight.setNomePointFight(nomePointFight.trim());
            pointFight.setPontosPointFight(pontos);

            pointFightRepository.save(pointFight);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido para pontos. Use números como 10,00.");
        }
    }
}
