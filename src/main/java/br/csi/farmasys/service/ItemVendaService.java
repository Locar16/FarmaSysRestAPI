package br.csi.farmasys.service;

import br.csi.farmasys.model.venda.ItemVenda;
import br.csi.farmasys.model.venda.ItemVendaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemVendaService {

    private final ItemVendaRepository repository;

    public ItemVendaService(ItemVendaRepository repository) {
        this.repository = repository;
    }

    public List<ItemVenda> listar() {
        return this.repository.findAll();
    }

    public ItemVenda getItemVenda(Long id) {
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
