import java.util.Scanner;

class MatrizDinamica {
    static class Celula {
        int elemento;
        Celula direita, abaixo;

        Celula(int elemento) {
            this.elemento = elemento;
        }
    }

    int linhas, colunas;
    Celula inicio;

    // Construtor
    MatrizDinamica(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;

        if (linhas <= 0 || colunas <= 0) return;

        // Cria as linhas
        Celula[] linhaAnterior = new Celula[colunas];
        for (int i = 0; i < linhas; i++) {
            Celula atual = null, esquerda = null;
            for (int j = 0; j < colunas; j++) {
                Celula nova = new Celula(0);
                if (j == 0 && i == 0) inicio = nova;

                if (esquerda != null) esquerda.direita = nova;
                esquerda = nova;

                if (i > 0) linhaAnterior[j].abaixo = nova;
                linhaAnterior[j] = nova;

                if (j == 0 && i == 0) atual = nova;
            }
        }
    }

    void preencher(Scanner sc) {
        Celula linha = inicio;
        for (int i = 0; i < linhas; i++) {
            Celula col = linha;
            for (int j = 0; j < colunas; j++) {
                col.elemento = sc.nextInt();
                col = col.direita;
            }
            linha = linha.abaixo;
        }
    }

    void mostrarDiagonalPrincipal() {
        Celula atual = inicio;
        for (int i = 0; i < Math.min(linhas, colunas); i++) {
            System.out.print(atual.elemento + " ");
            if (atual.direita != null && atual.abaixo != null)
                atual = atual.direita.abaixo;
        }
        System.out.println();
    }

    void mostrarDiagonalSecundaria() {
    Celula linhaAtual = inicio;
    for (int i = 0; i < linhas; i++) {
        // Começa na linha i
        Celula celula = linhaAtual;
        // Vai para a coluna (colunas - 1 - i)
        for (int j = 0; j < colunas - 1 - i; j++) {
            celula = celula.direita;
        }
        System.out.print(celula.elemento + " ");
        linhaAtual = linhaAtual.abaixo;
    }
    System.out.println();
}



    MatrizDinamica soma(MatrizDinamica outra) {
        if (linhas != outra.linhas || colunas != outra.colunas)
            throw new IllegalArgumentException("Dimensões incompatíveis para soma");

        MatrizDinamica resultado = new MatrizDinamica(linhas, colunas);

        Celula l1 = this.inicio;
        Celula l2 = outra.inicio;
        Celula lr = resultado.inicio;

        for (int i = 0; i < linhas; i++) {
            Celula c1 = l1;
            Celula c2 = l2;
            Celula cr = lr;

            for (int j = 0; j < colunas; j++) {
                cr.elemento = c1.elemento + c2.elemento;
                c1 = c1.direita;
                c2 = c2.direita;
                cr = cr.direita;
            }

            l1 = l1.abaixo;
            l2 = l2.abaixo;
            lr = lr.abaixo;
        }

        return resultado;
    }

    MatrizDinamica multiplicacao(MatrizDinamica outra) {
        if (this.colunas != outra.linhas)
            throw new IllegalArgumentException("Dimensões incompatíveis para multiplicação");

        MatrizDinamica resultado = new MatrizDinamica(this.linhas, outra.colunas);

        Celula linhaA = this.inicio;
        Celula linhaR = resultado.inicio;

        for (int i = 0; i < this.linhas; i++) {
            Celula colunaR = linhaR;
            for (int j = 0; j < outra.colunas; j++) {
                int soma = 0;
                Celula a = linhaA;
                Celula b = outra.inicio;

                for (int k = 0; k < j; k++)
                    b = b.direita;

                for (int k = 0; k < this.colunas; k++) {
                    soma += a.elemento * b.elemento;
                    a = a.direita;
                    b = b.abaixo;
                }

                colunaR.elemento = soma;
                colunaR = colunaR.direita;
            }

            linhaA = linhaA.abaixo;
            linhaR = linhaR.abaixo;
        }

        return resultado;
    }

    void imprimir() {
        Celula linha = inicio;
        while (linha != null) {
            Celula coluna = linha;
            while (coluna != null) {
                System.out.print(coluna.elemento + " ");
                coluna = coluna.direita;
            }
            System.out.println();
            linha = linha.abaixo;
        }
    }

    // Main para leitura e execução dos casos de teste
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int casos = sc.nextInt();

        for (int t = 0; t < casos; t++) {
            int l1 = sc.nextInt(), c1 = sc.nextInt();
            MatrizDinamica m1 = new MatrizDinamica(l1, c1);
            m1.preencher(sc);

            int l2 = sc.nextInt(), c2 = sc.nextInt();
            MatrizDinamica m2 = new MatrizDinamica(l2, c2);
            m2.preencher(sc);

            // Diagonais da primeira matriz
            m1.mostrarDiagonalPrincipal();
            m1.mostrarDiagonalSecundaria();

            // Soma (se possível)
            try {
                MatrizDinamica soma = m1.soma(m2);
                soma.imprimir();
            } catch (Exception e) {
                System.out.println("Soma inválida");
            }

            // Multiplicação (se possível)
            try {
                MatrizDinamica mult = m1.multiplicacao(m2);
                mult.imprimir();
            } catch (Exception e) {
                System.out.println("Multiplicação inválida");
            }
        }

        sc.close();
    }
}

