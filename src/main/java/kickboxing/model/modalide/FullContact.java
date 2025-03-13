package kickboxing.model.modalide;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class FullContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFullContact;

    @Column(nullable = false)
    private String nomeFullContact;

    @Column(nullable = false, precision = 10, scale = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pontosFullContact;

    public long getIdFullContact() {
        return idFullContact;
    }

    public void setIdFullContact(long idFullContact) {
        this.idFullContact = idFullContact;
    }

    public String getNomeFullContact() {
        return nomeFullContact;
    }

    public void setNomeFullContact(String nomeFullContact) {
        this.nomeFullContact = nomeFullContact;
    }

    public BigDecimal getPontosFullContact() {
        return pontosFullContact;
    }

    public void setPontosFullContact(BigDecimal pontosFullContact) {
        this.pontosFullContact = pontosFullContact;
    }
}