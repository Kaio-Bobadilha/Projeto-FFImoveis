package com.site_victor.Model;

import com.site_victor.Enums.StatusQuarto;
import jakarta.persistence.*;

@Entity
@Table(name="quartos")
public class Quartos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_do_quarto")
    private int numeroDoQuarto;

    @Column(name = "tipo_do_quarto")
    private String tipoDoQuarto;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco_diaria")
    private double precoDiaria;

    @Enumerated(EnumType.STRING)
    private StatusQuarto status;

    public Quartos() {
    }

    public Quartos(int numeroDoQuarto, String tipoDoQuarto, String descricao, double precoDiaria) {
        this.numeroDoQuarto = numeroDoQuarto;
        this.tipoDoQuarto = tipoDoQuarto;
        this.descricao = descricao;
        this.precoDiaria = precoDiaria;
    }

    // get e set
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public int getNumeroDoQuarto() {return numeroDoQuarto;}
    public void setNumeroDoQuarto(int numeroDoQuarto) {this.numeroDoQuarto = numeroDoQuarto;}

    public String getTipoDoQuarto() {return tipoDoQuarto;}
    public void setTipoDoQuarto(String tipoDoQuarto) {this.tipoDoQuarto = tipoDoQuarto;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public double getPrecoDiaria() {return precoDiaria;}
    public void setPrecoDiaria(double precoDiaria) {this.precoDiaria = precoDiaria;}
}