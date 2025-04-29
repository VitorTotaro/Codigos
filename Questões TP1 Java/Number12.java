public class Number12{
    
    public static boolean senhaValida(String senha) {
        // Verificar se a senha tem pelo menos 8 caracteres
        if (senha.length() < 8) {
            return false;
        }
        
        boolean temMaiuscula = false;
        boolean temMinuscula = false;
        boolean temNumero = false;
        boolean temEspecial = false;
        
        // Verificar cada caractere da senha
        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i);
            
            // Verificar se é letra maiúscula
            if (Character.isUpperCase(c)) {
                temMaiuscula = true;
            }
            // Verificar se é letra minúscula
            else if (Character.isLowerCase(c)) {
                temMinuscula = true;
            }
            // Verificar se é número
            else if (Character.isDigit(c)) {
                temNumero = true;
            }
            // Se não é letra nem número, é caractere especial
            else {
                temEspecial = true;
            }
        }
        
        // Retorna true apenas se todos os requisitos forem atendidos
        return temMaiuscula && temMinuscula && temNumero && temEspecial;
    }
    
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in, "UTF-8");
        String entrada;
        
        // Processar entradas até encontrar "FIM"
        while (true) {
            entrada = scanner.nextLine();
            
            if (entrada.equals("FIM")) {
                break;
            }
            
            // Verificar se a senha é válida e exibir resultado
            if (senhaValida(entrada)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }
        
        scanner.close();
    }
}