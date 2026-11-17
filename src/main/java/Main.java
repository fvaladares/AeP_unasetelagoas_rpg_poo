import controller.MotorBatalha;
import controller.PersonagemDAO;
import model.Personagem;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PersonagemDAO dao = new PersonagemDAO();
    private static final MotorBatalha motor = new MotorBatalha();

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            //tratamento de exceção
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Digite apenas números válidos.");
                continue; // interrompe a execução e volta no início do loop
            }

            switch (opcao) {
                case 1 -> cadastrarPersonagem();
                case 2 -> listarPersonagens();
                case 3 -> adicionarItemInventario();
                case 4 -> curarPersonagem();
                case 5 -> iniciarArenaBatalha();
                case 6 -> excluirPersonagem();
                case 0 -> System.out.println("Saindo e salvando progresso... Até a próxima!");
                default -> System.out.println("⚠️ Opção inválida.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n==============================");
        System.out.println("   ⚔️  ARENA RPG - SQLITE  ⚔️");
        System.out.println("==============================");
        System.out.println("1. [CREATE] Cadastrar Personagem");
        System.out.println("2. [READ]   Listar Personagens Salvos");
        System.out.println("3. [UPDATE] Adicionar Item ao Inventário");
        System.out.println("4. [UPDATE] Restaurar HP e Mana (Estalagem)");
        System.out.println("5. [GAME]   Entrar na Arena de Batalha");
        System.out.println("6. [DELETE] Excluir Personagem");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarPersonagem() {
        System.out.print("Nome do personagem: ");
        String nome = scanner.nextLine();

        System.out.print("HP inicial (ex: 100): ");
        int hp = Integer.parseInt(scanner.nextLine());

        System.out.print("Mana inicial (ex: 50): ");
        int mana = Integer.parseInt(scanner.nextLine());

        System.out.print("Ataque base (ex: 20): ");
        int atk = Integer.parseInt(scanner.nextLine());

        Personagem p = new Personagem(nome, hp, mana, atk);
        dao.salvar(p);
        System.out.println("✅ Personagem salvo no banco com ID: " + p.getId());
    }

    private static void listarPersonagens() {
        List<Personagem> lista = dao.listarTodos();
        System.out.println("\n--- PERSONAGENS NO BANCO ---");
        if (lista.isEmpty()) {
            System.out.println("Nenhum personagem cadastrado.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private static void adicionarItemInventario() {
        listarPersonagens();
        System.out.print("\nDigite o ID do personagem: ");
        int id = Integer.parseInt(scanner.nextLine());

        Personagem p = dao.buscarPorId(id);
        if (p == null) {
            System.out.println("❌ Personagem não encontrado.");
            return;
        }

        System.out.print("Nome do item a adicionar: ");
        String item = scanner.nextLine();
        p.adicionarItem(item);
        dao.atualizar(p);
        System.out.println("✅ Item salvo no inventário (JSON atualizado no SQLite)!");
    }

    private static void curarPersonagem() {
        listarPersonagens();
        System.out.print("\nDigite o ID do personagem para descansar na estalagem: ");
        int id = Integer.parseInt(scanner.nextLine());

        Personagem p = dao.buscarPorId(id);
        if (p == null) {
            System.out.println("❌ Personagem não encontrado.");
            return;
        }

        p.setHp(100);
        p.setMana(50);
        dao.atualizar(p);
        System.out.println("✨ " + p.getNome() + " descansou e recuperou HP (100) e Mana (50)!");
    }

    private static void iniciarArenaBatalha() {
        listarPersonagens();
        System.out.print("\nID do Atacante: ");
        int idAtacante = Integer.parseInt(scanner.nextLine());
        System.out.print("ID do Defensor: ");
        int idDefensor = Integer.parseInt(scanner.nextLine());

        Personagem atacante = dao.buscarPorId(idAtacante);
        Personagem defensor = dao.buscarPorId(idDefensor);

        if (atacante == null || defensor == null) {
            System.out.println("❌ Um dos personagens não foi encontrado.");
            return;
        }

        System.out.println("\nEscolha a ação de " + atacante.getNome() + ":");
        System.out.println("1. Ataque Básico (" + atacante.getAtaqueBase() + " dano)");
        System.out.println("2. Golpe Especial (2x dano | Custo: 20 Mana)");
        System.out.print("Ação: ");
        int acao = Integer.parseInt(scanner.nextLine());

        if (acao == 1) {
            motor.realizarAtaque(atacante, defensor);
        } else if (acao == 2) {
            System.out.print("Nome do golpe especial: ");
            String golpe = scanner.nextLine();
            motor.invocarGolpeEspecial(atacante, defensor, golpe, 20);
        } else {
            System.out.println("⚠️ Ação cancelada.");
        }
    }

    private static void excluirPersonagem() {
        listarPersonagens();
        System.out.print("\nDigite o ID do personagem para excluir: ");
        int id = Integer.parseInt(scanner.nextLine());

        dao.deletar(id);
        System.out.println("🗑️ Registro removido do banco de dados.");
    }
}