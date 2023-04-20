package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
// Serviço de autenticação obrigatoriamente deve implementar o UserDatailsService
public class AuthService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;


    @Override
    // método default da implementação do UserDetailsService
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // findByLogin foi criado no UsuarioRepository
        return repository.findByLogin(username);
    }
}
