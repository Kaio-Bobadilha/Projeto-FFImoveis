package com.site_victor.Model;

import jakarta.persistence.*;

@Entity
@Table(name="hospede")
public class Hospede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NomeCompleto")
    private String nomeCompleto;

    @Column(name="Cpf") // Unico
    private String cpf;

    @Column(name="Email") //
    private String email;

    @Column(name="Telefone")
    private String telefone;

    @Column(name="Endereco")
    private String endereco;

    @Column(name="Imagem")
    private String imagem;

    public Hospede(){}

    public Hospede(String nomeCompleto, String cpf, String email, String telefone, String endereco, String imagem){
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.imagem = imagem;
    }

    // Get e Set

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNomeCompleto() {return nomeCompleto;}
    public void setNomeCompleto(String nomeCompleto) {this.nomeCompleto = nomeCompleto;}

    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}

    public String getEndereco() {return endereco;}
    public void setEndereco(String endereco) {this.endereco = endereco;}

    public String getImagem() {return imagem;}

}
