package kickboxing.repository.modalidade;

import kickboxing.model.modalide.PointFight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointFightRepository extends JpaRepository<PointFight, Long> {
    List<PointFight> findAllByOrderByPontosPointFightDesc();
}