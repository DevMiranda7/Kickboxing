package kickboxing.model.modalide;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class LightContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLightContact;

    @Column(nullable = false)
    private String nomeLightContact;

    @Column(nullable = false, precision = 10, scale = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pontosLightContact;

    public long getIdLightContact() {
        return idLightContact;
    }

    public void setIdLightContact(long idLightContact) {
        this.idLightContact = idLightContact;
    }

    public String getNomeLightContact() {
        return nomeLightContact;
    }

    public void setNomeLightContact(String nomeLightContact) {
        this.nomeLightContact = nomeLightContact;
    }

    public BigDecimal getPontosLightContact() {
        return pontosLightContact;
    }

    public void setPontosLightContact(BigDecimal pontosLightContact) {
        this.pontosLightContact = pontosLightContact;
    }
}