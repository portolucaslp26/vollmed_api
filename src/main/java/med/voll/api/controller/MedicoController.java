package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import med.voll.api.domain.paciente.DadosCadastrosPacientes;
import med.voll.api.domain.paciente.DadosDetalhamentoPacientes;
import med.voll.api.domain.paciente.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import med.voll.api.domain.medico.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid DadosCadastrosMedicos dados, UriComponentsBuilder uriBuilder) {
        // Criar uma instância do codificador BCrypt com um fator de força de 10
        var passwordEncoder = new BCryptPasswordEncoder(10);

        // Codificar a senha fornecida usando o codificador BCrypt
        var senhaCriptografada = passwordEncoder.encode(dados.usuario().senha());

        // Criar um novo paciente com base nos dados fornecidos e no usuário criado anteriormente
        var medico = new Medico(dados);
        medico.getUsuario().setSenha(senhaCriptografada);
        System.out.println(medico);
        repository.save(medico);

        // Criar a URI de resposta e retornar uma resposta HTTP 201 CREATED com os detalhes do medico criado
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<DadosListagemMedico>> list(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        // faz o find all mapeando o DTO DadosListageMedico fazendo a paginação
        // (?size=10&sort=nome) na URL para uma página de 10 items ordenada pelo nome ou definir no método
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @PutMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity update(@RequestBody @Valid DadosAtualizarMedicos dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInfo(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity delete(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.inativar();
        return ResponseEntity.noContent().build();
    }

}
