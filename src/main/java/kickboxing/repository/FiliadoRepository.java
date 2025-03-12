package kickboxing.repository;

import kickboxing.model.Filiado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FiliadoRepository extends JpaRepository<Filiado, Long>, JpaSpecificationExecutor<Filiado> {
}