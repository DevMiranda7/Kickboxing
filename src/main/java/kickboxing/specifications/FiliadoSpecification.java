package kickboxing.specifications;

import kickboxing.model.Filiado;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class FiliadoSpecification {

    public static Specification<Filiado> filtrarFiliados(String cidade, String nome, String registro, String tipoFaixa) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (cidade != null && !cidade.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("cidadeFiliado"), cidade));
            }

            if (nome != null && !nome.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nomeFiliado")), "%" + nome.toLowerCase() + "%"));
            }

            if (registro != null && !registro.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("registroFiliado"), "%" + registro + "%"));
            }

            if (tipoFaixa != null) {
                if ("colorida".equals(tipoFaixa)) {
                    predicates.add(root.get("graduacaoFiliado").in("Amarela", "Laranja", "Verde", "Azul", "Marrom"));
                } else if ("preta".equals(tipoFaixa)) {
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(root.get("graduacaoFiliado"), "%Preta%"),
                            criteriaBuilder.like(root.get("graduacaoFiliado"), "%Coral%")
                    ));
                }
            }

            query.orderBy(criteriaBuilder.asc(root.get("nomeFiliado")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
