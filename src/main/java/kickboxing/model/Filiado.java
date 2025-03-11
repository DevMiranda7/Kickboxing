package kickboxing.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Filiado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFiliado;

    @Column(nullable = false)
    private String registroFiliado;

    @Column(nullable = false)
    private String nomeFiliado;

    @Column(nullable = false)
    private String cidadeFiliado;

    @Column(nullable = true)
    private String graduacaoFiliado;

    @Column
    private LocalDate graduadoEm;

    @Column
    private LocalDate nascimentoFiliado;

    @Column(nullable = false)
    private String academiaFiliado;

    @Column(nullable = false)
    private String responsavelFiliado;

    @Column(nullable = false)
    private String imagemFiliado;

    @Column(nullable = false)
    private String generoFiliado;

    public long getIdFiliado() {
        return idFiliado;
    }

    public void setIdFiliado(long idFiliado) {
        this.idFiliado = idFiliado;
    }

    public String getRegistroFiliado() {
        return registroFiliado;
    }

    public void setRegistroFiliado(String registroFiliado) {
        this.registroFiliado = registroFiliado;
    }

    public String getNomeFiliado() {
        return nomeFiliado;
    }

    public void setNomeFiliado(String nomeFiliado) {
        this.nomeFiliado = nomeFiliado;
    }

    public String getCidadeFiliado() {
        return cidadeFiliado;
    }

    public void setCidadeFiliado(String cidadeFiliado) {
        this.cidadeFiliado = cidadeFiliado;
    }

    public String getGraduacaoFiliado() {
        return graduacaoFiliado;
    }

    public void setGraduacaoFiliado(String graduacaoFiliado) {
        this.graduacaoFiliado = graduacaoFiliado;
    }

    public LocalDate getGraduadoEm() {
        return graduadoEm;
    }

    public void setGraduadoEm(LocalDate graduadoEm) {
        this.graduadoEm = graduadoEm;
    }

    public LocalDate getNascimentoFiliado() {
        return nascimentoFiliado;
    }

    public void setNascimentoFiliado(LocalDate nascimentoFiliado) {
        this.nascimentoFiliado = nascimentoFiliado;
    }

    public String getAcademiaFiliado() {
        return academiaFiliado;
    }

    public void setAcademiaFiliado(String academiaFiliado) {
        this.academiaFiliado = academiaFiliado;
    }

    public String getResponsavelFiliado() {
        return responsavelFiliado;
    }

    public void setResponsavelFiliado(String responsavelFiliado) {
        this.responsavelFiliado = responsavelFiliado;
    }

    public String getImagemFiliado() {
        return imagemFiliado;
    }

    public void setImagemFiliado(String imagemFiliado) {
        this.imagemFiliado = imagemFiliado;
    }

    public String getGeneroFiliado() {
        return generoFiliado;
    }

    public void setGeneroFiliado(String generoFiliado) {
        this.generoFiliado = generoFiliado;
    }
}