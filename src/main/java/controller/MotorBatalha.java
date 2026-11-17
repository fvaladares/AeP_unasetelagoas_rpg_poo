package controller;
import model.Personagem;

public class MotorBatalha {


    // Ação 1: Ataque físico simples
    public void realizarAtaque(Personagem atacante,
                               Personagem defensor) {
        if (!validarCombate(atacante, defensor)) return;

        int dano = atacante.getAtaqueBase();
        defensor.receberDano(dano);

        System.out.println("⚔\uFE0F " + atacante.getNome() + " atacou " + defensor.getNome() +
                " causando " + dano + " de dano!");
        exibirStatus(defensor);
    }

    // Ação 2: Golpe Especial (Consome mana e multiplica dano)
    public void invocarGolpeEspecial(Personagem atacante,
                                     Personagem defensor,
                                     String nomeGolpe,
                                     int custoMana) {
        if (!validarCombate(atacante, defensor)) return;

        if (atacante.gastarMana(custoMana)) {
            int danoEspecial = atacante.getAtaqueBase() * 2;
            defensor.receberDano(danoEspecial);

            System.out.println("  \uD83D\uDD25  " + atacante.getNome() + " invocou [" + nomeGolpe + "] em " +
                    defensor.getNome() + " causando " + danoEspecial + " de dano!");
            exibirStatus(defensor);
        } else {
            System.out.println("❌ " + atacante.getNome() + " tentou usar [" + nomeGolpe +
                    "], mas está sem mana! (Mana atual: " + atacante.getMana() + ")");
        }
    }

    // Métodos auxiliares privados (Encapsulamento interno do motor!)
    private boolean validarCombate(Personagem atacante, Personagem defensor) {
        if (!atacante.estaVivo()) {
            System.out.println("⚠️ " + atacante.getNome() + " já está derrotado e não pode agir.");
            return false;
        }
        if (!defensor.estaVivo()) {
            System.out.println("⚠\uFE0F " + defensor.getNome() + " já foi derrotado!");
            return false;
        }
        return true;
    }

    private void exibirStatus(Personagem p) {
        if (p.estaVivo()) {
            System.out.println(" \uD83D\uDD0B  -> Status de " + p.getNome() + ": " + p.getHp() + " HP | " + p.getMana() + " Mana\n");
        } else {
            System.out.println(" ☠\uFE0F" + p.getNome() + " foi derrotado!\n");
        }
    }

}
