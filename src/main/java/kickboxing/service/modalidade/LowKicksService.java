package kickboxing.service.modalidade;

import kickboxing.model.modalide.LowKicks;
import kickboxing.repository.modalidade.LowKicksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class LowKicksService {

    @Autowired
    private LowKicksRepository lowKicksRepository;

    @Transactional
    public void cadastrarLowKicks(String nomeLowKicks, String pontosLowKicks) {
        try {
            String pontosFormatado = pontosLowKicks.trim().replace(",", ".");
            BigDecimal pontos = new BigDecimal(pontosFormatado).setScale(2, RoundingMode.HALF_UP);

            if (pontos.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Os pontos não podem ser negativos.");
            }

            LowKicks lowKicks = new LowKicks();
            lowKicks.setNomeLowKicks(nomeLowKicks.trim());
            lowKicks.setPontosLowKicks(pontos);

            lowKicksRepository.save(lowKicks);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido para pontos. Use números como 10,00.");
        }
    }
}
