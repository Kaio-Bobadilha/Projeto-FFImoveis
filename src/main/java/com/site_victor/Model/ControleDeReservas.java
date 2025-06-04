package com.site_victor.Model;


import com.site_victor.Enums.StatusReserva;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="controledereservas")
public class ControleDeReservas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "hospede_id") //FK
    private Hospede hospedeId;

    @ManyToOne
    @JoinColumn(name = "quartos_id") //FK
    private Quartos quartosId;

    @Column(name="data_check_in_previsto")
    private LocalDate dataCheckIn_previsto;

    @Column(name="data_check_out_previsto")
    private LocalDate dataCheckOut_previsto;

    @Column(name="data_reserva")
    private LocalDateTime dataReserva = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusReserva StatusReserva;

    @Column(name="Valor_Total")
    private double ValorTotal;

    public ControleDeReservas() {}

    public ControleDeReservas(LocalDate dataCheckIn, LocalDate dataCheckOut, LocalDateTime dataReserva, StatusReserva StatusReserva, double ValorTotal) {
        this.dataCheckIn_previsto = dataCheckIn;
        this.dataCheckOut_previsto = dataCheckOut;
        this.dataReserva = dataReserva;
        this.StatusReserva = StatusReserva;
        this.ValorTotal = ValorTotal;
    }

    // get e set

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public Hospede getHospedeId() {return hospedeId;}
    public void setHospedeId(Hospede hospedeId) {this.hospedeId = hospedeId;}

    public Quartos getQuartosId() {return quartosId;}
    public void setQuartosId(Quartos quartosId) {this.quartosId = quartosId;}

    public LocalDate getDataCheckIn_previsto() {return dataCheckIn_previsto;}
    public void setDataCheckIn_previsto(LocalDate dataCheckIn_previsto) {this.dataCheckIn_previsto = dataCheckIn_previsto;}

    public LocalDate getDataCheckOut_previsto() {return dataCheckOut_previsto;}
    public void setDataCheckOut_previsto(LocalDate dataCheckOut_previsto) {this.dataCheckOut_previsto = dataCheckOut_previsto;}

    public LocalDateTime getDataReserva() {return dataReserva;}
    public void setDataReserva(LocalDateTime dataReserva) {this.dataReserva = dataReserva;}

    public double getValorTotal() {return ValorTotal;}
    public void setValorTotal(double valorTotal) {ValorTotal = valorTotal;}
}
