package com.site_victor.Model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="controle_de_hospedagem")
public class ControleDeHospedagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="reserva_id",nullable = false)
    private ControleDeReservas reserva;

    @Column(name="data_checkin_real")
    private Timestamp dataCheckinReal;

    @Column(name="data_ckeckout_real")
    private Timestamp dataCkeckoutReal;

    @Column(name="observacoes", columnDefinition = "TEXT")
    private String observacoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ControleDeReservas getReserva() {
        return reserva;
    }

    public void setReserva(ControleDeReservas reserva) {
        this.reserva = reserva;
    }

    public Timestamp getDataCheckinReal() {
        return dataCheckinReal;
    }

    public void setDataCheckinReal(Timestamp dataCheckinReal) {
        this.dataCheckinReal = dataCheckinReal;
    }

    public Timestamp getDataCkeckoutReal() {
        return dataCkeckoutReal;
    }

    public void setDataCkeckoutReal(Timestamp dataCkeckoutReal) {
        this.dataCkeckoutReal = dataCkeckoutReal;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
