package br.csi.farmasys.service;

import br.csi.farmasys.model.cliente.Cliente;
import br.csi.farmasys.model.cliente.ClienteRepository;
import br.csi.farmasys.model.remedio.Remedio;
import br.csi.farmasys.model.remedio.RemedioRepository;
import br.csi.farmasys.model.venda.DadosAtualizacaoVenda;
import br.csi.farmasys.model.venda.DadosCadastroVenda;
import br.csi.farmasys.model.venda.DadosItemVenda;
import br.csi.farmasys.model.venda.ItemVenda;
import br.csi.farmasys.model.venda.Venda;
import br.csi.farmasys.model.venda.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Venda getVendaUUID(UUID uuid) {
        Venda venda = this.repository.findByUuid(uuid);
        if (venda == null) {
            throw new EntityNotFoundException();
        }
        return venda;
    }

    @Transactional
    public Venda salvar(DadosCadastroVenda dados) {
        Venda venda = new Venda();
        venda.setCliente(buscarCliente(dados.clienteId()));
        venda.setFormaPagamento(dados.formaPagamento());

        BigDecimal total = BigDecimal.ZERO;
        for (DadosItemVenda dadosItem : dados.itens()) {
            Remedio remedio = this.remedioRepository.findById(dadosItem.remedioId())
                .orElseThrow(EntityNotFoundException::new);

            if (dadosItem.quantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade invalida para o remedio: " + remedio.getNome());
            }
            if (remedio.getQuantidadeEstoque() < dadosItem.quantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para o remedio: " + remedio.getNome());
            }
            remedio.setQuantidadeEstoque(remedio.getQuantidadeEstoque() - dadosItem.quantidade());

            ItemVenda item = new ItemVenda();
            item.setVenda(venda);
            item.setRemedio(remedio);
            item.setQuantidade(dadosItem.quantidade());
            item.setValorUnitario(remedio.getPreco());
            venda.getItens().add(item);

            total = total.add(remedio.getPreco().multiply(BigDecimal.valueOf(dadosItem.quantidade())));
        }

        venda.setValorTotal(total);
        venda.setDataHora(LocalDateTime.now());
        return this.repository.save(venda);
    }

    @Transactional
    public Venda atualizar(Long id, DadosAtualizacaoVenda dados) {
        return aplicarAlteracoes(getVenda(id), dados);
    }

    @Transactional
    public Venda atualizarUUID(UUID uuid, DadosAtualizacaoVenda dados) {
        return aplicarAlteracoes(getVendaUUID(uuid), dados);
    }

    @Transactional
    public void excluir(Long id) {
        excluirVenda(getVenda(id));
    }

    @Transactional
    public void excluirUUID(UUID uuid) {
        excluirVenda(getVendaUUID(uuid));
    }

    private Venda aplicarAlteracoes(Venda venda, DadosAtualizacaoVenda dados) {
        venda.setCliente(buscarCliente(dados.clienteId()));
        venda.setFormaPagamento(dados.formaPagamento());
        return this.repository.save(venda);
    }

    private void excluirVenda(Venda venda) {
        devolverEstoque(venda);
        this.repository.delete(venda);
    }

    private Cliente buscarCliente(Long clienteId) {
        if (clienteId == null) {
            return null;
        }
        return this.clienteRepository.findById(clienteId).orElseThrow(EntityNotFoundException::new);
    }

    private void devolverEstoque(Venda venda) {
        for (ItemVenda item : venda.getItens()) {
            Remedio remedio = item.getRemedio();
            remedio.setQuantidadeEstoque(remedio.getQuantidadeEstoque() + item.getQuantidade());
        }
    }
}
