package model;

import java.util.ArrayList;
import java.util.List;

public class Personagem {
    private int id;
    private String nome;
    private int hp;
    private int mana;
    private int ataqueBase;
    private List<String> inventario;

    public Personagem(String nome,
                      int hp,
                      int mana,
                      int ataqueBase) {
        this.nome = nome;
        this.hp = hp;
        this.mana = mana;
        this.ataqueBase = ataqueBase;
        this.inventario = new ArrayList<>();
    }

    public Personagem(int id,
                      String nome,
                      int hp,
                      int mana,
                      int ataqueBase,
                      List<String> inventario) {
        this.id = id;
        this.nome = nome;
        this.hp = hp;
        this.mana = mana;
        this.ataqueBase = ataqueBase;
        this.inventario = inventario != null ? inventario : new ArrayList<>();
    }

    public String receberDano(int dano) {
        this.hp -= dano;
        if (this.hp < 0) {
            this.hp = 0;
        }
        String retorno = (this.nome +
                " recebeu " + dano +
                " de dano! HP atual: " +
                this.hp);
        return retorno;
    }

    public void usarHabilidade(String habilidade,
                               int custoMana) {
        if (this.mana >= custoMana) {
            this.mana -= custoMana;
            System.out.println(this.nome + " usou " + habilidade + "! Mana restante: " + this.mana);
        } else {
            System.out.println(this.nome + " não tem mana suficiente para usar " + habilidade + "!");
        }
    }

    public void adicionarItem(String item) {
        this.inventario.add(item);
    }

    /**
     *
     * @param custo
     * @return Verdadeiro ou falso, caso tenha mana suficiente.
     */
    public boolean gastarMana(int custo) {
        if (this.mana >= custo) {
            this.mana -= custo;
            return true;
        }
        return false;
    }

    public boolean estaVivo() {
        return this.hp > 0;
    }

    // Getters e Setters necessários para o DAO e o Motor
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    public List<String> getInventario() {
        return inventario;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | HP: %d | Mana: %d | Atk: %d | Itens: %s",
                id, nome, hp, mana, ataqueBase, inventario);
    }
}