public class ArvoreBinaria{
    No raiz;
    public int comparacoes = 0;

    public ArvoreBinaria(){
        raiz = null;
    }

    public void pesquisar(String x) {
        System.out.print("=>raiz  ");
        boolean resp = pesquisar(x, raiz);
		if(resp == false)
            System.out.println("NAO");
        else
            System.out.println("SIM");

	}

	private boolean pesquisar(String x, No i) {
      boolean resp;

		if (i == null) {
            resp = false;

       }else{
            comparacoes++; // uma chamada real de compareTo
            int cmp = x.compareTo(i.elemento.title);

            if (cmp == 0) {
                resp = true;
            } else if (cmp < 0) {
                System.out.print("esq ");
                resp = pesquisar(x, i.esq);
            } else {
                System.out.print("dir ");
                resp = pesquisar(x, i.dir);
        }
    }
    return resp;
}


   public void inserir(Show x) throws Exception {
		raiz = inserir(x, raiz);
	}

	private No inserir(Show x, No i) throws Exception {
		if (i == null) {
         i = new No(x);

      } else if (x.title.compareTo(i.elemento.title) < 0) {
         i.esq = inserir(x, i.esq);

      } else if (x.title.compareTo(i.elemento.title) > 0) {
         i.dir = inserir(x, i.dir);

      } else {
         throw new Exception("Erro ao inserir!");
      }

		return i;
	}

    public void caminharPre() {
		System.out.print("[ ");
		caminharPre(raiz);
		System.out.println("]");
	}

	private void caminharPre(No i) {
		if (i != null) {
			System.out.println(i.elemento.title); // Conteudo do no.
			caminharPre(i.esq); // Elementos da esquerda.
			caminharPre(i.dir); // Elementos da direita.
		}
	}
}