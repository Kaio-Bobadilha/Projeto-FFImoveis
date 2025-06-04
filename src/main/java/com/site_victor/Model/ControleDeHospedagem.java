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
    @JoinColumn(name="reserva_id")
    private ControleDeReservas reserva;

    @Column(name="data_checkin_real")
    private Timestamp dataCheckinReal;

    @Column(name="data_ckeckout_real")
    private Timestamp dataCkeckoutReal;

    @Column(name="observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
