package kickboxing.model.modalide;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class K1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idK1;

    @Column(nullable = false)
    private String nomeK1;

    @Column(nullable = false, precision = 10, scale = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pontosK1;

    public long getIdK1() {
        return idK1;
    }

    public void setIdK1(long idK1) {
        this.idK1 = idK1;
    }

    public String getNomeK1() {
        return nomeK1;
    }

    public void setNomeK1(String nomeK1) {
        this.nomeK1 = nomeK1;
    }

    public BigDecimal getPontosK1() {
        return pontosK1;
    }

    public void setPontosK1(BigDecimal pontosK1) {
        this.pontosK1 = pontosK1;
    }
}
