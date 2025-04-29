#include <stdio.h>
#include <stdlib.h>
int getTamanho(char palavra[]){
    int i=0;
    while(palavra[i] != '\0')
        i++;
   
    return i;
}

void combinar(char palavra1[], char palavra2[]){
    char combinacao[150];
    int k=0;
    int tam1 = getTamanho(palavra1);
    int tam2 = getTamanho(palavra2);
    int tamMax = tam1 > tam2 ? tam1:tam2;

    for(int i=0; i<=tamMax; i++){
        if(palavra1[i] != '\0'){
            combinacao[k] = palavra1[i];
            k++;
        }
        if(palavra2[i] != '\0'){
            combinacao[k] = palavra2[i];
            k++;
        }
    }
    printf("%s", combinacao);
}

int main(){
    char palavra1[100];
    char palavra2[100];

    while (scanf(" %s %s",palavra1, palavra2) == 2){
        combinar(palavra1, palavra2);
        printf("\n");
    }
    
}