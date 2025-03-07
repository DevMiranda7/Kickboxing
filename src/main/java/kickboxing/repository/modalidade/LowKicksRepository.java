package kickboxing.repository.modalidade;

import kickboxing.model.modalide.LowKicks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LowKicksRepository extends JpaRepository<LowKicks, Long> {
    List<LowKicks> findAllByOrderByPontosLowKicksDesc();
}