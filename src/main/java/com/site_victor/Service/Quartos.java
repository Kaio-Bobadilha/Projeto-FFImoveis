package com.site_victor.Service;

import com.site_victor.Enums.StatusQuarto;
import com.site_victor.Repository.QuartorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Quartos {
    private final QuartorRepositorio quartosRepositorio;

    @Autowired
    public Quartos(QuartorRepositorio quartosRepositorio) {
        this.quartosRepositorio = quartosRepositorio;
    }

    public List<com.site_victor.Model.Quartos> ListarQuartos(){
        return quartosRepositorio.findAll();
    }

    public Quartos BuscarporStatus(StatusQuarto status){
        return quartosRepositorio.findByStatusQuarto(status);
    }

    public QuartorRepositorio getQuartosRepositorio() {
        return quartosRepositorio;
    }

    @Transactional
    public Quartos SalvarQuarto(Quartos quartos){return quartosRepositorio.save(quartos)}

    @Transactional
    public void deletarQuarto(Quartos quartos){quartosRepositorio.deleteById(quartos.getId());}
}
