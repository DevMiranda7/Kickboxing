package kickboxing.repository.modalidade;

import kickboxing.model.modalide.FullContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FullContactRepository extends JpaRepository<FullContact, Long> {
    List<FullContact> findAllByOrderByPontosFullContactDesc();
}