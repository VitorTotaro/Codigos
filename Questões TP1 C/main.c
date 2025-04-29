/*
Palındromo - Crie um metodo iterativo que recebe uma string como parametro e retorna true
se essa ´e um “Palındromo”. Na saıda padrao, para cada linha de entrada, escreva uma linha
de saıda com SIM/NAO indicando se a linha ´e um palındromo. Destaca-se que uma linha de ˜
entrada pode ter caracteres nao letras.
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int isPalindromo(char frase[])
{
    int resp = 0;
    int n = strlen(frase);
    int j=n-1;

    for(int i=0; i<n/2; i++) {
       if (frase[i]!=frase[j]){
         resp=1;
         i=n;
       }
       else{
        j--;
       }
    }

    return resp;
}


int main()
{

    char frase[1000];

    scanf(" %[^\n]", frase);   //palavra será digitada


    while(frase[0]!= 'F' || frase[1]!= 'I' || frase[2]!= 'M' || frase[3]!='\0') {

        if(isPalindromo(frase)==0){                //continua recebendo e dizendo se é palíndromo enquanto a palavra não for FIM,
            printf("SIM\n");
        }
        else
            printf("NAO\n");
        scanf(" %[^\n]", frase);
    }
    printf("\n");

    return 0;
}
