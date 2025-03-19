package kickboxing.repository.modalidade;

import kickboxing.model.modalide.LightContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LightContactRepository extends JpaRepository<LightContact, Long> {
    List<LightContact> findAllByOrderByPontosLightContactDesc();
}