#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int verificarPalindromo(char frase[], int i, int j) {
    if (i >= j) {
        return 0; // É um palíndromo
    }
    if (frase[i] != frase[j]) {
        return 1; // Não é um palíndromo
    }
    return verificarPalindromo(frase, i + 1, j - 1);
}

int isPalindromo(char frase[]) {
    return verificarPalindromo(frase, 0, strlen(frase) - 1);
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
