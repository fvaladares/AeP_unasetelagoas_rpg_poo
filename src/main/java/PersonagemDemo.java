public class PersonagemDemo {
    public static void main(String[] args) {
        Personagem p1;
        Personagem p2 =
                new Personagem();


         p1 =
        new Personagem("Geralt",
                100,
                100,
                10);
        p1.setNome("Geralt");
        System.out.println(p1.getNome());

        System.out.println(p1);
        System.out.println(p2);
//        p1.nome = "Geralt";
//        p1.hp = 100;
//        p1.mana = 100;
//        p1.nivel = 10;
//        System.out.println(p1);
//
//
//        p2.nome = "Yennefer";
//        p2.hp = 100;
//        p2.mana = 100;
//        p2.nivel = 15;
//        System.out.println(p1);
    }
}
