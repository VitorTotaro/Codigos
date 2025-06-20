

class PilhaFlexivel{
    Celula topo;
    int n;


    class Celula{
        Show elem;
        Celula prox;

        public Celula(Show elemento) {
            this.elem = elemento;
            this.prox = null;
	    }
    }
    
    public PilhaFlexivel(){
        topo = null;
        n=0;
    }

    void empilhar(Show show){

        Celula tmp = new Celula(show);
        tmp.prox = topo;
        topo = tmp;
        tmp = null;
        n++;
    }

    Show remover()throws Exception{
        //validar remoção
      if(topo == null){
        throw new Exception("Lista vazia!!!");
      }

      Show resp = topo.elem;
      Celula tmp = topo;
      topo = topo.prox;
      tmp.prox = null;
      tmp = null;
      n--;
      
      return resp;
      
    }

     void mostrar(){
        int k = n - 1;
        for(Celula i = topo; i!= null; i=i.prox){
            System.out.printf("[%d] ",k);
            System.out.printf("=> %s ## %s ## %s ## %s ##", i.elem.show_id, i.elem.title, i.elem.type, i.elem.director);

            // Cast
            System.out.print(" [");

            for (int j = 0; j < i.elem.cast1.length; j++) {
                System.out.print(i.elem.cast1[j].trim());
                if (j < i.elem.cast1.length - 1)
                    System.out.print(", ");
            }

            
            System.out.print("]");

            // Restante
            System.out.printf(" ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
                i.elem.country, i.elem.date, i.elem.release_year, i.elem.rating, i.elem.duration, i.elem.listed_in);
            k--;
        }
    }
}