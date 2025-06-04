package com.site_victor.Repository;

import com.site_victor.Enums.StatusQuarto;
import com.site_victor.Model.Quartos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuartorRepositorio extends JpaRepository<Quartos, Long> {
    List<Quartos> findByStatus(StatusQuarto status);
    Optional<Quartos> findByNumeroDoQuarto(int numeroDoQuarto);
}
