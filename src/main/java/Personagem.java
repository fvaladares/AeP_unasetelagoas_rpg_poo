public class Personagem {
    private String nome;
    private int hp;
    private int mana;
    private int nivel;

    public Personagem(String nome,
                      int hp,
                      int mana,
                      int nivel) {
        this.nome = nome;
        this.hp = hp;
        this.mana = mana;
        this.nivel = nivel;

    }
    public Personagem() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(
            String nome) {
        this.nome = nome;
    }

    public String receberDano(int dano){
        this.hp -= dano;
        if(this.hp < 0){
            this.hp = 0;
        }
        String retorno = (this.nome +
                " recebeu " + dano +
                " de dano! HP atual: "+
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

    @Override
    public String toString() {
        return "Personagem{" +
                "nome='" + nome + '\'' +
                ", hp=" + hp +
                ", mana=" + mana +
                ", nivel=" + nivel +
                '}';
    }
}
