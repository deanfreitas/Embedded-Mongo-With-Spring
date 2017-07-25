package br.com.exemplo.testemongo;

import br.com.example.model.Endereco;
import br.com.example.model.Usuario;
import br.com.example.repository.ReposytoryMongoImplements;
import com.mongodb.Mongo;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.RuntimeConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import org.junit.*;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TesteMongo {

    private static final String LOCALHOST = "127.0.0.1";
    private static final String DB_NAME = "teste";
    private static final int MONGO_TEST_PORT = 27017;

    private ReposytoryMongoImplements reposytoryMongoImplements;
    private MongoTemplate mongoTemplate;

    private static MongodProcess mongoProcess;
    private static Mongo mongo;

    @BeforeClass
    public static void iniciarMongoDb() throws IOException {
        RuntimeConfig runtimeConfig = new RuntimeConfig();
        runtimeConfig.setExecutableNaming(new UserTempNaming());
        MongodStarter mongodStarter = MongodStarter.getInstance(runtimeConfig);

        MongodExecutable mongodExecutable = mongodStarter.prepare(new MongodConfig(Version.V2_2_0, MONGO_TEST_PORT, false));
        mongoProcess = mongodExecutable.start();

        mongo = new Mongo(LOCALHOST, MONGO_TEST_PORT);
        mongo.getDB(DB_NAME);
    }

    @AfterClass
    public static void shutdownDB() throws InterruptedException {
        mongo.close();
        mongoProcess.stop();
    }

    @Before
    public void setUp() throws Exception {
        reposytoryMongoImplements = new ReposytoryMongoImplements();
        mongoTemplate = new MongoTemplate(mongo, DB_NAME);
        reposytoryMongoImplements.setMongoOps(mongoTemplate);
    }

    @After
    public void tearDown() throws Exception {
        mongoTemplate.dropCollection(Usuario.class);
    }

    public List<Usuario> criarPessoas(int quantidadeUsuarios, int quantidadeEnderecos) {
        List<Usuario> usuarios = new ArrayList<Usuario>();

        for(int i = 1; i <= quantidadeUsuarios; i++) {
            Usuario usuario = new Usuario();
            List<Endereco> enderecos = new ArrayList<Endereco>();

            for(int j = 1; j <= quantidadeEnderecos; j++) {
                Endereco endereco = new Endereco();
                endereco.setId(j).setRua("R. Teste" + j).setNumero(j).setBairro("B. Teste" + j).setCidade("C. Teste" + j).setEstado("E. Teste" + j);
                enderecos.add(endereco);
            }

            usuario.setId(i).setNome("teste" + i).setIdade(20 + i).setEnderecos(enderecos);
            usuarios.add(usuario);
        }

        return usuarios;
    }

    public void salvarUsuarios(int quantidadeUsuarios, int quantidadeEnderecos) {
        List<Usuario> listaUsuarios = criarPessoas(quantidadeUsuarios, quantidadeEnderecos);

        for (Usuario usuario : listaUsuarios) {
            for(Endereco endereco : usuario.getEnderecos()) {
                reposytoryMongoImplements.save(endereco);
            }
            reposytoryMongoImplements.save(usuario);
        }
    }

    @Test
    public void testSaveInMongo() {
        int quantidadeUsuarios = 1;
        int quantidadeEnderecos = 1;

        salvarUsuarios(quantidadeUsuarios, quantidadeEnderecos);

        int quantidadeUsuariosSalvos = mongoTemplate.findAll(Usuario.class).size();

        Assert.assertEquals("número de quantidade de usuários salvos no mongo --> " + quantidadeUsuariosSalvos, quantidadeUsuariosSalvos, quantidadeUsuarios);
    }

    @Test
    public void testEncontrarPorId() {
        int quantidadeUsuarios = 5;
        int quantidadeEnderecos = 5;
        String nomeUsuario = "teste3";

        salvarUsuarios(quantidadeUsuarios, quantidadeEnderecos);

        List<Usuario> listaUsuariosSalvos = reposytoryMongoImplements.encontrarPorNome(nomeUsuario);

        Assert.assertEquals("A quantidade de usuarios salvos com o nome de " + nomeUsuario + " = " + listaUsuariosSalvos.size(),
                listaUsuariosSalvos != null && listaUsuariosSalvos.size() > 0, true);

    }

    @Test
    public void testeEncontrarPorId() {
        int quantidadeUsuarios = 5;
        int quantidadeEnderecos = 5;
        int idEncontar = 1;

        salvarUsuarios(quantidadeUsuarios, quantidadeEnderecos);

        Usuario UsuarioSalvo = (Usuario) reposytoryMongoImplements.encontrarPorId(idEncontar, Usuario.class);

        Assert.assertNotNull(UsuarioSalvo);
    }
}
