#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct
{
    char nome[51];
    int peso
} Atleta;

void ordenar(Atleta *atletas[100], int n)
{
    for (int i = 0; i < n -1; i++){
        int maior = i;
    
        for (int j = i + 1; j < n; j++){
            if (atletas[maior]->peso < atletas[j]->peso || 
            (atletas[maior]->peso == atletas[j]->peso && strcmp(atletas[j]->nome, atletas[maior]->nome) < 0)){
                maior = j;
            }
        }
        if(maior != i){
            Atleta* tmp = atletas[i];
            atletas[i] = atletas[maior];
            atletas[maior] = tmp;
        }
    }
}

Atleta *newAtleta(char *nome, int peso)
{
    Atleta *atleta = malloc(sizeof(Atleta));
    strcpy(atleta->nome, nome);
    atleta->peso = peso;
    return atleta;
}
int main()
{
    Atleta *atletas[100];
    int i, n = 0;
    int peso;
    char nome[50];
    while (scanf(" %s %d", nome, &peso) != EOF)
    {
        atletas[i] = newAtleta(nome, peso);
        i++;
        n++;
    }

    ordenar(atletas, n);

    for(int i = 0; i<n; i++){
        printf(" %s %d\n", atletas[i]->nome, atletas[i] ->peso);
    }

    
    for(int i = 0; i<n; i++){
        free(atletas[i]);
    }
}

