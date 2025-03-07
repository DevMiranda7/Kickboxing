package kickboxing.repository.modalidade;

import kickboxing.model.modalide.KickLight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KickLightRepository extends JpaRepository<KickLight, Long> {
    List<KickLight> findAllByOrderByPontosKickLightDesc();
}