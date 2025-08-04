public class Arvore2 {
    private No1 raiz;
    private int comparacoes;

    public Arvore2() {
        int[] valores = { 7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14 };
        for (int valor : valores) {
            raiz = inserirNo1(raiz, valor);
        }
    }

    private No1 inserirNo1(No1 i, int x) {
        if (i == null) {
            i = new No1(x);
        } else if (x < i.chave) {
            i.esq = inserirNo1(i.esq, x);
        } else if (x > i.chave) {
            i.dir = inserirNo1(i.dir, x);
        }
        return i;
    }

    public void inserir(Show s) throws Exception {
        int chave = s.release % 15;
        raiz = inserirShow(raiz, chave, s.title);
    }

    private No1 inserirShow(No1 i, int chave, String titulo) throws Exception {
        if (i == null) {
            throw new Exception("Chave não encontrada: " + chave);
        } else if (chave < i.chave) {
            i.esq = inserirShow(i.esq, chave, titulo);
        } else if (chave > i.chave) {
            i.dir = inserirShow(i.dir, chave, titulo);
        } else {
            i.raizNo2 = inserirNo2(i.raizNo2, titulo);
        }
        return i;
    }

    private No2 inserirNo2(No2 i, String titulo) {
        if (i == null) {
            i = new No2(titulo);
        } else if (titulo.compareTo(i.elemento) < 0) {
            i.esq = inserirNo2(i.esq, titulo);
        } else if (titulo.compareTo(i.elemento) > 0) {
            i.dir = inserirNo2(i.dir, titulo);
        }
        return i;
    }

    public void pesquisar(String titulo) {
        System.out.print(" raiz ");
        boolean encontrado = pesquisarNo1(raiz, titulo);
        System.out.println(encontrado ? "SIM" : "NAO");
    }

    private boolean pesquisarNo1(No1 i, String titulo) {
        if (i == null) {
            return false;
        }

        System.out.print("esq ");
        if (pesquisarNo1(i.esq, titulo)) return true;

        System.out.print("raiz ");
        if (pesquisarNo2(i.raizNo2, titulo)) return true;

        System.out.print("dir ");
        return pesquisarNo1(i.dir, titulo);
    }

    private boolean pesquisarNo2(No2 i, String titulo) {
        if (i == null) return false;

        comparacoes++;
        if (titulo.equals(i.elemento)) {
            return true;
        } else if (titulo.compareTo(i.elemento) < 0) {
            return pesquisarNo2(i.esq, titulo);
        } else {
            return pesquisarNo2(i.dir, titulo);
        }
    }
}

class No2 {
    String elemento;
    No2 esq, dir;

    public No2(String elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
    }
}

class No1 {
    int chave; // releaseYear % 15
    No1 esq, dir;
    No2 raizNo2;

    public No1(int chave) {
        this.chave = chave;
        this.raizNo2 = null;
        this.esq = this.dir = null;
    }
}
