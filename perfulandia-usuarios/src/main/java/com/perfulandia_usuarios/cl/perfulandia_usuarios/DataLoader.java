package com.perfulandia_usuarios.cl.perfulandia_usuarios;

import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.CategoriaUsuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.Usuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.repository.CategoriaUsuarioRepository;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Profile("cargadev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CategoriaUsuarioRepository categoriaUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Cargar categorías de usuario
        String[] categorias = {"Gold", "Premium", "Elite", "Standard"};
        for (String cat : categorias) {
            if (categoriaUsuarioRepository.findByCategoria(cat) == null) {
                CategoriaUsuario categoriaUsuario = new CategoriaUsuario();
                categoriaUsuario.setCategoria(cat);
                categoriaUsuarioRepository.save(categoriaUsuario);
            }
        }

        List<CategoriaUsuario> categoriasGuardadas = categoriaUsuarioRepository.findAll();

        // Rango fechas para fechaNacimiento (entre 65 y 18 años atrás)
        LocalDate minDate = LocalDate.now().minusYears(65);
        LocalDate maxDate = LocalDate.now().minusYears(18);
        long minDay = minDate.toEpochDay();
        long maxDay = maxDate.toEpochDay();

        for (int i = 0; i < 20; i++) {
            Usuario usuario = new Usuario();

            // username: max 20 chars (17 base + 3 num)
            String baseUsername = faker.internet().username().toLowerCase();
            if (baseUsername.length() > 17) {
                baseUsername = baseUsername.substring(0, 17);
            }
            String username = baseUsername + faker.number().numberBetween(10, 999);
            usuario.setUsername(username);

            // nombres y apellidos max 20 chars
            String nombres = faker.name().firstName();
            if (nombres.length() > 20) {
                nombres = nombres.substring(0, 20);
            }
            usuario.setNombres(nombres);

            String apellidos = faker.name().lastName();
            if (apellidos.length() > 20) {
                apellidos = apellidos.substring(0, 20);
            }
            usuario.setApellidos(apellidos);

            // rut max 10 chars - formato simple con guion
            // Ejemplo: 12345678-K
            String rutNumber = String.valueOf(faker.number().numberBetween(10000000, 99999999));
            String rut = rutNumber + "-" + "K"; // 'K' fijo o podría ser randomizado si quieres
            if (rut.length() > 10) {
                rut = rut.substring(0, 10);
            }
            usuario.setRut(rut);

            // fechaNacimiento entre 18 y 65 años atrás, usando Java puro
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
            Date fechaNacimiento = Date.from(randomBirthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            usuario.setFechaNacimiento(fechaNacimiento);

            // asignar categoría aleatoria
            usuario.setCategoriaUsuario(categoriasGuardadas.get(random.nextInt(categoriasGuardadas.size())));

            usuarioRepository.save(usuario);
        }
    }
}
