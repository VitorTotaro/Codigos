public class No {
    Show elemento;
    No dir, esq;

    public No(Show elemento) {
        this(elemento, null, null);
    }

    public No(Show elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
}
}
