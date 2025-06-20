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






#define MAX_LISTA 300

typedef struct Celula {
    Show elemento;
    struct Celula *prox;
} Celula;

typedef struct {
    Celula *primeiro;
    int tamanho;
} Lista;


void iniciarLista(Lista *l) {
    l->primeiro = NULL;
    l->tamanho = 0;
}

void inserirInicio(Lista *l, Show s) {
    Celula *nova = (Celula*)malloc(sizeof(Celula));
    nova->elemento = s;
    nova->prox = l->primeiro;
    l->primeiro = nova;
    l->tamanho++;
}


void inserirFim(Lista *l, Show s) {
    Celula *nova = (Celula*)malloc(sizeof(Celula));
    nova->elemento = s;
    nova->prox = NULL;

    if (l->primeiro == NULL) {
        l->primeiro = nova;
    } else {
        Celula *atual = l->primeiro;
        while (atual->prox != NULL) {
            atual = atual->prox;
        }
        atual->prox = nova;
    }
    l->tamanho++;
}

void inserir(Lista *l, Show s, int pos) {
    if (pos < 0 || pos > l->tamanho) {
        printf("Erro ao inserir!\n");
        return;
    }

    if (pos == 0) {
        inserirInicio(l, s);
        return;
    }

    Celula *nova = (Celula*)malloc(sizeof(Celula));
    nova->elemento = s;

    Celula *atual = l->primeiro;
    for (int i = 0; i < pos - 1; i++) {
        atual = atual->prox;
    }

    nova->prox = atual->prox;
    atual->prox = nova;
    l->tamanho++;
}

Show removerInicio(Lista *l) {
    if (l->primeiro == NULL) {
        printf("Lista vazia!!!\n");
        exit(1);
    }

    Celula *remover = l->primeiro;
    Show s = remover->elemento;
    l->primeiro = remover->prox;
    free(remover);
    l->tamanho--;
    return s;
}


Show removerFim(Lista *l) {
    if (l->primeiro == NULL) {
        printf("Lista vazia!!!\n");
        exit(1);
    }

    Celula *atual = l->primeiro;
    Show s;

    if (atual->prox == NULL) {
        s = atual->elemento;
        free(atual);
        l->primeiro = NULL;
    } else {
        while (atual->prox->prox != NULL) {
            atual = atual->prox;
        }
        s = atual->prox->elemento;
        free(atual->prox);
        atual->prox = NULL;
    }

    l->tamanho--;
    return s;
}


Show remover(Lista *l, int pos) {
    if (pos < 0 || pos >= l->tamanho || l->primeiro == NULL) {
        printf("Erro ao remover!!!\n");
        exit(1);
    }

    if (pos == 0) {
        return removerInicio(l);
    }

    Celula *atual = l->primeiro;
    for (int i = 0; i < pos - 1; i++) {
        atual = atual->prox;
    }

    Celula *remover = atual->prox;
    Show s = remover->elemento;
    atual->prox = remover->prox;
    free(remover);
    l->tamanho--;
    return s;
}


void mostrar(Lista *l) {
    Celula *atual = l->primeiro;
    while (atual != NULL) {
        Show show = atual->elemento;

        printf("=> %s ## %s ## %s ## %s ## [", 
            show.show_id, show.title, show.type, show.director);

        for (int j = 0; j < show.cast_size; j++) {
            printf("%s", show.cast1[j]);
            if (j < show.cast_size - 1) {
                printf(", ");
            }
        }

        printf("] ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
            show.country, show.date, show.release_year,
            show.rating, show.duration, show.listed_in);

        atual = atual->prox;
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

    Lista lista;
    iniciarLista(&lista);

    char entrada[20];
    lerLinhaInput(entrada, sizeof(entrada));

    while (strcmp(entrada, "FIM") != 0) {
        int index = atoi(entrada + 1) - 1;
        inserirFim(&lista, shows[index]);
        lerLinhaInput(entrada, sizeof(entrada));
    }

    int numComandos;
    scanf("%d", &numComandos);
    getchar(); // limpar o \n

    for (int i = 0; i < numComandos; i++) {
        char comando[10];
        scanf("%s", comando);

        if (strcmp(comando, "II") == 0) {
            scanf("%s", entrada);
            int idx = atoi(entrada + 1) - 1;
            inserirInicio(&lista, shows[idx]);

        } else if (strcmp(comando, "IF") == 0) {
            scanf("%s", entrada);
            int idx = atoi(entrada + 1) - 1;
            inserirFim(&lista, shows[idx]);

        } else if (strcmp(comando, "I*") == 0) {
            int pos;
            scanf("%d %s", &pos, entrada);
            int idx = atoi(entrada + 1) - 1;
            inserir(&lista, shows[idx], pos);

        } else if (strcmp(comando, "RI") == 0) {
            Show r = removerInicio(&lista);
            printf("(R) %s\n", r.title);

        } else if (strcmp(comando, "RF") == 0) {
            Show r = removerFim(&lista);
            printf("(R) %s\n", r.title);

        } else if (strcmp(comando, "R*") == 0) {
            int pos;
            scanf("%d", &pos);
            Show r = remover(&lista, pos);
            printf("(R) %s\n", r.title);
        }
    }

    mostrar(&lista);

    return 0;
}