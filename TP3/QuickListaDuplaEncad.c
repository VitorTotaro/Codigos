#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

int comparacoes = 0;

#define MAX_SHOWS 1368
#define MAX_LINE 1000

typedef struct {
    int ano, mes, dia;
} Data;

typedef struct {
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

typedef struct Celula {
    Show elemento;
    struct Celula *ant, *prox;
} Celula;

Celula* novaCelula(Show s) {
    Celula* nova = (Celula*) malloc(sizeof(Celula));
    nova->elemento = s;
    nova->ant = nova->prox = NULL;
    return nova;
}

char* tratarCampo(char* campo) {
    return (strcmp(campo, "") == 0) ? "NaN" : campo;
}

int tratarMes(char* mes) {
    const char* meses[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    for (int i = 0; i < 12; i++) if (strcmp(mes, meses[i]) == 0) return i + 1;
    return 0;
}

char* trim(char* str) {
    while (isspace((unsigned char)*str)) str++;
    if (*str == 0) return strdup("");
    char* end = str + strlen(str) - 1;
    while (end > str && isspace((unsigned char)*end)) end--;
    *(end + 1) = '\0';
    return strdup(str);
}

void imprimirShow(Show s) {
    printf("=> %s ## %s ## %s ## %s ## [", s.show_id, s.title, s.type, s.director);
    for (int i = 0; i < s.cast_size; i++) {
        printf("%s", s.cast1[i]);
        if (i < s.cast_size - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
           s.country, s.date, s.release_year, s.rating, s.duration, s.listed_in);
}

void ordenarCast(char** cast, int n) {
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (strcasecmp(cast[j], cast[i]) < 0) {
                char* temp = cast[i];
                cast[i] = cast[j];
                cast[j] = temp;
            }
        }
    }
}

Show lerLinha(char* linha) {
    Show s;
    char* campos[12];
    int c = 0, inAspas = 0;
    campos[c] = malloc(200); strcpy(campos[c], "");

    for (int i = 0; linha[i]; i++) {
        if (linha[i] == '"') inAspas = !inAspas;
        else if (linha[i] == ',' && !inAspas) {
            campos[++c] = malloc(200); strcpy(campos[c], "");
        } else {
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

    if (strcmp(campos[4], "") == 0) {
        s.cast1 = malloc(sizeof(char*));
        s.cast1[0] = strdup("NaN");
        s.cast_size = 1;
    } else {
        s.cast1 = malloc(20 * sizeof(char*));
        char* token = strtok(campos[4], ",");
        int i = 0;
        while (token != NULL) {
            s.cast1[i++] = trim(token);
            token = strtok(NULL, ",");
        }
        s.cast_size = i;
        ordenarCast(s.cast1, s.cast_size);
    }

    if (strcmp(s.date, "NaN") == 0) {
        s.data.ano = 1900; s.data.mes = 3; s.data.dia = 1;
    } else {
        char mes[15], dia[5], ano[5];
        sscanf(s.date, "%s %s %s", mes, dia, ano);
        dia[strlen(dia) - 1] = '\0';
        s.data.dia = atoi(dia);
        s.data.mes = tratarMes(mes);
        s.data.ano = atoi(ano);
    }

    for (int j = 0; j <= c; j++) free(campos[j]);
    return s;
}

void swapCelulas(Celula* a, Celula* b) {
    Show temp = a->elemento;
    a->elemento = b->elemento;
    b->elemento = temp;
}

Celula* particao(Celula* esq, Celula* dir) {
    Show pivo = dir->elemento;
    Celula* i = esq->ant;

    for (Celula* j = esq; j != dir; j = j->prox) {
        comparacoes++;
        if (j->elemento.data.ano < pivo.data.ano ||
            (j->elemento.data.ano == pivo.data.ano && j->elemento.data.mes < pivo.data.mes) ||
            (j->elemento.data.ano == pivo.data.ano && j->elemento.data.mes == pivo.data.mes && j->elemento.data.dia < pivo.data.dia) ||
            (j->elemento.data.ano == pivo.data.ano && j->elemento.data.mes == pivo.data.mes && j->elemento.data.dia == pivo.data.dia &&
             strcmp(j->elemento.title, pivo.title) < 0)) {
            i = (i == NULL) ? esq : i->prox;
            swapCelulas(i, j);
        }
    }
    i = (i == NULL) ? esq : i->prox;
    swapCelulas(i, dir);
    return i;
}

void quicksort(Celula* esq, Celula* dir) {
    if (dir != NULL && esq != dir && esq != dir->prox) {
        Celula* p = particao(esq, dir);
        quicksort(esq, p->ant);
        quicksort(p->prox, dir);
    }
}

int main() {
    FILE* arq = fopen("/tmp/disneyplus.csv", "r");
    if (!arq) {
        printf("Erro ao abrir arquivo.\n");
        return 1;
    }

    Show todos[MAX_SHOWS];
    char linha[MAX_LINE];
    int count = 0;
    fgets(linha, MAX_LINE, arq);
    while (fgets(linha, MAX_LINE, arq) && count < MAX_SHOWS) {
        linha[strcspn(linha, "\n")] = '\0';
        todos[count++] = lerLinha(linha);
    }
    fclose(arq);

    Celula* inicio = NULL;
    Celula* fim = NULL;
    char entrada[10];
    while (1) {
        fgets(entrada, sizeof(entrada), stdin);
        entrada[strcspn(entrada, "\n")] = '\0';
        if (strcmp(entrada, "FIM") == 0) break;
        int idx = atoi(entrada + 1) - 1;
        Celula* nova = novaCelula(todos[idx]);
        if (!inicio) inicio = fim = nova;
        else {
            fim->prox = nova;
            nova->ant = fim;
            fim = nova;
        }
    }

    clock_t inicio_tempo = clock();
    quicksort(inicio, fim);
    clock_t fim_tempo = clock();

    double tempo = (double)(fim_tempo - inicio_tempo) / CLOCKS_PER_SEC;
    FILE *log = fopen("872284_quicsort.txt", "w");
    if (log != NULL) {
        fprintf(log, "Matricula: 872284\tTempo: %.6lf s\tComparacoes: %d\n", tempo, comparacoes);
        fclose(log);
    }

    for (Celula* c = inicio; c != NULL; c = c->prox) imprimirShow(c->elemento);
    return 0;
}
