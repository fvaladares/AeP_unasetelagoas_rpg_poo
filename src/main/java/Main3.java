import controller.PersonagemDAO;
import model.Personagem;

public class Main3 {
    public static void main(String[] args) {
        PersonagemDAO dao = new PersonagemDAO();

        // 1. CREATE: Criando e salvando personagens com inventário
        Personagem geralt = new Personagem("Geralt", 100, 40, 20);
        geralt.adicionarItem("Espada de Prata");
        geralt.adicionarItem("Poção Andorinha");
        dao.salvar(geralt);

        Personagem ciri = new Personagem("Ciri", 90, 60, 25);
        ciri.adicionarItem("Zireael");
        dao.salvar(ciri);

        Personagem eredin = new Personagem("Eredin",
                120,
                30,
                25);
        eredin.adicionarItem("Hav'caaren");
        dao.salvar(eredin);

        // 2. READ: Listando personagens salvos no arquivo rpg_save.db
        System.out.println("=== PERSONAGENS NO BANCO ===");
        dao.listarTodos().forEach(System.out::println);

//        // 3. UPDATE: Simulando dano em batalha e salvando o novo estado
//        Personagem carregado = dao.buscarPorId(geralt.getId());
//        if (carregado != null) {
//            carregado.setHp(65);
//            carregado.adicionarItem("Troféu de Grifo");
//            dao.atualizar(carregado);
//            System.out.println("\n=== APÓS SALVAR PROGRESSO ===");
//            System.out.println(dao.buscarPorId(carregado.getId()));
//        }
//
//        // 4. DELETE: Removendo um registro
//        dao.deletar(ciri.getId());
//        System.out.println("\n=== APÓS DELETAR PERSONAGEM ===");
//        dao.listarTodos().forEach(System.out::println);
//    }
    }
}
