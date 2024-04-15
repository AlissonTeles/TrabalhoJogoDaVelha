import java.util.*;

public class JogoDaVelha {

    // Jogo da velha variaveis
    int dificuldade;
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
    // Cor de texto padrão
    static String textColorReset = "\u001B[0m";
    // Cores de texto
    static String textYellow = "\u001B[33m";
    static String textGreen = "\u001B[32m";
    static String textRed = "\u001B[31m";

    JogoDaVelha(Scanner scanner) {
        System.out.println("Qual o nível de dificuldade ? (Fácil: 1, Médio: 2, Difícil: 3)");
        while (!scanner.hasNextInt()) { // verifica se não for um inteiro
            System.out.println(textYellow + "Opção inválida, escolha um desses:" + textColorReset);
            System.out.println("Qual o nível de dificuldade ? (Fácil: 1, Médio: 2, Difícil: 3)");
            scanner.nextLine(); // Limpa a entrada inválida
        }
        this.dificuldade = scanner.nextInt();
        scanner.nextLine(); // Limpa o restante da linha
        while (dificuldade != 1 && dificuldade != 2 && dificuldade != 3) {
            try {
                System.out.println(textYellow + "Opção inválida, escolha um desses:" + textColorReset);
                System.out.println("Qual o nível de dificuldade ? (Fácil: 1, Médio: 2, Difícil: 3)");
                this.dificuldade = scanner.nextInt();
                scanner.nextLine(); // Limpa o restante da linha
            } catch (InputMismatchException e) { // Caso o usuário coloque diferente de um int
                scanner.nextLine(); // Limpa a entrada inválida
            }
        }

        System.out.println("Quem joga primeiro ? (Máquina: 1, Jogador: 0)");
        while (!scanner.hasNextInt()) { // verifica se não for um inteiro
            System.out.println(textYellow + "Opção inválida, escolha um desses:" + textColorReset);
            System.out.println("Quem joga primeiro ? (Máquina: 1, Jogador: 0)");
            scanner.nextLine(); // Limpa a entrada inválida
        }
        this.quemJogaPrimeiro = scanner.nextInt();
        scanner.nextLine(); // Limpa o restante da linha
        while (quemJogaPrimeiro != 0 && quemJogaPrimeiro != 1) {
            try {
                System.out.println(textYellow + "Opção inválida, escolha um desses:" + textColorReset);
                System.out.println("Quem joga primeiro ? (Máquina: 1, Jogador: 0)");
                this.quemJogaPrimeiro = scanner.nextInt();
                scanner.nextLine(); // Limpa o restante da linha
            } catch (InputMismatchException e) { // Caso o usuário coloque diferente de um int
                scanner.nextLine(); // Limpa a entrada inválida
            }
        }

        System.out.println("Você quer ser o X ou a O ?");
        this.bolaOuX = scanner.nextLine();
        while (!bolaOuX.equals("X") && !bolaOuX.equals("O")) {
            System.out.println(textYellow + "Opção inválida, escolha um desses:" + textColorReset);
            System.out.println("Você quer ser o X ou a O ?");
            this.bolaOuX = scanner.nextLine(); // Limpa o restante da linha
        }

        if (this.bolaOuX.equals("X")) {
            this.bolaOuXMaquina = "O";
        } else {
            this.bolaOuXMaquina = "X";
        }

        // Cria as posições do jogo da velha
        this.posicoes = new String[9];
        for (int i = 0; i < 9; i++) {
            this.posicoes[i] = Integer.toString(i + 1);
        }
    }

    // Mostra como que está o jogo da velha
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
        // Não pode ser fora do jogo da velha e nem dos que já tem
        int proximaPosicao = 10;
        System.out.println("Em qual posição você quer jogar ? (Escolha de 1 a 9)");
        while (!scanner.hasNextInt()) { // verifica se não for um inteiro
            System.out.println(textYellow + "Opção inválida, escolha um desses:\n" + textColorReset);
            montarJogoDaVelha();
            System.out.println("Em qual posição você quer jogar ? (Escolha de 1 a 9)");
            scanner.nextLine(); // Limpa a entrada inválida
        }
        proximaPosicao = scanner.nextInt();
        scanner.nextLine(); // Limpa o restante da linha
        while ((proximaPosicao <= 0 || proximaPosicao > 9) || this.posicoes[proximaPosicao - 1] == this.bolaOuX
                || this.posicoes[proximaPosicao - 1] == this.bolaOuXMaquina) {
            try {
                System.out.println(textYellow + "Opção inválida, escolha um desses:\n" + textColorReset);
                montarJogoDaVelha();
                System.out.println("Em qual posição você quer jogar ? (Escolha de 1 a 9)");
                proximaPosicao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) { // Caso o usuário coloque diferente de um int
                scanner.nextLine();
            }
        }

        this.posicoes[proximaPosicao - 1] = this.bolaOuX;
    }

    // Máquina faz uma busca para saber qual a melhor jogada no momento
    void escolherPosicaoMaquina() {
        int melhorJogadaIndex = 0;
        int melhorJogadaPossivel = -100;
        melhorJogadaIndex = medirDificuldade(posicoes, melhorJogadaIndex, melhorJogadaPossivel);
        this.posicoes[melhorJogadaIndex] = this.bolaOuXMaquina;
    }

    // Faz a jogada dependendo da dificuldade
    int medirDificuldade(String[] posicoes, int melhorJogadaIndex, int melhorJogadaPossivel) {
        Random gerador = new Random();
        int randomPlay = 0;
        // Se for 1 sempre faz jogadas randomicamente
        if (this.dificuldade == 1) {
            randomPlay = gerador.nextInt(9);
            while (posicoes[randomPlay].equals("X") || posicoes[randomPlay].equals("O")) {
                randomPlay = gerador.nextInt(9);
            }
            return randomPlay;
        }
        // Se for 2, tem 50% de fazer uma jogadas randomicamente
        if (this.dificuldade == 2) {
            if (gerador.nextInt(2) == 1) {
                randomPlay = gerador.nextInt(9);
                while (posicoes[randomPlay].equals("X") || posicoes[randomPlay].equals("O")) {
                    randomPlay = gerador.nextInt(9);
                }
                return randomPlay;
            } else {
                for (int i = 0; i < posicoes.length; i++) {
                    if (posicoes[i].equals(Integer.toString(i + 1))) {
                        posicoes[i] = this.bolaOuXMaquina;
                        int melhorJogada = melhorJogadaAtual(posicoes, false, 0);
                        posicoes[i] = Integer.toString(i + 1);
                        if (melhorJogada > melhorJogadaPossivel) {
                            melhorJogadaPossivel = melhorJogada;
                            melhorJogadaIndex = i;
                        }
                    }
                }
                return melhorJogadaIndex;
            }
        }
        // Se for 3 ele sempre faz a busca em profundidade/largura para fazer a jogada
        if (this.dificuldade == 3) {
            for (int i = 0; i < posicoes.length; i++) {
                if (posicoes[i].equals(Integer.toString(i + 1))) {
                    posicoes[i] = this.bolaOuXMaquina;
                    int melhorJogada = melhorJogadaAtual(posicoes, false, 0);
                    posicoes[i] = Integer.toString(i + 1);
                    if (melhorJogada > melhorJogadaPossivel) {
                        melhorJogadaPossivel = melhorJogada;
                        melhorJogadaIndex = i;
                    }
                }
            }
            return melhorJogadaIndex;
        }
        return 0;
    }

    // Simula várias jogadas até encontrar a melhor possivel, fazendo por
    // recursividade
    int melhorJogadaAtual(String[] board, Boolean vezDoJogador, int profundidade) {
        // Verificar se alguém venceu
        String resultado = escolhasDaVitoria(board);
        if (resultado.equals(this.bolaOuXMaquina)) {
            return 10 - profundidade; // Vitória da máquina
        } else if (resultado.equals(this.bolaOuX)) {
            return -10 + profundidade; // Vitória do jogador
        } else if (deuEmpate(board)) {
            return 0; // Empate
        }

        // Vez do jogador
        if (vezDoJogador) {
            int melhorValor = -1000; // Salvamos o melhor valor da jogada para o jogador
            for (int i = 0; i < 9; i++) {
                if (board[i].equals(Integer.toString(i + 1))) {
                    board[i] = bolaOuXMaquina; // simula jogada
                    // Verifica se o jogo acabou ou não, salvando o melhor caso
                    melhorValor = Math.max(melhorValor, melhorJogadaAtual(board, !vezDoJogador, profundidade + 1));
                    board[i] = Integer.toString(i + 1); // desfaz
                }
            }
            return melhorValor;
        } else {
            // Vez da máquina
            int melhorValor = 1000; // Salvamos o pior cenário de jogada para a máquina
            for (int i = 0; i < 9; i++) {
                if (board[i].equals(Integer.toString(i + 1))) {
                    board[i] = bolaOuX; // simula jogada
                    // Verifica se o jogo acabou ou não, salvando o caso
                    melhorValor = Math.min(melhorValor, melhorJogadaAtual(board, !vezDoJogador, profundidade + 1));
                    board[i] = Integer.toString(i + 1); // desfaz
                }
            }
            return melhorValor;
        }
    }

    // Verifica se alguem ganhou
    String escolhasDaVitoria(String board[]) {

        // Verifica cada combinação de vitória
        for (int[] combinacao : this.combinacoesVitoria) {
            int posicao1 = combinacao[0];
            int posicao2 = combinacao[1];
            int posicao3 = combinacao[2];

            // Verifica se todas as posições têm o mesmo valor (X ou O)
            if (board[posicao1].equals(this.bolaOuX) &&
                    board[posicao2].equals(this.bolaOuX) &&
                    board[posicao3].equals(this.bolaOuX)) {
                return this.bolaOuX;
            }
            if (board[posicao1].equals(this.bolaOuXMaquina) &&
                    board[posicao2].equals(this.bolaOuXMaquina) &&
                    board[posicao3].equals(this.bolaOuXMaquina)) {
                return this.bolaOuXMaquina;
            }

        }
        return "";
    }

    boolean deuEmpate(String[] board) {
        // Conta quantas posições estão ocupadas
        int ocupadas = 0;
        for (String posicao : board) {
            if (posicao.equals(this.bolaOuX) || posicao.equals(this.bolaOuXMaquina)) {
                ocupadas++;
            }
        }
        // Se todas as posições estiverem ocupadas e ainda não houver um vencedor, então
        // é empate
        return ocupadas == 9;
    }

    boolean acabouJogo() {
        // Verificar se teve vencedor de algum dos lados
        if (escolhasDaVitoria(this.posicoes) == this.bolaOuX) {
            montarJogoDaVelha();
            System.out.println(textGreen + "Vitória do Jogador!" + textColorReset);
            return true;
        } else if (escolhasDaVitoria(this.posicoes) == this.bolaOuXMaquina) {
            montarJogoDaVelha();
            System.out.println(textRed + "Vitória da Máquina!" + textColorReset);
            return true;
        } else if (deuEmpate(this.posicoes)) {
            montarJogoDaVelha();
            System.out.println(textYellow + "Empate!" + textColorReset);
            return true;
        }
        return false;
    }

    void loopJogo(Scanner scanner) {
        // Loop que repete até o jogo acabar
        while (true) {
            if (this.quemJogaPrimeiro == 0) {
                montarJogoDaVelha();
                escolherPosicaoJogador(scanner);
                if (acabouJogo()) {
                    break;
                }
                escolherPosicaoMaquina();
                if (acabouJogo()) {
                    break;
                }
            } else {
                escolherPosicaoMaquina();
                if (acabouJogo()) {
                    break;
                }
                montarJogoDaVelha();
                escolherPosicaoJogador(scanner);
                if (acabouJogo()) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out
                .println(textYellow + "####################### - Jogo da Velha - ######################"
                        + textColorReset);
        System.out
                .println(textGreen + "Bem-vindo! Tenha uma ótima jogatina." + textColorReset);
        // Loop externo para permitir jogar novamente
        while (true) {
            JogoDaVelha jogo = new JogoDaVelha(scanner);
            jogo.loopJogo(scanner);

            System.out.println("Deseja jogar novamente? (S/N)");
            String jogarNovamente = scanner.nextLine();
            while (!jogarNovamente.equals("N") && !jogarNovamente.equals("S")) {
                System.out.println(textYellow + "Opção inválida, escolha um desses:" + textColorReset);
                System.out.println("Deseja jogar novamente? (S/N)");
                jogarNovamente = scanner.nextLine(); // Limpa o restante da linha
            }
            if (!jogarNovamente.equals("S")) {
                System.out.println(textGreen + "Obrigado por jogar!" + textColorReset);
                break;
            }
        }

        scanner.close();
    }
}