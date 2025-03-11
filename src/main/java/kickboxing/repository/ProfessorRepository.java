package kickboxing.repository;

import kickboxing.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Query("SELECT p FROM Professor p WHERE p.cidadeProfessor = :cidade")
    List<Professor> findByCidadeProfessor(@Param("cidade") String cidade);

    @Query("SELECT a FROM Professor a WHERE LOWER(a.nomeProfessor) LIKE LOWER(CONCAT('%', :nomeProfessor, '%'))")
    List<Professor> findByNomeProfessor(@Param("nomeProfessor") String nomeProfessor);

    @Query("SELECT a FROM Professor a WHERE a.cidadeProfessor = :cidadeProfessor AND LOWER(a.nomeProfessor) LIKE LOWER(CONCAT('%', :nomeProfessor, '%'))")
    List<Professor> findByCidadeProfessorAndNomeProfessor(@Param("cidadeProfessor") String cidadeProfessor, @Param("nomeProfessor") String nomeProfessor);

}