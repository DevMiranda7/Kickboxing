package kickboxing.service.modalidade;

import kickboxing.model.modalide.KBCombat;
import kickboxing.repository.modalidade.KBCombatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<KBCombat> listarKBCombat() {
        List<KBCombat> kbCombats = kbCombatRepository.findAll();
        return kbCombats.stream()
                .sorted((lc1, lc2) -> lc2.getPontosKBCombat().compareTo(lc1.getPontosKBCombat()))
                .collect(Collectors.toList());
    }

    public void excluirKBCombat(Long id) {
        KBCombat kbCombat = kbCombatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não encontrado"));

        kbCombatRepository.deleteById(id);
    }
}
