package kickboxing.model.modalide;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class LightCombat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLightCombat;

    @Column(nullable = false)
    private String nomeLightCombat;

    @Column(nullable = false, precision = 10, scale = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pontosLightCombat;

    public long getIdLightCombat() {
        return idLightCombat;
    }

    public void setIdLightCombat(long idLightCombat) {
        this.idLightCombat = idLightCombat;
    }

    public String getNomeLightCombat() {
        return nomeLightCombat;
    }

    public void setNomeLightCombat(String nomeLightCombat) {
        this.nomeLightCombat = nomeLightCombat;
    }

    public BigDecimal getPontosLightCombat() {
        return pontosLightCombat;
    }

    public void setPontosLightCombat(BigDecimal pontosLightCombat) {
        this.pontosLightCombat = pontosLightCombat;
    }
}
