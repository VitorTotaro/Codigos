#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_SHOWS 1368
#define MAX_INPUT 300
#define MAX_LINE 1000

typedef struct
{
    int ano, mes, dia;
} Data;

typedef struct
{
    char show_id[20];
    char type[50];
    char title[100];
    char director[100];
    char country[50];
    char duration[20];
    char date[30];
    char release_year[10];
    char rating[10];
    char listed_in[100];
    char **cast1;
    int cast_size;
    int release;
    Data data;
} Show;

char *tratarCampo(char *campo)
{
    return (strcmp(campo, "") == 0) ? "NaN" : campo;
}

int tratarMes(char *mes)
{
    if (strcmp(mes, "January") == 0)
        return 1;
    if (strcmp(mes, "February") == 0)
        return 2;
    if (strcmp(mes, "March") == 0)
        return 3;
    if (strcmp(mes, "April") == 0)
        return 4;
    if (strcmp(mes, "May") == 0)
        return 5;
    if (strcmp(mes, "June") == 0)
        return 6;
    if (strcmp(mes, "July") == 0)
        return 7;
    if (strcmp(mes, "August") == 0)
        return 8;
    if (strcmp(mes, "September") == 0)
        return 9;
    if (strcmp(mes, "October") == 0)
        return 10;
    if (strcmp(mes, "November") == 0)
        return 11;
    if (strcmp(mes, "December") == 0)
        return 12;
    return 0;
}
char *trim(char *str)
{
    while (isspace((unsigned char)*str))
        str++; // pula espaços iniciais

    if (*str == 0)
        return strdup(""); // string vazia

    char *end = str + strlen(str) - 1;
    while (end > str && isspace((unsigned char)*end))
        end--;
    *(end + 1) = '\0';

    return strdup(str);
}

void imprimirShow(Show s)
{
    printf("=> %s ## %s ## %s ## %s ## [", s.show_id, s.title, s.type, s.director);
    for (int i = 0; i < s.cast_size; i++)
    {
        printf("%s", s.cast1[i]);
        if (i < s.cast_size - 1)
            printf(", ");
    }
    printf("] ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
           s.country, s.date, s.release_year, s.rating, s.duration, s.listed_in);
}

void ordenarCast(char **cast, int n)
{
    for (int i = 0; i < n; i++)
    {
        for (int j = i + 1; j < n; j++)
        {
            if (strcasecmp(cast[j], cast[i]) < 0)
            {
                char *temp = cast[i];
                cast[i] = cast[j];
                cast[j] = temp;
            }
        }
    }
}

Show lerLinha(char *linha)
{
    Show s;
    char *campos[12];
    int c = 0, inAspas = 0;
    campos[c] = malloc(200);
    strcpy(campos[c], "");

    for (int i = 0; linha[i]; i++)
    {
        if (linha[i] == '"' && !inAspas)
            inAspas = 1;
        else if (linha[i] == '"' && inAspas)
            inAspas = 0;
        else if (linha[i] == ',' && !inAspas)
        {
            campos[++c] = malloc(200);
            strcpy(campos[c], "");
        }
        else
        {
            char str[2] = {linha[i], '\0'};
            strcat(campos[c], str);
        }
    }

    strcpy(s.show_id, tratarCampo(campos[0]));
    strcpy(s.type, tratarCampo(campos[1]));
    strcpy(s.title, tratarCampo(campos[2]));
    strcpy(s.director, tratarCampo(campos[3]));
    strcpy(s.country, tratarCampo(campos[5]));
    strcpy(s.date, tratarCampo(campos[6]));
    strcpy(s.release_year, tratarCampo(campos[7]));
    strcpy(s.rating, tratarCampo(campos[8]));
    strcpy(s.duration, tratarCampo(campos[9]));
    strcpy(s.listed_in, tratarCampo(campos[10]));
    s.release = atoi(campos[7]);

    // Cast
    if (strcmp(campos[4], "") == 0)
    {
        s.cast1 = malloc(sizeof(char *));
        s.cast1[0] = strdup("NaN");
        s.cast_size = 1;
    }
    else
    {
        s.cast1 = malloc(20 * sizeof(char *));
        char *token = strtok(campos[4], ",");
        int i = 0;
        while (token != NULL)
        {
            s.cast1[i++] = trim(token);
            token = strtok(NULL, ",");
        }
        s.cast_size = i;
        ordenarCast(s.cast1, s.cast_size);
    }

    // Data
    if (strcmp(s.date, "NaN") == 0)
    {
        s.data.ano = 1900;
        s.data.mes = 3;
        s.data.dia = 1;
    }
    else
    {
        char mes[15], dia[5], ano[5];
        sscanf(s.date, "%s %s %s", mes, dia, ano);
        dia[strlen(dia) - 1] = '\0';
        s.data.dia = atoi(dia);
        s.data.mes = tratarMes(mes);
        s.data.ano = atoi(ano);
    }

    for (int j = 0; j <= c; j++)
        free(campos[j]);
    return s;
}





typedef struct Celula{
    Show elemento;
    struct Celula *prox;
} Celula;

Celula* novaCelula(Show elemento) {
   Celula* nova = (Celula*) malloc(sizeof(Celula));
   nova->elemento = elemento;
   nova->prox = NULL;
   return nova;
}

typedef struct {
    Celula *primeiro, *ultimo;
} FilaC;

void iniciarFila(FilaC *f) {
   Show vazio;
   memset(&vazio, 0, sizeof(Show)); // zera todos os campos
   f->primeiro = novaCelula(vazio); // cria célula cabeça com dado "vazio"
   f->ultimo = f->primeiro;
}


int tamanho(FilaC *f){
    Celula *tmp = f->primeiro;
    int cont = 0;
    while(tmp != f->ultimo){
        tmp = tmp->prox;
        cont ++;
    }
    return cont;
}

Show remover(FilaC *f) {
    if (f->primeiro == f->ultimo) {
        printf("FilaC vazia!!!\n");
        exit(1);
    }

    Celula* tmp = f->primeiro->prox;  // primeira célula real
    Show resp = tmp->elemento;

    f->primeiro->prox = tmp->prox;

    if (tmp == f->ultimo) {
        f->ultimo = f->primeiro;  // fila ficou vazia
    }

    free(tmp);
    return resp;
}


void inserir(FilaC *f, Show s) {
    int cont = tamanho(f);
    if (cont == 5){
        remover(f);
    }
    f->ultimo->prox = novaCelula(s);
    f->ultimo = f->ultimo->prox;
}



void media(FilaC *f){
    int media=0;
    int k=0;
    for(Celula* i = f->primeiro->prox; i != NULL; i = i->prox){
        media = media + i->elemento.release;
        k++;
    }
    
    media = media / k;
    printf("[Media] %d\n", media);
}

void mostrar(FilaC *f) {
    int k = tamanho(f) - 1;

    for(Celula* i = f->primeiro->prox; i != NULL; i = i->prox) {
        printf("[%d] => %s ## %s ## %s ## %s ## [", 
           k, i->elemento.show_id, i->elemento.title, i->elemento.type, i->elemento.director);

        for (int j = 0; j < i->elemento.cast_size; j++) {
            printf("%s", i->elemento.cast1[j]);
            if (j < i->elemento.cast_size - 1) {
                printf(", ");
            }
        }

        printf("] ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
            i->elemento.country, i->elemento.date, i->elemento.release_year,
            i->elemento.rating, i->elemento.duration, i->elemento.listed_in);
        k--;
    }
}

void lerLinhaInput(char* buffer, int max) {
    if (fgets(buffer, max, stdin)) {
        size_t len = strlen(buffer);
        if (len > 0 && buffer[len - 1] == '\n') buffer[len - 1] = '\0';
    }
}



int main() {
    FILE *arquivo = fopen("/tmp/disneyplus.csv", "r");
    if (!arquivo) {
        fprintf(stderr, "Erro ao abrir arquivo!\n");
        return 1;
    }

    Show shows[MAX_SHOWS];
    char linha[MAX_LINE];
    int i = 0;

    // Ignorar cabeçalho
    fgets(linha, MAX_LINE, arquivo);


    while (fgets(linha, MAX_LINE, arquivo) && i < MAX_SHOWS) {
        shows[i++] = lerLinha(linha);
    }

    fclose(arquivo);

    FilaC fila;
    iniciarFila(&fila);

    char entrada[20];
    lerLinhaInput(entrada, sizeof(entrada));

    while (strcmp(entrada, "FIM") != 0) {
        int index = atoi(entrada + 1) - 1;
        inserir(&fila, shows[index]);
        media(&fila);
        lerLinhaInput(entrada, sizeof(entrada));
    }

    int numComandos;
    scanf("%d", &numComandos);
    getchar(); // limpar o \n

    for (int i = 0; i < numComandos; i++) {
        char comando[10];
        scanf("%s", comando);

        if (strcmp(comando, "I") == 0) {
            scanf("%s", entrada);
            int idx = atoi(entrada + 1) - 1;
            inserir(&fila, shows[idx]);
            media(&fila);

        } else if (strcmp(comando, "R") == 0) {
            Show r = remover(&fila);
            printf("(R) %s\n", r.title);
        }
    }

    mostrar(&fila);


    return 0;
}