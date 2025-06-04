package com.site_victor.Service;


import com.site_victor.Enums.StatusQuarto;
import com.site_victor.Enums.StatusReserva;
import com.site_victor.Model.ControleDeHospedagem;
import com.site_victor.Model.ControleDeReservas;
import com.site_victor.Repository.HospedagemRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class HospedagemService {

    private final HospedagemRepositorio hospedagemRepositorio;
    private final ReservaService reservaService; // Para buscar/validar reservas
    private final QuartosService quartosService; // Para atualizar status do quarto

    @Autowired
    public HospedagemService(HospedagemRepositorio hospedagemRepositorio, ReservaService reservaService, QuartosService quartosService) {
        this.hospedagemRepositorio = hospedagemRepositorio;
        this.reservaService = reservaService;
        this.quartosService = quartosService;
    }

    public List<ControleDeHospedagem> listarTodasHospedagens() {
        return hospedagemRepositorio.findAll();
    }

    public Optional<ControleDeHospedagem> buscarHospedagemPorId(Long id) {
        return hospedagemRepositorio.findById(id);
    }


    public Optional<ControleDeHospedagem> buscarHospedagemPorReservaId(Long reservaId) {
        return Optional.ofNullable(hospedagemRepositorio.findByReserva_id(reservaId));
    }

    @Transactional
    public ControleDeHospedagem realizarCheckIn(Long reservaId) {
        ControleDeReservas reserva = reservaService.buscarReservaPorId(reservaId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva com ID " + reservaId + " não encontrada para check-in."));
        if (reserva.getStatusReserva() != StatusReserva.Confirmado) {
            throw new IllegalStateException("Check-in só pode ser realizado para reservas confirmadas. Status atual: " + reserva.getStatusReserva());
        }
        if (buscarHospedagemPorReservaId(reservaId).isPresent()) {
            throw new IllegalStateException("Check-in já realizado para esta reserva.");
        }
        if (reserva.getQuartosId() == null) {
             throw new IllegalStateException("Reserva não possui quarto associado.");
        }
        Long quartoId = reserva.getQuartosId().getId();
        // Recarregar o quarto para garantir status atualizado
        com.site_victor.Model.Quartos quarto = quartosService.BuscarPorId(quartoId);
        if (quarto.getStatus() != StatusQuarto.Reservado && quarto.getStatus() != StatusQuarto.Disponivel) {
             throw new IllegalStateException("Quarto não está em status válido para check-in (Reservado ou Disponível). Status atual: " + quarto.getStatus());
        }

        ControleDeHospedagem hospedagem = new ControleDeHospedagem();
        hospedagem.setReserva(reserva);
        hospedagem.setDataCheckinReal(Timestamp.from(Instant.now())); // Define a data/hora atual do check-in

        ControleDeHospedagem hospedagemSalva = hospedagemRepositorio.save(hospedagem);

        quartosService.atualizarStatusQuarto(quartoId, StatusQuarto.Ocupado);

        return hospedagemSalva;
    }

    @Transactional
    public ControleDeHospedagem realizarCheckOut(Long hospedagemId, String observacoes) {
        ControleDeHospedagem hospedagem = buscarHospedagemPorId(hospedagemId)
                .orElseThrow(() -> new EntityNotFoundException("Hospedagem com ID " + hospedagemId + " não encontrada para check-out."));

        if (hospedagem.getDataCkeckoutReal() != null) {
            throw new IllegalStateException("Check-out já realizado para esta hospedagem.");
        }
        if (hospedagem.getDataCheckinReal() == null) {
            throw new IllegalStateException("Check-in não foi realizado para esta hospedagem.");
        }

        // Definir data/hora do check-out e observações
        hospedagem.setDataCkeckoutReal(Timestamp.from(Instant.now()));
        if (observacoes != null && !observacoes.trim().isEmpty()) {
            hospedagem.setObservacoes(observacoes);
        }

        ControleDeHospedagem hospedagemAtualizada = hospedagemRepositorio.save(hospedagem);

        // Atualizar status do quarto para Disponível (ou Manutenção)
        if (hospedagem.getReserva() != null && hospedagem.getReserva().getQuartosId() != null) {
            Long quartoId = hospedagem.getReserva().getQuartosId().getId();
            // Aqui pode ter lógica para decidir se vai para Disponível ou Manutenção
            quartosService.atualizarStatusQuarto(quartoId, StatusQuarto.Disponivel);
        } else {
            // Logar um aviso ou tratar erro, pois a hospedagem deveria ter um quarto associado via reserva
            System.err.println("Aviso: Hospedagem ID " + hospedagemId + " não possui quarto associado via reserva para atualizar status no check-out.");
        }

        return hospedagemAtualizada;
    }

    @Transactional
    public ControleDeHospedagem adicionarObservacao(Long hospedagemId, String observacoes) {
         ControleDeHospedagem hospedagem = buscarHospedagemPorId(hospedagemId)
                .orElseThrow(() -> new EntityNotFoundException("Hospedagem com ID " + hospedagemId + " não encontrada."));

        hospedagem.setObservacoes(observacoes);
        return hospedagemRepositorio.save(hospedagem);
    }

}

