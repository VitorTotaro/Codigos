#include <stdio.h>
#include <string.h>
#include <ctype.h>

// Função recursiva para somar os dígitos de uma string
int somarDigitos(const char* digitos, int indice) {
    // Caso base: chegou ao final da string
    if (digitos[indice] == '\0') {
        return 0;
    }
    
    // Converte o caractere para seu valor numérico e soma com a chamada recursiva
    // para o restante da string
    return (isdigit(digitos[indice]) ? digitos[indice] - '0' : 0) + somarDigitos(digitos, indice + 1);
}

int main() {
    char entrada[100];
    
    // Lê a primeira entrada
    fgets(entrada, sizeof(entrada), stdin);
    
    // Remove o caractere de nova linha, se existir
    int len = strlen(entrada);
    if (entrada[len - 1] == '\n') {
        entrada[len - 1] = '\0';
        len--;
    }
    
    // Continua até encontrar "FIM"
    while (!(len >= 3 && entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M')) {
        // Chama a função recursiva começando pelo índice 0
        printf("%d\n", somarDigitos(entrada, 0));
        
        // Lê a próxima entrada
        fgets(entrada, sizeof(entrada), stdin);
        
        // Remove o caractere de nova linha, se existir
        len = strlen(entrada);
        if (entrada[len - 1] == '\n') {
            entrada[len - 1] = '\0';
            len--;
        }
    }
    
    return 0;
}