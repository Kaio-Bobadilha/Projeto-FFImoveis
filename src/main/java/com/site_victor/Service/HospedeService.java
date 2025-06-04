package com.site_victor.Service;


import com.site_victor.Model.Hospede;
import com.site_victor.Repository.HospedeRepositorio;
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
    } // mudar o nom para listar Hospedes

    public Hospede BuscarPorCpf(String cpf){
        return hospedeRepositorio.findByCpf(cpf);
    }

    public Hospede BuscarPorId(Long id){
        return hospedeRepositorio.findById(id).get();
    }

    @Transactional
    public Hospede salvarHospede(Hospede hospede){return hospedeRepositorio.save(hospede);}

    @Transactional
    public void deletarHospede(Hospede hospede){hospedeRepositorio.deleteById(hospede.getId());}
}
