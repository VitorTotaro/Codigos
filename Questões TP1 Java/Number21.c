#include <stdio.h>
#include <string.h>

// Função recursiva para inverter a string
void inverter(char *frase, int inicio, int fim) {
    if (inicio >= fim) {
        return;
    }

    // Troca os caracteres nas posições 'inicio' e 'fim'
    char temp = frase[inicio];
    frase[inicio] = frase[fim];
    frase[fim] = temp;

    // Chamada recursiva para os próximos caracteres
    inverter(frase, inicio + 1, fim - 1);
}

int main() {
    char frase[1000]; // Buffer para armazenar a string

    // Lê a entrada até encontrar "FIM"
    while (1) {
        fgets(frase, sizeof(frase), stdin);
        
        // Remover o '\n' do final, se presente
        frase[strcspn(frase, "\n")] = '\0';

        // Verifica se a entrada é "FIM"
        if (strcmp(frase, "FIM") == 0) {
            break;
        }

        // Chama a função recursiva para inverter a string
        int tamanho = strlen(frase);
        inverter(frase, 0, tamanho - 1);

        // Exibe a string invertida
        printf("%s\n", frase);
    }

    return 0;
}
