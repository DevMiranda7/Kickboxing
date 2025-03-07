package kickboxing.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Academia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAcademia;

    @Column(nullable = false)
    private String nomeAcademia;

    @Column(nullable = false)
    private String responsavelAcademia;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enderecoAcademia;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String bairroAcademia;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String cidadeAcademia;

    @Column(nullable = false)
    private String contatoAcademia;

    @Column(nullable = false)
    private String imagemAcademia;

    public long getIdAcademia() {
        return idAcademia;
    }

    public void setIdAcademia(long idAcademia) {
        this.idAcademia = idAcademia;
    }

    public String getNomeAcademia() {
        return nomeAcademia;
    }

    public void setNomeAcademia(String nomeAcademia) {
        this.nomeAcademia = nomeAcademia;
    }

    public String getResponsavelAcademia() {
        return responsavelAcademia;
    }

    public void setResponsavelAcademia(String responsavelAcademia) {
        this.responsavelAcademia = responsavelAcademia;
    }

    public String getEnderecoAcademia() {
        return enderecoAcademia;
    }

    public void setEnderecoAcademia(String enderecoAcademia) {
        this.enderecoAcademia = enderecoAcademia;
    }

    public String getCidadeAcademia() {
        return cidadeAcademia;
    }

    public void setCidadeAcademia(String cidadeAcademia) {
        this.cidadeAcademia = cidadeAcademia;
    }

    public String getContatoAcademia() {
        return contatoAcademia;
    }

    public void setContatoAcademia(String contatoAcademia) {
        this.contatoAcademia = contatoAcademia;
    }

    public String getImagemAcademia() {
        return imagemAcademia;
    }

    public void setImagemAcademia(String imagemAcademia) {
        this.imagemAcademia = imagemAcademia;
    }
}