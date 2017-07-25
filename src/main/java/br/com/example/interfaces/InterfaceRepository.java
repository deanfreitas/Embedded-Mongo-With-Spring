package br.com.example.interfaces;

import br.com.example.model.Usuario;

import java.util.List;

public interface InterfaceRepository {

    public void save(Object object);
    public Object encontrarPorId(long id, Class aClass);
    public List<Usuario> encontrarPorNome(String nome);

}
