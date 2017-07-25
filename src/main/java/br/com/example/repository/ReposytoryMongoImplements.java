package br.com.example.repository;

import br.com.example.interfaces.InterfaceRepository;
import br.com.example.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReposytoryMongoImplements implements InterfaceRepository {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void save(Object object) {
        mongoOperations.save(object);
    }

    @Override
    public Object encontrarPorId(long id, Class aClass) {
        return mongoOperations.findById(id, aClass);
    }

    @Override
    public List<Usuario> encontrarPorNome(String name) {
        return mongoOperations.find(Query.query(Criteria.where("nome").is(name)), Usuario.class);
    }

    public void setMongoOps(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }
}
