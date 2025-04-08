package kickboxing.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProfessor;

    @Column(nullable = false)
    private String registroProfessor;

    @Column(nullable = false)
    private String nomeProfessor;

    @Column(nullable = false)
    private String cidadeProfessor;

    @Column(nullable = true)
    private String graduacaoProfessor;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate nascimentoProfessor;

    @Column(nullable = false)
    private String equipeProfessor;

    @Column(nullable = false)
    private String imagemProfessor;

    @Column(nullable = false)
    private String generoProfessor;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate graduadoEm;

    @Column(nullable = false)
    private String statusProfessor;

    public long getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(long idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getRegistroProfessor() {
        return registroProfessor;
    }

    public void setRegistroProfessor(String registroProfessor) {
        this.registroProfessor = registroProfessor;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public String getCidadeProfessor() {
        return cidadeProfessor;
    }

    public void setCidadeProfessor(String cidadeProfessor) {
        this.cidadeProfessor = cidadeProfessor;
    }

    public String getGraduacaoProfessor() {
        return graduacaoProfessor;
    }

    public void setGraduacaoProfessor(String graduacaoProfessor) {
        this.graduacaoProfessor = graduacaoProfessor;
    }

    public LocalDate getNascimentoProfessor() {
        return nascimentoProfessor;
    }

    public void setNascimentoProfessor(LocalDate nascimentoProfessor) {
        this.nascimentoProfessor = nascimentoProfessor;
    }

    public String getEquipeProfessor() {
        return equipeProfessor;
    }

    public void setEquipeProfessor(String equipeProfessor) {
        this.equipeProfessor = equipeProfessor;
    }

    public String getImagemProfessor() {
        return imagemProfessor;
    }

    public void setImagemProfessor(String imagemProfessor) {
        this.imagemProfessor = imagemProfessor;
    }

    public String getGeneroProfessor() {
        return generoProfessor;
    }

    public void setGeneroProfessor(String generoProfessor) {
        this.generoProfessor = generoProfessor;
    }

    public LocalDate getGraduadoEm() {
        return graduadoEm;
    }

    public void setGraduadoEm(LocalDate graduadoEm) {
        this.graduadoEm = graduadoEm;
    }

    public String getStatusProfessor() {
        return statusProfessor;
    }

    public void setStatusProfessor(String statusProfessor) {
        this.statusProfessor = statusProfessor;
    }
}