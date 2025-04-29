class Paciente {
    public String nome;
    public int prioridade;
}

class FilaPrioridade {

    private Paciente[] itens;
    private int n;

    public FilaPrioridade(int tam) {
        Paciente itens[] = new Paciente[tam];
        n = 0;
    }

    public void inserir(Paciente p) throws Exception {
        if (n == itens.length)
            throw new Exception();
        int a = n; // variável que irá guardar o índice onde "p" será armazenado, se p não tiver prioridade maior que nenhum paciente irá para o fim da fila

        for (int i = 0; i < n; i++) {
            if (itens[i].prioridade < p.prioridade) {
                a = i; // indice onde "p" será armazenado
                i = n; // para de iterar
            }
        }

        for (int i = n; i > a; i--) {
            itens[i] = itens[i - 1]; // arrastando elementos
        }

        itens[a] = p; // colocando paciente p na fila
        n++;
    }

    public Paciente remover() throws Exception{
        if (n == 0)
            throw new Exception("Fila vazia!!");

        Paciente resp = itens[0]; ///paciente a ser removido

        for(int i=0; i<n; i++){
            itens[i] = itens[i+1];
        }

        n--;
        return resp;
    }
}