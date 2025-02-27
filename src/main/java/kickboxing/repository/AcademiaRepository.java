package kickboxing.repository;

import kickboxing.model.Academia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AcademiaRepository extends JpaRepository<Academia, Long> {
    @Query("SELECT DISTINCT a.cidadeAcademia FROM Academia a ORDER BY a.cidadeAcademia ASC")
    List<String> findDistinctCidades();

    @Query("SELECT a FROM Academia a WHERE a.cidadeAcademia = :cidade")
    List<Academia> findByCidadeAcademia(@Param("cidade") String cidade);
}