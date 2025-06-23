package com.perfulandia_auth.cl.perfulandia_auth;

import com.perfulandia_auth.cl.perfulandia_auth.model.AuthUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.model.RolUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.repository.AuthUsuarioRepository;
import com.perfulandia_auth.cl.perfulandia_auth.repository.RolUsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Profile("cargadev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    @Autowired
    private AuthUsuarioRepository authUsuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Cargar roles
        String[] roles = {"ADMIN", "CLIENTE", "VENDEDOR"};
        for (String nombreRol : roles) {
            RolUsuario rolUsuario = new RolUsuario();
            rolUsuario.setRol(nombreRol);
            rolUsuarioRepository.save(rolUsuario);
        }

        List<RolUsuario> rolUsuarios = rolUsuarioRepository.findAll();

        // Cargar usuarios
        for (int i = 0; i < 20; i++) {
            AuthUsuario usuario = new AuthUsuario();
            String username = faker.name().firstName().toLowerCase() + faker.number().numberBetween(10, 999);
            usuario.setUsername(username);
            usuario.setPassword(faker.regexify("[A-Za-z0-9]{10,16}"));
            usuario.setRolUsuario(rolUsuarios.get(random.nextInt(rolUsuarios.size())));
            authUsuarioRepository.save(usuario);
        }
    }
}
