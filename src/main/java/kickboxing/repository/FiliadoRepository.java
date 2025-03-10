package kickboxing.repository;

import kickboxing.model.Filiado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FiliadoRepository extends JpaRepository<Filiado, Long> {
    @Query("SELECT a FROM Filiado a WHERE a.cidadeFiliado = :cidade")
    List<Filiado> findByCidadeFiliado(@Param("cidade") String cidade);
}