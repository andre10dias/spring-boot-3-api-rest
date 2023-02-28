package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    /**
     * @PageableDefault
     * Como o parâmetro do tipo "Pageable" é opcional, caso não seja passado nennum
     * parãmetro na url, essa notação sobrescreve a paginação padrão do spring boot,
     * que é size=20, page=0 e ordenacao (sort) da forma que vier do banco de dados.
     * */
    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size=10, sort={"nome", "especialidade"}) Pageable paginacao) {
        //return repository.findAll().stream().map(DadosListagemMedico::new).toList(); sem paginacao
        //return repository.findAll(paginacao).map(DadosListagemMedico::new);
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //pegando apenas os registros ativos
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        //repository.deleteById(id);
        /**Alterando para exclusão lógica*/
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }

}
