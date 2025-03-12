package kickboxing.repository;

import kickboxing.model.Filiado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FiliadoRepository extends JpaRepository<Filiado, Long> {
    @Query("SELECT a FROM Filiado a WHERE a.cidadeFiliado = :cidade")
    List<Filiado> findByCidadeFiliado(@Param("cidade") String cidade);

    @Query("SELECT a FROM Filiado a WHERE LOWER(a.nomeFiliado) LIKE LOWER(CONCAT('%', :nomeFiliado, '%'))")
    List<Filiado> findByNomeFiliado(@Param("nomeFiliado") String nomeFiliado);

    @Query("SELECT a FROM Filiado a WHERE a.cidadeFiliado = :cidadeFiliado AND LOWER(a.nomeFiliado) LIKE LOWER(CONCAT('%', :nomeFiliado, '%'))")
    List<Filiado> findByCidadeFiliadoAndNomeFiliado(@Param("cidadeFiliado") String cidadeFiliado, @Param("nomeFiliado") String nomeFiliado);

    @Query("SELECT p FROM Filiado p WHERE p.registroFiliado LIKE CONCAT('%', :registroFiliado, '%')")
    List<Filiado> findByRegistroFiliado(@Param("registroFiliado") String registroFiliado);
}