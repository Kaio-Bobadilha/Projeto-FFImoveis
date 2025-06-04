package com.site_victor.Repository;

import com.site_victor.Model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospedeRepositorio extends JpaRepository<Hospede , Long>{
    Hospede findByCpf(String cpf);
}
