package kickboxing.repository.modalidade;

import kickboxing.model.modalide.LightCombat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LightCombatRepository extends JpaRepository<LightCombat, Long> {
    List<LightCombat> findAllByOrderByPontosLightCombatDesc();
}