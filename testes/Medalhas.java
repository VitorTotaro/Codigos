import java.util.Scanner;
class Medalhas{
    public static class Pais{
        public String nome;
        public int ouro, prata, bronze;
    
    
    public Pais(String nome, int ouro, int prata, int bronze){
        this.nome = nome;
        this.ouro = ouro;
        this.prata = prata;
        this.bronze = bronze;
    }
    public void mostrar(){
        System.out.printf("Pais: " + nome + " ");
        System.out.println(ouro +" " + prata +" " + bronze);

    }
}

public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();

    Pais [] nacoes = new Pais[n];

    for(int i = 0; i<n; i++){
    Pais pais = new Pais(sc.next(), sc.nextInt(), sc.nextInt(),sc.nextInt());
    nacoes[i] = pais;
}
ordenar(nacoes);

for (Pais p : nacoes) {
    p.mostrar();
}

sc.close();
}

public static void ordenar(Pais[] nacoes){
    int n = nacoes.length;
    
    
    for(int i = 1; i<n; i++){
        Pais temp = nacoes[i];
        int j = i-1;

        while((j>=0) &&  (nacoes[j].ouro < temp.ouro || 
        (nacoes[j].ouro == temp.ouro && nacoes[j].prata < temp.prata) || 
        (nacoes[j].ouro == temp.ouro && nacoes[j].prata == temp.prata && nacoes[j].bronze < temp.bronze) || 
        (nacoes[j].ouro == temp.ouro && nacoes[j].prata == temp.prata && nacoes[j].bronze == temp.bronze && nacoes[j].nome.compareTo(temp.nome) > 0))){
                nacoes[j+1] = nacoes[j];
                j--;
            }
            nacoes[j+1] = temp;
        }
    }
}

