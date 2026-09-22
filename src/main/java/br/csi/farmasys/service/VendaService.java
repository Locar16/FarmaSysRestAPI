package br.csi.farmasys.service;

import br.csi.farmasys.model.cliente.ClienteRepository;
import br.csi.farmasys.model.remedio.Remedio;
import br.csi.farmasys.model.remedio.RemedioRepository;
import br.csi.farmasys.model.venda.ItemVenda;
import br.csi.farmasys.model.venda.Venda;
import br.csi.farmasys.model.venda.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VendaService {

    private final VendaRepository repository;
    private final ClienteRepository clienteRepository;
    private final RemedioRepository remedioRepository;

    public VendaService(VendaRepository repository,
                        ClienteRepository clienteRepository,
                        RemedioRepository remedioRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.remedioRepository = remedioRepository;
    }

    public List<Venda> listar() {
        return this.repository.findAll();
    }

    public Venda getVenda(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    public Venda getVendaUUID(String uuid) {
        return this.repository.findByUuid(UUID.fromString(uuid));
    }

    @Transactional
    public void salvar(Venda venda) {
        if (venda.getItens() == null || venda.getItens().isEmpty()) {
            throw new IllegalArgumentException("A venda deve ter pelo menos um item.");
        }
        venda.setId(null);
        vincularCliente(venda);

        BigDecimal total = BigDecimal.ZERO;
        for (ItemVenda item : venda.getItens()) {
            Remedio remedio = this.remedioRepository.findById(item.getRemedio().getId()).orElseThrow();

            if (item.getQuantidade() == null || item.getQuantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade inválida para o remédio: " + remedio.getNome());
            }
            if (remedio.getQuantidadeEstoque() < item.getQuantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para o remédio: " + remedio.getNome());
            }

            remedio.setQuantidadeEstoque(remedio.getQuantidadeEstoque() - item.getQuantidade());
            item.setRemedio(remedio);
            item.setValorUnitario(remedio.getPreco());
            item.setVenda(venda);
            total = total.add(remedio.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        venda.setValorTotal(total);
        venda.setDataHora(LocalDateTime.now());
        this.repository.save(venda);
    }

    @Transactional
    public void atualizar(Venda venda) {
        Venda existente = this.repository.findById(venda.getId()).orElseThrow();
        aplicarAlteracoes(existente, venda);
    }

    @Transactional
    public void atualizarUUID(Venda venda) {
        Venda existente = this.repository.findByUuid(venda.getUuid());
        if (existente != null) {
            aplicarAlteracoes(existente, venda);
        }
    }

    @Transactional
    public void excluir(Long id) {
        Venda venda = this.repository.findById(id).orElseThrow();
        devolverEstoque(venda);
        this.repository.delete(venda);
    }

    @Transactional
    public void deletarUUID(String uuid) {
        Venda venda = this.repository.findByUuid(UUID.fromString(uuid));
        if (venda != null) {
            devolverEstoque(venda);
            this.repository.delete(venda);
        }
    }

    private void aplicarAlteracoes(Venda existente, Venda dados) {
        vincularCliente(dados);
        existente.setCliente(dados.getCliente());
        existente.setFormaPagamento(dados.getFormaPagamento());
    }

    private void vincularCliente(Venda venda) {
        if (venda.getCliente() != null && venda.getCliente().getId() != null) {
            venda.setCliente(this.clienteRepository.findById(venda.getCliente().getId()).orElseThrow());
        } else {
            venda.setCliente(null);
        }
    }

    private void devolverEstoque(Venda venda) {
        for (ItemVenda item : venda.getItens()) {
            Remedio remedio = item.getRemedio();
            remedio.setQuantidadeEstoque(remedio.getQuantidadeEstoque() + item.getQuantidade());
        }
    }
}
