#include <stdio.h>
#include <stdlib.h>

typedef struct{
    int dia;
    int mes;
    int ano;
} Data;

typedef struct{
    int hora;
    int minutos;
} Duracao;

typedef struct{
    int id;
    double preco;
    Data data;
    Duracao tempo;
    char destino[100];
} Passagem;

void imprimirPassagem(){

    Passagem principal;
    int n;

    scanf("%d", &n);
    
    for(int i=0; i<n; i++){
        scanf("%d %lf %d-%d-%d %d:%d %99[^\n]",
            &principal.id, &principal.preco, &principal.data.ano, 
            &principal.data.mes, &principal.data.dia, &principal.tempo.hora, 
            &principal.tempo.minutos, &principal.destino);
    

    printf("%d; %d/%02d/%02d; %02d:%02d; %s; %lf\n",principal.id, principal.data.ano, 
        principal.data.mes, principal.data.dia, principal.tempo.hora, 
        principal.tempo.minutos, principal.destino, principal.preco);
    }
}

int main(){
    imprimirPassagem();
return 0;
}