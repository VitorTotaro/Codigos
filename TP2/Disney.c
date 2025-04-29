#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_LINE 1024
#define MAX_FIELDS 12
#define MAX_CAST 20
#define MAX_LEN 100
#define MAX_SHOWS 1368

typedef struct {
    char show_id[MAX_LEN];
    char type[MAX_LEN];
    char title[MAX_LEN];
    char director[MAX_LEN];
    char country[MAX_LEN];
    char duration[MAX_LEN];
    char date[MAX_LEN];
    char release_year[MAX_LEN];
    char rating[MAX_LEN];
    char listed_in[MAX_LEN];
    char cast1[MAX_CAST][MAX_LEN];
    int cast_size;
    int release;
} Show;

char* tratarCampo(char* campo) {
    return (strlen(campo) == 0) ? "NaN" : campo;
}

void ordenar(char arr[][MAX_LEN], int size) {
    for (int i = 0; i < size; i++) {
        for (int j = i + 1; j < size; j++) {
            if (strcasecmp(arr[j], arr[i]) < 0) {
                char tmp[MAX_LEN];
                strcpy(tmp, arr[i]);
                strcpy(arr[i], arr[j]);
                strcpy(arr[j], tmp);
            }
        }
    }
}

void imprimir(Show show) {
    printf("=> %s ## %s ## %s ## %s ## ", show.show_id, show.title, show.type, show.director);
    printf("[");
    for (int i = 0; i < show.cast_size; i++) {
        printf("%s", show.cast1[i]);
        if (i < show.cast_size - 1) printf(", ");
    }
    printf("]");
    printf(" ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
           show.country, show.date, show.release_year, show.rating, show.duration, show.listed_in);
}

Show ler(char* linha) {
    Show show;
    char* campos[MAX_FIELDS];
    char* token;

    campos[0] = strtok(linha, ",");
    for (int i = 1; i < MAX_FIELDS; i++) {
        campos[i] = strtok(NULL, ",");
    }

    strcpy(show.show_id, tratarCampo(campos[0]));
    strcpy(show.type, tratarCampo(campos[1]));
    strcpy(show.title, tratarCampo(campos[2]));
    strcpy(show.director, tratarCampo(campos[3]));
    strcpy(show.country, tratarCampo(campos[5]));
    strcpy(show.date, tratarCampo(campos[6]));
    strcpy(show.release_year, tratarCampo(campos[7]));
    strcpy(show.rating, tratarCampo(campos[8]));
    strcpy(show.duration, tratarCampo(campos[9]));
    strcpy(show.listed_in, tratarCampo(campos[10]));

    show.cast_size = 0;
    if (campos[4] == NULL || strlen(campos[4]) == 0) {
        strcpy(show.cast1[0], "NaN");
        show.cast_size = 1;
    } else {
        token = strtok(campos[4], ",");
        while (token != NULL && show.cast_size < MAX_CAST) {
            while (*token == ' ') token++; // remover espaços à esquerda
            strcpy(show.cast1[show.cast_size++], token);
            token = strtok(NULL, ",");
        }
        ordenar(show.cast1, show.cast_size);
    }

    show.release = atoi(campos[7]);
    return show;
}

int main() {
    FILE* file = fopen("./disneyplus.csv", "r");
    if (file == NULL) return 1;

    Show shows[MAX_SHOWS];
    char linha[MAX_LINE];
    int total = 0;

    fgets(linha, MAX_LINE, file); // Pular cabeçalho

    while (fgets(linha, MAX_LINE, file) && total < MAX_SHOWS) {
        linha[strcspn(linha, "\n")] = '\0';
        shows[total++] = ler(linha);
    }

    fclose(file);

    char entrada[10];
    while (scanf("%s", entrada) == 1) {
        if (strcmp(entrada, "FIM") == 0) break;
        int idx = atoi(&entrada[1]);
        if (idx >= 1 && idx <= total) {
            imprimir(shows[idx - 1]);
        }
    }

    return 0;
}
