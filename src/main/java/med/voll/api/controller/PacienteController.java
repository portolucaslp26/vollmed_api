package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import med.voll.api.domain.usuario.DadosCadastroUsuario;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid DadosCadastrosPacientes dados, UriComponentsBuilder uriBuilder) {
        // Criar uma instância do codificador BCrypt com um fator de força de 10
        var passwordEncoder = new BCryptPasswordEncoder(10);

        // Codificar a senha fornecida usando o codificador BCrypt
        var senhaCriptografada = passwordEncoder.encode(dados.usuario().senha());

        // Criar um novo paciente com base nos dados fornecidos e no usuário criado anteriormente
        var paciente = new Paciente(dados);
        paciente.getUsuario().setSenha(senhaCriptografada);
        System.out.println(paciente);
        repository.save(paciente);

        // Criar a URI de resposta e retornar uma resposta HTTP 201 CREATED com os detalhes do paciente criado
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPacientes(paciente));
    }


    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<DadosListagemPacientes>> list(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        // faz o find all mapeando o DTO DadosListageMedico fazendo a paginação
        // (?size=10&sort=nome) na URL para uma página de 10 items ordenada pelo nome ou definir no método
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPacientes::new);
        return ResponseEntity.ok(page);
    }
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity findById(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPacientes(paciente));
    }

    @PutMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity update(@RequestBody @Valid DadosAtualizarPacientes dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInfo(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPacientes(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity delete(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.inativar();
        return ResponseEntity.noContent().build();
    }
}
