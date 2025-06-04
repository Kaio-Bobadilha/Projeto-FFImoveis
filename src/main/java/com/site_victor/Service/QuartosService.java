package com.site_victor.Service;

import com.site_victor.Enums.StatusQuarto;
import com.site_victor.Model.Quartos;
import com.site_victor.Repository.QuartorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuartosService {
    private final QuartorRepositorio quartosRepositorio;

    @Autowired
    public QuartosService(QuartorRepositorio quartosRepositorio) {
        this.quartosRepositorio = quartosRepositorio;
    }

    public List<Quartos> ListarQuartos(){
        return quartosRepositorio.findAll();
    }

    public Quartos BuscarPorId(Long id){
        return quartosRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Quarto com " + id + "não encontrado"));
    }

    // Optional Avisa que o valor poder ser nulo ou esta aqui
    public Optional<Quartos> BuscarPorNumeroDoQuarto(int numeroDoQuarto){
        return quartosRepositorio.findByNumeroDoQuarto(numeroDoQuarto);
    }

    public List<Quartos> BuscarporStatus(StatusQuarto status){
        return quartosRepositorio.findByStatus(status);
    }

    @Transactional
    public void atualizarStatusQuarto(Long id, StatusQuarto novoStatus){
        Optional<com.site_victor.Model.Quartos> quartos = quartosRepositorio.findById(id);
        if(quartos.isPresent()){
            com.site_victor.Model.Quartos quarto = quartos.get();
            quarto.setStatus(novoStatus);
            quartosRepositorio.save(quarto);
        }else{
            System.out.println("Quarto Com ID " + id+ "não encontrado");
        }
    }

    @Transactional
    public Quartos AdicionarQuarto(Quartos quartos){
        if (quartos.getNumeroDoQuarto() <= 0) {
            throw new RuntimeException("Numero do quarto deve ser maior que zero");
        }
        if (BuscarPorNumeroDoQuarto(quartos.getNumeroDoQuarto()).isPresent()) {
            throw new RuntimeException("Quarto com numero " + quartos.getNumeroDoQuarto() + " já existe");
        }
        if (quartos.getStatus() == null){
            quartos.setStatus(StatusQuarto.Disponivel);
        }
        return quartosRepositorio.save(quartos);
    }

    @Transactional
    public void RemoverQuarto(Long id){
        Quartos quarto = BuscarPorId(id);
        if (quarto.getStatus() == StatusQuarto.Ocupado || quarto.getStatus() == StatusQuarto.Reservado){
            throw new RuntimeException("Não é possivel remover quarto com status " + quarto.getStatus());
        }
        quartosRepositorio.deleteById(id);
    }

}
