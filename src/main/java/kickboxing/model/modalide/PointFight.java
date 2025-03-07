package kickboxing.model.modalide;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class PointFight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPointFight;

    @Column(nullable = false)
    private String nomePointFight;

    @Column(nullable = false, precision = 10, scale = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pontosPointFight;

    public long getIdPointFight() {
        return idPointFight;
    }

    public void setIdPointFight(long idPointFight) {
        this.idPointFight = idPointFight;
    }

    public String getNomePointFight() {
        return nomePointFight;
    }

    public void setNomePointFight(String nomePointFight) {
        this.nomePointFight = nomePointFight;
    }

    public BigDecimal getPontosPointFight() {
        return pontosPointFight;
    }

    public void setPontosPointFight(BigDecimal pontosPointFight) {
        this.pontosPointFight = pontosPointFight;
    }
}
