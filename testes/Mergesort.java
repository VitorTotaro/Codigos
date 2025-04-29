import java.util.Arrays;

public class Mergesort {
    /*void mergesort(int esq, int dir){
        if(esq < dir){
            int meio = (esq + dir) / 2;
            mergesort(esq, meio);
            mergesort(meio + 1, dir);
            intercalar(esq, meio, dir);
        }
    }*/
    
    //intercalar em ordem dois arrays previamente ordenados
    static void intercalar(int[] a1, int[]a2, int []result){
            int n1 = a1.length; int n2 = a2.length;
            int i = 0, j = 0, k = 0;
            while(i < n1 && j < n2){
                if(a1[i] <= a2[j]){
                    result[k++] = a1[i++];
                }else{
                    result [k++] = a2[j++];
                }
            }
            //inserir elementos que podem ter sobrado de algum array;
            while(i < n1){
                result[k++] = a1[i++];
            }
    
            while(j < n2){
                result[k++] = a2[j++];
            }
        }
        public static void main(String[] args){
            int[] array1 = {1,3,5,7,9,11,15};
            int[] array2 = {2,4,6,8,10,15,16,22};
            int[] ordenado = new int[array1.length + array2.length];
    
            System.out.println("Array 1: " + Arrays.toString(array1));
            System.out.println("Array 2: " + Arrays.toString(array2));
    
            intercalar(array1, array2, ordenado);
        System.out.println("Array intercalado: " + Arrays.toString(ordenado));
        

    }
}