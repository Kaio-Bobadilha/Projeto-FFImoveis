package com.site_victor.Repository;

import com.site_victor.Enums.StatusReserva;
import com.site_victor.Model.ControleDeReservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepositorio extends JpaRepository<ControleDeReservas, Long> {
    List<ControleDeReservas> findByStatusReserva(String statusReserva);
    List<ControleDeReservas> findByDataCheckIn_previstoBetween(LocalDate start, LocalDate end);
    List<ControleDeReservas> findByStatus(StatusReserva status);

}
