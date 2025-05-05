#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

int comparacoes = 0; // contador global de comparações

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

void swap(Show* a, Show* b){
    Show temp = *a;
    *a = *b;
    *b = temp;
}

void quicksort(Show *array, int esq, int dir) {
    int i = esq, j = dir;
    Data pivo = array[(esq + dir) / 2].data;
    char tituloPivo[150];
    strcpy(tituloPivo, array[(esq + dir) / 2].title);

    while (i <= j) {
        while (
            array[i].data.ano < pivo.ano ||
            (array[i].data.ano == pivo.ano && array[i].data.mes < pivo.mes) ||
            (array[i].data.ano == pivo.ano && array[i].data.mes == pivo.mes && array[i].data.dia < pivo.dia) ||
            (array[i].data.ano == pivo.ano && array[i].data.mes == pivo.mes && array[i].data.dia == pivo.dia &&
            strcmp(array[i].title, tituloPivo) < 0)
        ){
            i++;
            comparacoes++;;
        }

        while (
            array[j].data.ano > pivo.ano ||
            (array[j].data.ano == pivo.ano && array[j].data.mes > pivo.mes) ||
            (array[j].data.ano == pivo.ano && array[j].data.mes == pivo.mes && array[j].data.dia > pivo.dia) ||
            (array[j].data.ano == pivo.ano && array[j].data.mes == pivo.mes && array[j].data.dia == pivo.dia &&
            strcmp(array[j].title, tituloPivo) > 0) 
        ){
            j--;
            comparacoes++;
        }

        if (i <= j) {
            swap(&array[i], &array[j]);
            i++;
            j--;
        }
    }

    if (esq < j) quicksort(array, esq, j);
    if (i < dir) quicksort(array, i, dir);
}
int main(){
        FILE* arq = fopen("/tmp/disneyplus.csv", "r");
        if (!arq) {
            printf("Erro ao abrir arquivo.\n");
            return 1;
        }
    
        Show todos[MAX_SHOWS];
        char linha[MAX_LINE];
        int count = 0;
        fgets(linha, MAX_LINE, arq); // pula cabeçalho
    
        while (fgets(linha, MAX_LINE, arq) && count < MAX_SHOWS) {
            linha[strcspn(linha, "\n")] = '\0';
            todos[count++] = lerLinha(linha);
        }
    
        fclose(arq);
    
        // Entrada do usuário
        Show selecionados[MAX_INPUT];
        int n = 0;
        char entrada[10];
        while (1) {
            fgets(entrada, sizeof(entrada), stdin);
            entrada[strcspn(entrada, "\n")] = '\0';
            if (strcmp(entrada, "FIM") == 0) break;
            int idx = atoi(entrada + 1) - 1;
            selecionados[n++] = todos[idx];
        }
    
        
        clock_t inicio = clock();
        quicksort(selecionados, 0, n-1);
        clock_t fim = clock();
    
        double tempo = (double)(fim - inicio) / CLOCKS_PER_SEC;
    
        FILE *log = fopen("872284_quicsort.txt", "w");
        if (log != NULL) {
            fprintf(log, "Matricula: 872284\t");
            fprintf(log, "Tempo: %.6lf s\t", tempo);
            fprintf(log, "Comparacoes: %d\n", comparacoes);
            fclose(log);
        }
        for (int i = 0; i < n; i++) {
            imprimirShow(selecionados[i]);
        }
        return 0;
    }