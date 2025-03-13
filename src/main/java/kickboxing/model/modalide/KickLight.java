package kickboxing.model.modalide;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class KickLight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idKickLight;

    @Column(nullable = false)
    private String nomeKickLight;

    @Column(nullable = false, precision = 10, scale = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pontosKickLight;

    public long getIdKickLight() {
        return idKickLight;
    }

    public void setIdKickLight(long idKickLight) {
        this.idKickLight = idKickLight;
    }

    public String getNomeKickLight() {
        return nomeKickLight;
    }

    public void setNomeKickLight(String nomeKickLight) {
        this.nomeKickLight = nomeKickLight;
    }

    public BigDecimal getPontosKickLight() {
        return pontosKickLight;
    }

    public void setPontosKickLight(BigDecimal pontosKickLight) {
        this.pontosKickLight = pontosKickLight;
    }
}