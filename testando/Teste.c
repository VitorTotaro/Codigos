#include <stdio.h>

int main() {
    printf("Iniciando programa...\n");
    fflush(stdout);

    printf("Tentando abrir o arquivo...\n");
    fflush(stdout);

    FILE *file = fopen("disneyplus.csv", "r");
    if (file == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        fflush(stdout);
        return 1;
    }

    printf("Arquivo aberto com sucesso!\n");
    fflush(stdout);

    fclose(file);
    return 0;
}