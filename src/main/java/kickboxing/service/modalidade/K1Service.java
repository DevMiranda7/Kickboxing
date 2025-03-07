package kickboxing.service.modalidade;

import kickboxing.model.modalide.K1;
import kickboxing.model.modalide.LightCombat;
import kickboxing.repository.modalidade.K1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class K1Service {

    @Autowired
    private K1Repository k1Repository;

    @Transactional
    public void cadastrarK1(String nomeK1, String pontosK1) {
        try {
            String pontosFormatado = pontosK1.trim().replace(",", ".");
            BigDecimal pontos = new BigDecimal(pontosFormatado).setScale(2, RoundingMode.HALF_UP);

            if (pontos.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Os pontos não podem ser negativos.");
            }

            K1 k1 = new K1();
            k1.setNomeK1(nomeK1.trim());
            k1.setPontosK1(pontos);

            k1Repository.save(k1);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido para pontos. Use números como 10,00.");
        }
    }

    public List<K1> listarK1() {
        List<K1> k1s = k1Repository.findAll();
        return k1s.stream()
                .sorted((lc1, lc2) -> lc2.getPontosK1().compareTo(lc1.getPontosK1()))
                .collect(Collectors.toList());
    }
}
