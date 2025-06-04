package com.site_victor.Repository;

import com.site_victor.Model.ControleDeHospedagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospedagemRepositorio extends JpaRepository<ControleDeHospedagem, Long> {
    ControleDeHospedagem findByReserva_id(Long id);
}
