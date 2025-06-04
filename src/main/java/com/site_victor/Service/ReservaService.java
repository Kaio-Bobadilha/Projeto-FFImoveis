package com.site_victor.Service;

import com.site_victor.Enums.StatusQuarto;
import com.site_victor.Enums.StatusReserva;
import com.site_victor.Model.ControleDeReservas;
import com.site_victor.Model.Hospede;
import com.site_victor.Model.Quartos;
import com.site_victor.Repository.ReservaRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {
    private final ReservaRepositorio reservaRepositorio;
    private final HospedeService hospedeService;
    private final QuartosService quartosService;

    @Autowired
    public ReservaService(ReservaRepositorio reservaRepositorio, HospedeService hospedeService, QuartosService quartosService) {
        this.reservaRepositorio = reservaRepositorio;
        this.hospedeService = hospedeService;
        this.quartosService = quartosService;
    }

    public List<ControleDeReservas> listarTodasReservas(){return reservaRepositorio.findAll();}

    public Optional<ControleDeReservas> buscarPorId(Long id){return reservaRepositorio.findById(id);}

    public List<ControleDeReservas> buscarPorStatus(StatusReserva status){
        return reservaRepositorio.findByStatusReserva(status.toString());
    }

    public List<ControleDeReservas> buscarPorDataCheckIn_previsto(LocalDate inicio, LocalDate fim){
        return reservaRepositorio.findByDataCheckIn_previstoBetween(inicio, fim);
    }

    @Transactional
    public ControleDeReservas criarReserva(Long idHospede, Long idQuarto, LocalDate dataCheckIn, LocalDate dataCheckOut){
        if (dataCheckIn == null || dataCheckOut == null || dataCheckOut.isBefore(dataCheckIn) || dataCheckIn.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Data inválida");
        }
        Hospede hospede = hospedeService.BuscarPorId(idHospede);
        if (hospede == null){
            throw new IllegalArgumentException("Hospede com id " + idHospede + " não encontrado");
        }

        Quartos quarto = quartosService.BuscarPorId(idQuarto);
        if (quarto.getStatus() != StatusQuarto.Disponivel) {
            throw new IllegalStateException("Quarto não está disponível para reserva.");
        }

        // Calcular valor total
        long numeroDiarias = ChronoUnit.DAYS.between(dataCheckIn, dataCheckOut);
        if (numeroDiarias == 0) numeroDiarias = 1; // Mínimo de 1 diária
        double valorTotal = quarto.getPrecoDiaria() * numeroDiarias;

        // Criar a reserva
        ControleDeReservas novaReserva = new ControleDeReservas();
        novaReserva.setHospedeId(hospede);
        novaReserva.setQuartosId(quarto);
        novaReserva.setDataCheckIn_previsto(dataCheckIn);
        novaReserva.setDataCheckOut_previsto(dataCheckOut);
        novaReserva.setStatusReserva(StatusReserva.Confirmado); // Ou Pendente, dependendo da regra de negócio
        novaReserva.setValorTotal(valorTotal);

        // Salvar a reserva
        ControleDeReservas reservaSalva = reservaRepositorio.save(novaReserva);
        // Atualizar o status do quarto
        quartosService.atualizarStatusQuarto(idQuarto, StatusQuarto.Reservado);

        return reservaSalva;
    }

    public Optional<ControleDeReservas> buscarReservaPorId(Long id) {
        return reservaRepositorio.findById(id);
    }



    @Transactional
    public ControleDeReservas cancelarReservas(Long reservaId){
        ControleDeReservas reserva = buscarReservaPorId(reservaId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva com ID " + reservaId + " não encontrada."));


        if (StatusReserva.Cancelada == reserva.getStatusReserva()) {
            throw new IllegalStateException("Reserva já está cancelada.");
        }

        reserva.setStatusReserva(StatusReserva.Cancelada);
        ControleDeReservas reservaCancelada = reservaRepositorio.save(reserva);
        if (reserva.getQuartosId() != null) {
            quartosService.atualizarStatusQuarto(reserva.getQuartosId().getId(), StatusQuarto.Disponivel);
        }

        return reservaCancelada;
    }
}
