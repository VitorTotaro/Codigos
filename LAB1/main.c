#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int contadorMaiuscula (char frase[])
{
    int n=strlen (frase);
    int cont=0;
    for(int i=0; i<n; i++)
    {
        if(frase[i]>= 'A' && frase[i]<= 'Z')
            cont++;
    }
    return cont;
}

int main()
{
    int n[50];
    int contador=0;
    char frase[50];
    printf("Escreva as frases: \n");
    scanf(" %[^\n]", frase);
    //printf("%d\n", contadorMaiuscula(frase));


    while(frase[0]!='F' || frase[1]!='I' || frase[2]!='M' || frase[3]!='\0')
    {

        //printf("Escreva a frase: \n");

        //printf("%d\n", contadorMaiuscula(frase));
        n[contador]=contadorMaiuscula(frase);
        contador++;
        scanf(" %[^\n]", frase);

    }

    for(int i=0; i<contador; i++)
    {
        printf("%d\n",n[i]);
    }
    return 0;
}
