import java.util.*;

public class jogoDaVelha {

    // Jogo da velha variaveis
    int quemJogaPrimeiro;
    String bolaOuX;
    String bolaOuXMaquina;
    String[] posicoes;
    int quantidadeDeJogadas;
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
    int estadoInicial;
    List<String> adj;

    jogoDaVelha(Scanner scanner) {
        System.out.println("Quem joga primeiro ? (Máquina: 1, Jogador: 0)");
        this.quemJogaPrimeiro = scanner.nextInt();
        while (quemJogaPrimeiro != 0 && quemJogaPrimeiro != 1) {
            System.out.println("Opção invalida, escolha um desses:");
            System.out.println("Quem joga primeiro ? (Máquina: 1, Jogador: 0)");
            this.quemJogaPrimeiro = scanner.nextInt();
        }
        System.out.println("Voce quer ser o X ou a O ?");
        this.bolaOuX = scanner.next();
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
        this.quantidadeDeJogadas = 0;
    }

    boolean deuEmpate(String[] board) {
        for (int i = 0; i < 9; i++) {
            if (board[i].equals(Integer.toString(i + 1))) {
                return true;
            }

        }
        return false;
    }

    void montarJogoDaVelha() {
        System.out.println(String.format(" %s  |  %s  |  %s", this.posicoes[0], this.posicoes[1], this.posicoes[2]));
        System.out.println("---------------");
        System.out.println(String.format(" %s  |  %s  |  %s", this.posicoes[3], this.posicoes[4], this.posicoes[5]));
        System.out.println("---------------");
        System.out.println(String.format(" %s  |  %s  |  %s", this.posicoes[6], this.posicoes[7], this.posicoes[8]));
    }

    // Jogador escolhe a posição
    void escolherPosicaoJogador(Scanner scanner) {
        this.quantidadeDeJogadas++;
        System.out.println("Em qual posição voce quer jogar ? (Escolha de 1 a 9)");
        int proximaPosicao = scanner.nextInt();
        while ((proximaPosicao <= 0 || proximaPosicao > 9) || this.posicoes[proximaPosicao - 1] == this.bolaOuX) {
            System.out.println("Opção invalida, escolha um desses:\n");
            montarJogoDaVelha();
            System.out.println("Em qual posição voce quer jogar ? (Escolha de 1 a 9)");
            proximaPosicao = scanner.nextInt();
        }
        if (this.quantidadeDeJogadas == 1) {
            this.estadoInicial = proximaPosicao - 1;
        }
        this.posicoes[proximaPosicao - 1] = this.bolaOuX;
    }

    // Aplicar BFS e DFS aqui XD
    void escolherPosicaoMaquina() {
        this.quantidadeDeJogadas++;
        escolheMelhorJogada();
    }

    void escolheMelhorJogada() {
        int bestChoise = -1;
        int bestScore = -1;
        for (int i = 0; i < this.posicoes.length; i++) {
            if (posicoes[i].equals(Integer.toString(i + 1))) {
                posicoes[i] = bolaOuXMaquina;
                int qualJogada = melhorJogadaAtual(posicoes, false);
                posicoes[i] = Integer.toString(i + 1); // Desfaz a jogada
                if (qualJogada > bestScore) {
                    bestScore = qualJogada;
                    bestChoise = i;
                }
            }
        }
        this.posicoes[bestChoise] = this.bolaOuXMaquina;
    }

    int melhorJogadaAtual(String[] board, Boolean isMax) {

        if (escolhasDaVitoria(board) == this.bolaOuX) {
            return -10;
        }
        if (escolhasDaVitoria(board) == this.bolaOuXMaquina) {
            return 10;
        }
        if (deuEmpate(board) == false) {
            return 0;
        }

        // Jogada do jogador
        if (isMax) {
            int best = -1000;
            for (int i = 0; i < 9; i++) {
                if (board[i].equals(Integer.toString(i + 1))) {
                    board[i] = bolaOuX;
                    best = Math.max(best, melhorJogadaAtual(board, !isMax));
                    board[i] = Integer.toString(i + 1);
                }
            }
            return best;
        } else {
            // Jogada da maquina
            int best = 1000;
            for (int i = 0; i < 9; i++) {
                if (board[i].equals(Integer.toString(i + 1))) {
                    board[i] = bolaOuXMaquina;
                    best = Math.min(best, melhorJogadaAtual(board, !isMax));
                    board[i] = Integer.toString(i + 1);
                }
            }
            return best;
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

    void loopJogo(Scanner scanner) {
        while (true) {
            if (this.quemJogaPrimeiro == 0) {
                montarJogoDaVelha();
                escolherPosicaoJogador(scanner);
                if (this.quantidadeDeJogadas >= 5) {
                    if (escolhasDaVitoria(this.posicoes) == this.bolaOuX) {
                        montarJogoDaVelha();
                        System.out.println("Vitoria do Jogador!");
                        break;
                    } else if (escolhasDaVitoria(this.posicoes) == this.bolaOuXMaquina) {
                        montarJogoDaVelha();
                        System.out.println("Vitoria da Maquina!");
                        break;
                    }
                }
                escolherPosicaoMaquina();
            } else {
                escolherPosicaoMaquina();
                if (this.quantidadeDeJogadas >= 5) {
                    if (escolhasDaVitoria(this.posicoes) == this.bolaOuX) {
                        montarJogoDaVelha();
                        System.out.println("Vitoria do Jogador!");
                        break;
                    } else if (escolhasDaVitoria(this.posicoes) == this.bolaOuXMaquina) {
                        montarJogoDaVelha();
                        System.out.println("Vitoria da Maquina!");
                    }
                }
                montarJogoDaVelha();
                escolherPosicaoJogador(scanner);   
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Escolhe o nivel de dificuldade e quem joga primeiro
        jogoDaVelha jogoDaVelha = new jogoDaVelha(scanner);
        jogoDaVelha.loopJogo(scanner);
        // Fazer para jogar novamente?
        scanner.close();
    }
}