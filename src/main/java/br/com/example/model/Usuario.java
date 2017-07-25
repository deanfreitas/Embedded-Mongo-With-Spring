package br.com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "pessoa")
public class Usuario {

    @Id
    private long id;
    private String nome;
    private int idade;

    @DBRef(db = "endereco")
    private List<Endereco> enderecos = new ArrayList<Endereco>();

    /**
     *
     */
    public Usuario() {
    }

    /**
     *
     * @param id
     * @param nome
     * @param idade
     */
    @PersistenceConstructor
    public Usuario(Long id, String nome, int idade) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
    }

    /**
     *
     * @param nome
     * @param idade
     */
    public Usuario(String nome, int idade) {
        this(null, nome, idade);
    }

    public long getId() {
        return id;
    }

    public Usuario setId(long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Usuario setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public int getIdade() {
        return idade;
    }

    public Usuario setIdade(int idade) {
        this.idade = idade;
        return this;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public Usuario setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
        return this;
    }
}
