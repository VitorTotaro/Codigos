class Lista {
    private static Show[] array;
    private static int n;

    public Lista(int tamanho){
        array = new Show[tamanho];
        n=0;
    }

    void inserirInicio(Show show)throws Exception{
        //validar insercao
      if(n >= array.length){
         throw new Exception("Erro ao inserir!");
      } 

      //levar elementos para o fim do array
      for(int i = n; i > 0; i--){
         array[i] = array[i-1];
      }

      array[0] = show;
      n++;
    }

    void inserir(Show show, int pos)throws Exception{
        //validar insercao
      if(n >= array.length || pos < 0 || pos > n){
         throw new Exception("Erro ao inserir!");
      } 

      //arrastar os elementos a partir de pos
      for(int i = n; i > pos; i--){
         array[i] = array[i-1];
      }

      array[pos] = show;
      n++;
    }

    void inserirFim(Show show)throws Exception{
        //validar insercao
      if(n >= array.length){
         throw new Exception("Erro ao inserir!");
      } 

      array[n] = show;
      n++;
    }


    Show removerInicio()throws Exception{
        //validar remoção
      if (n==0){
        throw new Exception("Lista vazia!!!");
      }

      Show resp = array[0];

      for(int i = 0; i < n-1; i++){
        array[i] = array[i+1];
      }

      n--;
      return resp;

    }

    Show remover(int pos)throws Exception{
        //validar remoção
         if(n==0 || pos > n || pos < 0){
          throw new Exception("Erro ao remover!!!");
         }
         Show resp = array[pos];
         for (int i = pos; i < n - 1; i++) {
            array[i] = array[i + 1];
         }
         n--;

    return resp;

    }

    Show removerFim()throws Exception{
        //validar remoção
      if(n==0){
        throw new Exception("Lista vazia!!!");
      }

      return array[n--];
    }

    void mostrar(){
        for(int i = 0; i<n; i++){
            System.out.printf("=> %s ## %s ## %s ## %s ##", array[i].show_id, array[i].title, array[i].type, array[i].director);

            // Cast
            System.out.print(" [");

            for (int j = 0; j < array[i].cast1.length; j++) {
                System.out.print(array[i].cast1[j].trim());
                if (j < array[i].cast1.length - 1)
                    System.out.print(", ");
            }

            
            System.out.print("]");

            // Restante
            System.out.printf(" ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
                array[i].country, array[i].date, array[i].release_year, array[i].rating, array[i].duration, array[i].listed_in);
        }
    }
}


