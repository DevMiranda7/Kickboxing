package kickboxing.repository.modalidade;

import kickboxing.model.modalide.KBCombat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KBCombatRepository extends JpaRepository<KBCombat, Long> {
    List<KBCombat> findAllByOrderByPontosKBCombatDesc();
}