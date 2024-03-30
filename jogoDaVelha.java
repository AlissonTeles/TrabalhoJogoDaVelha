import java.util.*;


public class jogoDaVelha {

    int dificuldade;
    int quemJogaPrimeiro;
    String bolaOuX;
    String[] posicoes;
    int quantidadeDeJogadas;

    jogoDaVelha(Scanner scanner) {
        System.out.println("Escolha o nível de dificuldade (Facil: 1, Médio: 2, Dificil: 3): ");
        this.dificuldade = scanner.nextInt();
        while (dificuldade != 1 && dificuldade != 2 && dificuldade != 3) {
            System.out.println("Opção invalida, escolha um desses:");
            System.out.println("Escolha o nível de dificuldade (Facil: 1, Médio: 2, Dificil: 3): ");
            this.dificuldade = scanner.nextInt();
        }
        System.out.println("Quem joga primeiro ? (Máquina: 1, Jogador: 0)");
        this.quemJogaPrimeiro = scanner.nextInt();
        while (quemJogaPrimeiro != 0 && quemJogaPrimeiro != 1) {
            System.out.println("Opção invalida, escolha um desses:");
            System.out.println("Quem joga primeiro ? (Máquina: 1, Jogador: 0)");
            this.quemJogaPrimeiro = scanner.nextInt();
        }
        System.out.println("Voce quer ser o X ou a O ?");
        this.bolaOuX = scanner.nextLine();
        while (!bolaOuX.equals("X") && !bolaOuX.equals("O")) {
            System.out.println("Opção invalida, escolha um desses:");
            System.out.println("Voce quer ser o X ou a O ?");
            this.bolaOuX = scanner.nextLine();
        }
        
        this.posicoes = new String[9];
        for (int i = 0; i < 9; i++) {
            this.posicoes[i] = Integer.toString(i + 1);
        }
        this.quantidadeDeJogadas = 0;
    }

    void montarJogoDaVelha() {
        System.out.println(String.format(" %s  |  %s  |  %s", this.posicoes[0], this.posicoes[1], this.posicoes[2] ));
        System.out.println("---------------");
        System.out.println(String.format(" %s  |  %s  |  %s", this.posicoes[3], this.posicoes[4], this.posicoes[5] ));
        System.out.println("---------------");
        System.out.println(String.format(" %s  |  %s  |  %s", this.posicoes[6], this.posicoes[7], this.posicoes[8] ));
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
        System.out.println("sERA QUE JA PEGOU? ");
        System.out.println(this.posicoes[proximaPosicao - 1]);

        this.posicoes[proximaPosicao - 1] = this.bolaOuX;
    }

    // Aplicar BFS e DFS aqui XD
    void escolherPosicaoMaquina() {
        this.quantidadeDeJogadas++;
    }

    // Verifica se alguem ganhou
    boolean escolhasDaVitoria(String bolaOuX) {
        // Define as combinações de vitória
        int[][] combinacoesVitoria = {
            {0, 1, 2}, // Linha 1
            {3, 4, 5}, // Linha 2
            {6, 7, 8}, // Linha 3
            {0, 3, 6}, // Coluna 1
            {1, 4, 7}, // Coluna 2
            {2, 5, 8}, // Coluna 3
            {0, 4, 8}, // Diagonal principal
            {2, 4, 6}  // Diagonal secundária
        };
    
        // Verifica cada combinação de vitória
        for (int[] combinacao : combinacoesVitoria) {
            int posicao1 = combinacao[0];
            int posicao2 = combinacao[1];
            int posicao3 = combinacao[2];
    
            // Verifica se todas as posições têm o mesmo valor (X ou O)
            if (this.posicoes[posicao1].equals(bolaOuX) && 
                this.posicoes[posicao2].equals(bolaOuX) && 
                this.posicoes[posicao3].equals(bolaOuX)) {
                return true;
            }
        }
        return false;
    }
    

    void loopJogo(Scanner scanner) {
        while (true) {
            if (this.quemJogaPrimeiro == 0) {
                montarJogoDaVelha();
                escolherPosicaoJogador(scanner);
                if (this.quantidadeDeJogadas >= 5) {
                    if (escolhasDaVitoria(this.bolaOuX) == true) {
                        montarJogoDaVelha();
                        System.out.println("Vitoria do Jogador!");
                        break;
                    }
                }
                escolherPosicaoMaquina();
            }
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Escolhe o nivel de dificuldade e quem joga primeiro
        jogoDaVelha jogoDaVelha = new jogoDaVelha(scanner);
        jogoDaVelha.loopJogo(scanner);
        scanner.close();
    }
}