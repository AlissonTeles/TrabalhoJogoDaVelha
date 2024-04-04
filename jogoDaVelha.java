import java.util.*;

public class JogoDaVelha {

    // Jogo da velha variaveis
    int quemJogaPrimeiro;
    String bolaOuX;
    String bolaOuXMaquina;
    String[] posicoes;
    int[][] combinacoesVitoria = {
            { 0, 1, 2 }, // Linha 1
            { 3, 4, 5 }, // Linha 2
            { 6, 7, 8 }, // Linha 3
            { 0, 3, 6 }, // Coluna 1
            { 1, 4, 7 }, // Coluna 2
            { 2, 5, 8 }, // Coluna 3
            { 0, 4, 8 }, // Diagonal principal
            { 2, 4, 6 } // Diagonal secundária
    };

    // Busca variaveis
    int vertices;
    List<String> adj;

    JogoDaVelha(Scanner scanner) {
        this.quemJogaPrimeiro = 2;
        while (quemJogaPrimeiro != 0 && quemJogaPrimeiro != 1) {
            try {
                System.out.println("Opção invalida, escolha um desses:");
                System.out.println("Quem joga primeiro ? (Máquina: 1, Jogador: 0)");
                this.quemJogaPrimeiro = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) { // Caso o usuário coloque diferente de um int
                scanner.nextLine();
            }
        }

        System.out.println("Voce quer ser o X ou a O ?");
        this.bolaOuX = scanner.nextLine();
        while (!bolaOuX.equals("X") && !bolaOuX.equals("O")) {
            System.out.println("Opção invalida, escolha um desses:");
            System.out.println("Voce quer ser o X ou a O ?");
            this.bolaOuX = scanner.nextLine();
        }

        if (this.bolaOuX.equals("X")) {
            this.bolaOuXMaquina = "O";
        } else {
            this.bolaOuXMaquina = "X";
        }

        this.posicoes = new String[9];
        for (int i = 0; i < 9; i++) {
            this.posicoes[i] = Integer.toString(i + 1);
        }
    }

    void montarJogoDaVelha() {
        System.out.println();
        System.out.println(String.format(" %s  |  %s  |  %s", this.posicoes[0], this.posicoes[1], this.posicoes[2]));
        System.out.println("---------------");
        System.out.println(String.format(" %s  |  %s  |  %s", this.posicoes[3], this.posicoes[4], this.posicoes[5]));
        System.out.println("---------------");
        System.out.println(String.format(" %s  |  %s  |  %s", this.posicoes[6], this.posicoes[7], this.posicoes[8]));
        System.out.println();

    }

    // Jogador escolhe a posição
    void escolherPosicaoJogador(Scanner scanner) {
        System.out.println("Em qual posição voce quer jogar ? (Escolha de 1 a 9)");
        int proximaPosicao = scanner.nextInt();
        while ((proximaPosicao <= 0 || proximaPosicao > 9) || this.posicoes[proximaPosicao - 1] == this.bolaOuX
                || this.posicoes[proximaPosicao - 1] == this.bolaOuXMaquina) {
            System.out.println("Opção invalida, escolha um desses:\n");
            montarJogoDaVelha();
            System.out.println("Em qual posição voce quer jogar ? (Escolha de 1 a 9)");
            proximaPosicao = scanner.nextInt();
        }
        this.posicoes[proximaPosicao - 1] = this.bolaOuX;
    }

    void escolherPosicaoMaquina() {
        int melhorJogadaIndex = 0;
        int melhorJogadaPossivel = -1;
        for (int i = 0; i < this.posicoes.length; i++) {
            if (posicoes[i].equals(Integer.toString(i + 1))) {
                this.posicoes[i] = this.bolaOuXMaquina;
                int melhorJogada = melhorJogadaAtual(posicoes, false);
                this.posicoes[i] = Integer.toString(i + 1);
                if (melhorJogada > melhorJogadaPossivel) {
                    melhorJogadaPossivel = melhorJogada;
                    melhorJogadaIndex = i;
                }
            }
        }
        this.posicoes[melhorJogadaIndex] = this.bolaOuXMaquina;
    }

    int melhorJogadaAtual(String[] board, Boolean vezDoJogador) {
        // Verificar se alguém venceu
        String resultado = escolhasDaVitoria(board);
        if (resultado.equals(this.bolaOuX)) {
            return -10; // Vitória do jogador
        } else if (resultado.equals(this.bolaOuXMaquina)) {
            return 10; // Vitória da máquina
        } else if (deuEmpate(board)) {
            return 0; // Empate
        }

        // Vez do jogador
        if (vezDoJogador) {
            int melhorValor = -1000; // Salvamos o melhor valor da jogada para o jogador
            for (int i = 0; i < 9; i++) {
                if (board[i].equals(Integer.toString(i + 1))) {
                    board[i] = bolaOuXMaquina;
                    melhorValor = Math.max(melhorValor, melhorJogadaAtual(board, !vezDoJogador));
                    board[i] = Integer.toString(i + 1);
                }
            }
            return melhorValor;
        } else {
            // Vez da máquina
            int melhorValor = 1000; // Salvamos o pior cenário de jogada para a máquina
            for (int i = 0; i < 9; i++) {
                if (board[i].equals(Integer.toString(i + 1))) {
                    board[i] = bolaOuX;
                    melhorValor = Math.min(melhorValor, melhorJogadaAtual(board, !vezDoJogador));
                    board[i] = Integer.toString(i + 1);
                }
            }
            return melhorValor;
        }
    }

    // Verifica se alguem ganhou
    String escolhasDaVitoria(String teste[]) {

        // Verifica cada combinação de vitória
        for (int[] combinacao : this.combinacoesVitoria) {
            int posicao1 = combinacao[0];
            int posicao2 = combinacao[1];
            int posicao3 = combinacao[2];

            // Verifica se todas as posições têm o mesmo valor (X ou O)
            if (teste[posicao1].equals(this.bolaOuX) &&
                    teste[posicao2].equals(this.bolaOuX) &&
                    teste[posicao3].equals(this.bolaOuX)) {
                return this.bolaOuX;
            }
            if (teste[posicao1].equals(this.bolaOuXMaquina) &&
                    teste[posicao2].equals(this.bolaOuXMaquina) &&
                    teste[posicao3].equals(this.bolaOuXMaquina)) {
                return this.bolaOuXMaquina;
            }

        }
        return "";
    }

    boolean deuEmpate(String[] board) {
        for (int i = 0; i < 9; i++) {
            if (board[i].equals(Integer.toString(i + 1))) {
                return false;
            }
        }
        return true;
    }

    void loopJogo(Scanner scanner) {
        while (true) {
            if (this.quemJogaPrimeiro == 0) {
                montarJogoDaVelha();
                escolherPosicaoJogador(scanner);
                if (escolhasDaVitoria(this.posicoes) == this.bolaOuX) {
                    montarJogoDaVelha();
                    System.out.println("Vitoria do Jogador!");
                    break;
                } else if (escolhasDaVitoria(this.posicoes) == this.bolaOuXMaquina) {
                    montarJogoDaVelha();
                    System.out.println("Vitoria da Maquina!");
                    break;
                } else if (deuEmpate(this.posicoes)) {
                    montarJogoDaVelha();
                    System.out.println("Empate!");
                    break;
                }
                escolherPosicaoMaquina();
            } else {
                escolherPosicaoMaquina();
                if (escolhasDaVitoria(this.posicoes) == this.bolaOuX) {
                    montarJogoDaVelha();
                    System.out.println("Vitoria do Jogador!");
                    break;
                } else if (escolhasDaVitoria(this.posicoes) == this.bolaOuXMaquina) {
                    montarJogoDaVelha();
                    System.out.println("Vitoria da Maquina!");
                    break;
                } else if (deuEmpate(this.posicoes)) {
                    montarJogoDaVelha();
                    System.out.println("Empate!");
                    break;
                }
                montarJogoDaVelha();
                escolherPosicaoJogador(scanner);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Loop externo para permitir jogar novamente
        while (true) {
            JogoDaVelha jogo = new JogoDaVelha(scanner);
            jogo.loopJogo(scanner);

            System.out.println("Deseja jogar novamente? (S/N)");
            String jogarNovamente = scanner.next();
            if (!jogarNovamente.equalsIgnoreCase("S")) {
                System.out.println("Obrigado por jogar!");
                break;
            }
        }

        scanner.close();
    }
}