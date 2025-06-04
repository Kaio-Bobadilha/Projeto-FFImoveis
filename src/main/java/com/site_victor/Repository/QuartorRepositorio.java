package com.site_victor.Repository;

import com.site_victor.Enums.StatusQuarto;
import com.site_victor.Model.Quartos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartorRepositorio extends JpaRepository<Quartos, Long> {
    com.site_victor.Service.Quartos findByStatusQuarto(StatusQuarto statusQuarto);
    Quartos findByNumeroDoQuarto(int numeroDoQuarto);
}
