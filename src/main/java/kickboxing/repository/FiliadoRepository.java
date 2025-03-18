package kickboxing.repository;

import kickboxing.model.Filiado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FiliadoRepository extends JpaRepository<Filiado, Long>, JpaSpecificationExecutor<Filiado> {

    @Query("SELECT f FROM Filiado f WHERE f.graduacaoFiliado LIKE 'Preta%' OR f.graduacaoFiliado LIKE 'Coral%'")
    Page<Filiado> findBlackBelts(Pageable pageable);

}