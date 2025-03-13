package kickboxing.model.modalide;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class LowKicks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLowKicks;

    @Column(nullable = false)
    private String nomeLowKicks;

    @Column(nullable = false, precision = 10, scale = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pontosLowKicks;

    public long getIdLowKicks() {
        return idLowKicks;
    }

    public void setIdLowKicks(long idLowKicks) {
        this.idLowKicks = idLowKicks;
    }

    public String getNomeLowKicks() {
        return nomeLowKicks;
    }

    public void setNomeLowKicks(String nomeLowKicks) {
        this.nomeLowKicks = nomeLowKicks;
    }

    public BigDecimal getPontosLowKicks() {
        return pontosLowKicks;
    }

    public void setPontosLowKicks(BigDecimal pontosLowKicks) {
        this.pontosLowKicks = pontosLowKicks;
    }
}