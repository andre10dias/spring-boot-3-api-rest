package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    /**
     * De acordo com o protocolo HTTP, ao cadastrarmos uma informação ou recurso
     * numa API, o código HTTP a ser devolvido é o 201 (created).
     *
     * O código 201 devolve no corpo da resposta os dados do novo recurso/registro
     * criado e um cabeçalho do protocolo HTTP chamado location.
     *
     * Assim, na resposta devemos colocar o código 201 e, no corpo da rsposta, os
     * dados do novo registro criado, além do cabeçalho location.
     * */
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        repository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    /**
     * @PageableDefault
     * Como o parâmetro do tipo "Pageable" é opcional, caso não seja passado nennum
     * parãmetro na url, essa notação sobrescreve a paginação padrão do spring boot,
     * que é size=20, page=0 e ordenacao (sort) da forma que vier do banco de dados.
     * */
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size=10, sort={"nome", "especialidade"}) Pageable paginacao) {
        //return repository.findAll().stream().map(DadosListagemMedico::new).toList(); sem paginacao
        //return repository.findAll(paginacao).map(DadosListagemMedico::new);
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //pegando apenas os registros ativos
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        //repository.deleteById(id);
        /**Alterando para exclusão lógica*/
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
