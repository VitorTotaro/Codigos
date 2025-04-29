public class ordenarProva {
    public void ordenar(int[] array){
        int n = array.length();
        int menor =array[0];
        int maior = array[0];

        for(int i = 0; i<n; i++){
            for(int j=0; j<n; j++){
                if(array[j] > maior){
                    maior = array[j];
                }else if(array[j] < menor){
                    menor = array[j];
                }
            }
            array[i] = menor;
            array[n-i] = maior;
        }
        
    }
}
