package kickboxing.repository.modalidade;

import kickboxing.model.modalide.K1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface K1Repository extends JpaRepository<K1, Long> {
    List<K1> findAllByOrderByPontosK1Desc();
}