import java.util.Scanner;

public class Arvore {
    public static final int tamTab = 21;

    

    static class Atleta {
        String nome;
        int ouro, prata, bronze;
    }

    static class ArvoreBinaria {
        class No {
            Atleta elemento;
            No esq, dir;

            public No(Atleta elemento) {
                this.elemento = elemento;
                this.esq = null;
                this.dir = null;
            }
        }

        No raiz;

        public ArvoreBinaria() {
            raiz = null;
        }

        public void inserir(Atleta x) {
            raiz = inserir(raiz, x);
        }

        public No inserir(No no, Atleta x) {
            if (no == null) {
                no = new No(x);
            }

            else if (no.elemento.nome.compareToIgnoreCase(x.nome) > 0) {
                no.esq = inserir(no.esq, x);
            }

            else if (no.elemento.nome.compareToIgnoreCase(x.nome) < 0) {
                no.dir = inserir(no.dir, x);
            }

            else
                System.out.println("Erro ao inserir");

            return no;
        }

        public void caminhar() {
            caminhar(raiz);
        }

        public No caminhar(No no) {
            if (no != null) {
                caminhar(no.esq);
                System.out.printf(no.elemento.nome + " " + no.elemento.ouro + " " + no.elemento.prata
                        + " " + no.elemento.bronze + "\n");
                caminhar(no.dir);
            }
            return no;
        }
    }

    public static int hash(String nome) {
        int ascii = 0;
        for (int i = 0; i < nome.length(); i++) {
            ascii += (int) nome.charAt(i);
        }
        return ascii % tamTab;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Atleta[] tabela = new Atleta[tamTab];

        ArvoreBinaria arvore = new ArvoreBinaria();

        for (int i = 0; i < 30; i++) {
            Atleta atleta = new Atleta();
            atleta.nome = sc.next();
            atleta.ouro = sc.nextInt();
            atleta.prata = sc.nextInt();
            atleta.bronze = sc.nextInt();

            int pos = hash(atleta.nome);

            if (tabela[pos] == null)
                tabela[pos] = atleta;
            else {
                arvore.inserir(atleta);
            }
        }

        System.out.println("Ateltas na tabela:");
        for (int i = 0; i < tamTab; i++) {
            if (tabela[i] != null)
                System.out.println("posição " + i + ": " + tabela[i].nome + " " + tabela[i].ouro + " " + tabela[i].prata
                        + " " + tabela[i].bronze);
            ;
        }

        System.out.println("Ateltas na arvore:");
        arvore.caminhar();

        sc.close();
    }
}
