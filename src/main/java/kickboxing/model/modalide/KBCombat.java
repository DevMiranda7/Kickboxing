package kickboxing.model.modalide;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class KBCombat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idKBCombat;

    @Column(nullable = false)
    private String nomeKBCombat;

    @Column(nullable = false, precision = 10, scale = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pontosKBCombat;

    public long getIdKBCombat() {
        return idKBCombat;
    }

    public void setIdKBCombat(long idKBCombat) {
        this.idKBCombat = idKBCombat;
    }

    public String getNomeKBCombat() {
        return nomeKBCombat;
    }

    public void setNomeKBCombat(String nomeKBCombat) {
        this.nomeKBCombat = nomeKBCombat;
    }

    public BigDecimal getPontosKBCombat() {
        return pontosKBCombat;
    }

    public void setPontosKBCombat(BigDecimal pontosKBCombat) {
        this.pontosKBCombat = pontosKBCombat;
    }
}