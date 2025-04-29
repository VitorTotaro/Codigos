class PilhaLinear{
    private int array[];
    private int n;

    public PilhaLinear(int max){
        array = new int[max];
        n=0;
    }
    public void empilhar(int elem) throws Exception{
        if(n == array.length)
            throw new Exception("==========Pilha cheia!=========");

        array[n] = elem;
        n++;
    }
    public int desempilhar() throws Exception{
        if (n == 0)
            throw new Exception("=========Pilha vazia!=========");

        int resp = array[n-1];
        n--;

        return resp;
    }

    public void mostrar(){

        for(int i=n; i>0; i--){
            System.out.printf("%d ", array[i-1]);
        }
        System.out.println();
    }

    public boolean pesquisar(int elem){
        boolean resp = false;

        for(int i=n-1; i>=0; i--){
            if(array[i] == elem)
                resp = true;
                i=0;
        }
        return resp;
    }
}