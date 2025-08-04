#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>
long comparacoes = 0;


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




typedef struct No
{
    Show elemento;
    struct No *esq;
    struct No *dir;
    int nivel;
} No;

int max(int a, int b)
{
    return (a > b) ? a : b;
}

int getNivel(No *no)
{
    return (no == NULL) ? 0 : no->nivel;
}

void setNivel(No *no)
{
    if (no != NULL)
        no->nivel = 1 + max(getNivel(no->esq), getNivel(no->dir));
}

No *rotacionarDir(No *no)
{
    No *noEsq = no->esq;
    No *noEsqDir = noEsq->dir;

    noEsq->dir = no;
    no->esq = noEsqDir;

    setNivel(no);
    setNivel(noEsq);

    return noEsq;
}

No *rotacionarEsq(No *no)
{
    No *noDir = no->dir;
    No *noDirEsq = noDir->esq;

    noDir->esq = no;
    no->dir = noDirEsq;

    setNivel(no);
    setNivel(noDir);

    return noDir;
}

No *balancear(No *no)
{
    if (no != NULL)
    {
        int fator = getNivel(no->dir) - getNivel(no->esq);

        if (abs(fator) <= 1)
        {
            setNivel(no);
        }
        else if (fator == 2)
        {
            int fatorFilhoDir = getNivel(no->dir->dir) - getNivel(no->dir->esq);
            if (fatorFilhoDir == -1)
            {
                no->dir = rotacionarDir(no->dir);
            }
            no = rotacionarEsq(no);
        }
        else if (fator == -2)
        {
            int fatorFilhoEsq = getNivel(no->esq->dir) - getNivel(no->esq->esq);
            if (fatorFilhoEsq == 1)
            {
                no->esq = rotacionarEsq(no->esq);
            }
            no = rotacionarDir(no);
        }
        else
        {
            printf("Erro: fator de balanceamento invalido em '%s'\n", no->elemento.title);
        }
    }
    return no;
}

No *novoNo(Show x)
{
    No *novo = (No *)malloc(sizeof(No));
    novo->elemento = x;
    novo->esq = novo->dir = NULL;
    novo->nivel = 1;
    return novo;
}

No *inserir(No *raiz, Show x)
{
    if (raiz == NULL)
    {
        raiz = novoNo(x);
    }
    else
    {
        int cmp = strcmp(x.title, raiz->elemento.title);
        if (cmp < 0)
        {
            raiz->esq = inserir(raiz->esq, x);
        }
        else if (cmp > 0)
        {
            raiz->dir = inserir(raiz->dir, x);
        }
        else
        {
            printf("Erro: elemento repetido: %s\n", x.title);
        }
    }
    return balancear(raiz);
}

int pesquisar(No* raiz, const char* title) {
    comparacoes++;
    if (raiz == NULL) {
        return 0;
    }

    int cmp = strcmp(title, raiz->elemento.title);
    if (cmp == 0){
        return 1;
    } 
    else if (cmp < 0) {
        printf("esq ");
        return pesquisar(raiz->esq, title);
    }else {
        printf("dir ");
        return pesquisar(raiz->dir, title);
    }
}

int main() {
    FILE* arq = fopen("/tmp/disneyplus.csv", "r");
    if (arq == NULL) {
        perror("Erro ao abrir o arquivo");
        return 1;
    }

    Show shows[MAX_SHOWS];
    char linha[1000];
    fgets(linha, sizeof(linha), arq); // pular cabeçalho

    for (int i = 0; i < MAX_SHOWS; i++) {
        fgets(linha, sizeof(linha), arq);
        shows[i] = lerLinha(linha);
    }
    fclose(arq);

    No* raiz = NULL;
    char entrada[100];

    // Inserções
    while (1) {
        scanf("%s", entrada);
        if (strcmp(entrada, "FIM") == 0) break;

        int index = atoi(entrada + 1) - 1;
        raiz = inserir(raiz, shows[index]);
    }

    // Pesquisa
    clock_t inicio = clock();

    while (1) {
        scanf(" %[^\n]", entrada); // ler linha inteira
        if (strcmp(entrada, "FIM") == 0) break;

        printf("raiz ");
        printf(pesquisar(raiz, entrada) ? "SIM\n" : "NAO\n");
    }

    clock_t fim = clock();
    double tempoExecucao = ((double)(fim - inicio) / CLOCKS_PER_SEC) * 1000; // em ms

    // Gerar log
    FILE* log = fopen("872284_avl.txt", "w");
    if (log != NULL) {
        fprintf(log, "872284\t%.0f\t%ld\n", tempoExecucao, comparacoes);
        fclose(log);
    }

    return 0;
}


