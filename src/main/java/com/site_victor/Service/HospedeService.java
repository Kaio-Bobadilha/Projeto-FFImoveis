package com.site_victor.Service;


import com.site_victor.Model.Hospede;
import com.site_victor.Repository.HospedeRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HospedeService {
    private final HospedeRepositorio hospedeRepositorio;

    @Autowired
    public HospedeService(HospedeRepositorio hospedeRepositorio) {
        this.hospedeRepositorio = hospedeRepositorio;
    }

    public List<Hospede> Listar_Hospedes(){
        return hospedeRepositorio.findAll();
    }

    // Se quiser botar optional
    public Hospede BuscarPorCpf(String cpf){
        return hospedeRepositorio.findByCpf(cpf);
    }

    // Tirei o get para colocar uma mensagem de não encontrado
    public Hospede BuscarPorId(Long id){
        return hospedeRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException
                        ("Hospede com id " + id + " não encontrado"));
    }

    @Transactional
    public Hospede salvarHospede(Hospede hospede){return hospedeRepositorio.save(hospede);}

    @Transactional
    public void deletarHospede(Hospede hospede){hospedeRepositorio.deleteById(hospede.getId());}
}
