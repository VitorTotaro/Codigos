import java.util.Scanner;

class Lista {
    private static int[] array;
    private static int n;

    public void listaLinear(int max) {
        array = new int[max]; // criando array do tamanho desejado para a lista
        n = 0; // inicializando a lista como vazia
    }

    public void inserirInicio(int elem) throws Exception {
        if (n == array.length)
            throw new Exception("=========Lista cheia!=========");

        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1]; // deslocando os elementos para que o início fique liberado
        }
        array[0] = elem;
        n++; // incrementando n (lista com um elemento a mais)
    }

    public void inserirFim(int elem) throws Exception {
        if (n == array.length)
            throw new Exception("=========Lista cheia!=========");

        array[n] = elem;
        n++;
    }

    public void inserir(int elem, int pos) throws Exception {
        if (n == array.length) {
            throw new Exception("=========Lista cheia!=========");
        } else if (pos > n) {
            throw new Exception("=========Posição Inválida!=========");
        }

        for (int i = n; i > pos; i--) {
            array[i] = array[i - 1];
        }
        array[pos] = elem;
        n++;
    }

    public int removerInicio() throws Exception {
        if (n == 0)
            throw new Exception("=========Lista vazia!=========");

        int resp = array[0];

        for (int i = 0; i < n; i++) {
            array[i] = array[i + 1];
        }
        n--;
        return resp;
    }

    public int removerFim() throws Exception {
        if (n == 0)
            throw new Exception("=========Lista vazia!=========");

        int resp = array[--n]; // guarda o valor removido para ser retornado e já decresce o n

        return resp;
    }

    public int remover(int pos) throws Exception {
        if (n == 0) {
            throw new Exception("=========Lista cheia!=========");
        } else if (pos > n) {
            throw new Exception("=========Posição Inválida!=========");
        }

        int resp = array[pos];

        for (int i = pos; i < n; i++) {
            array[i] = array[i + 1];
        }

        n--;
        return resp;
    }

    public void mostrar() {
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            System.out.printf("%d ", array[i]);
        }
        System.out.print("]");
    }

    public boolean pesquisar(int elem) {
        boolean resp = false;

        for (int i = 0; i < n; i++) {
            if (array[i] == elem)
                resp = true;
        }
        return resp;
    }




    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Lista lista = new Lista();

        System.out.println("qual o tamanho da lista?");
        int a = sc.nextInt();
        lista.listaLinear(a);

        int elemento, pos;

        String linha = sc.next();

        while (!linha.equals("FIM")) {
            if (linha.equals("II")) { // inserir inicio
                sc.skip(" ");
                lista.inserirInicio(sc.nextInt());

            } else if (linha.equals("IF")) { // inserir fim
                sc.skip(" ");
                lista.inserirFim(sc.nextInt());

            } else if (linha.equals("M")) {
                lista.mostrar();
                System.out.println(); // Adiciona quebra de linha após mostrar

            } else if (linha.equals("pesquisar")) { // pesquisar
                sc.skip(" ");
                if(lista.pesquisar(sc.nextInt())){
                    System.out.println("SIM");
                }else{
                    System.out.println("NAO");
                }


            } else if (linha.equals("inserir")) {
                System.out.printf("Elemento e posição(respectivamente):");
                elemento = sc.nextInt();
                sc.skip(" ");

                pos = sc.nextInt();
                lista.inserir(elemento, pos);

            } else if (linha.equals("remover")) {
                System.out.printf("posição:");
                System.out.printf("%d\n", lista.remover(sc.nextInt()));

            } else if (linha.equals("RF")) {// remover fim

                System.out.printf("%d\n", lista.removerFim());

            } else if (linha.equals("RI")) { // remover inicio

                System.out.printf("%d\n", lista.removerInicio());
            } else if(linha.equals("inverter")){
                inverterLista();
            }

            linha = sc.next();
        }
        sc.close();
    }






















/*public static void inverterLista(int i, int j){
    if(i == j)
        return;

    int a = array[i];

    array[i] = array[j-1];
    array[j-1] = a;

    inverterLista(i + 1, j + 1);

}
}*/

public static void inverterLista(){
    int a; int b = n;

    for(int i = 0; i<b; i++){
        a = array[i];
        array[i] = array[b-1];
        array[b-1] = a;

        b--;
    }
}
}