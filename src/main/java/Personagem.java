public class Personagem {
    private String nome;
    private int hp;
    private int mana;
    private int nivel;

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
}
