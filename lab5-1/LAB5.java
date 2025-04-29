/*import java.util.Scanner;

class LAB5{
    class Data{
        private int dia;
        private int mes;
        private int ano;
    
        public Data(int dia, int mes, int ano){
            this.dia = dia;
            this.mes = mes;
            this.ano = ano;
        }

        @Override
        public String toString(){
            return String.format("%02d/%02d/%d",dia, mes, ano);
        }
    
    } 
    
    class Duracao{
        private int hora;
        private int minuto;

        public Duracao(int hora, int minuto){
            this.hora = hora;
            this.minuto = minuto;
        }

        @Override
        public String toString(){
            return String.format("%02d:%02d",hora, minuto);
        }
    }

    class Passagem{
        private int id;
        private double preco;             //id;data;tempo;cidade;preco
        private Data dataVoo;
        private Duracao tempo;
        String cidade;

        public Passagem(int id, double preco, Data dataVoo, Duracao tempo, String cidade){
            this.id = id;
            this.preco = preco;
            this.dataVoo = dataVoo;
            this.tempo = tempo;
            this.cidade = cidade;
        }

        public void imprimirPassagem(){
            System.out.println(id + ";" + dataVoo + ";" + tempo + ";" + cidade + ";" + preco);
        }
    }
}

class Principal{
    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);

        LAB5 lab5 = new LAB5();

        int n = scanner.nextInt();
        scanner.nextLine(); // para a quebra de linha

        for (int i=0; i<n; i++){
            int id = scanner.nextInt();
            double preco = scanner.nextDouble();

            // Lê a data usando delimitador personalizado
            scanner.useDelimiter("[-\\s:]");// hífen, espaço e dois pontos como delimitadores
            int ano = scanner.nextInt();
            int mes = scanner.nextInt();
            int dia = scanner.nextInt();
            int hora = scanner.nextInt();
            int minuto = scanner.nextInt();

            scanner.useDelimiter("\\s+"); // Volta para delimitador de espaço
            scanner.next(); // Pula o token vazio que pode ocorrer devido à mudança de delimitador
            
            scanner.useDelimiter("\\n"); // Configura para ler até o final da linha
            String cidade = scanner.next();
            
            // Volta ao delimitador padrão para a próxima iteração
            scanner.useDelimiter("\\p{javaWhitespace}+");
            
            LAB5.Data dataVoo = lab5.new Data(dia, mes, ano);
            LAB5.Duracao tempo = lab5.new Duracao(hora, minuto);
            LAB5.Passagem passagemVoo = lab5.new Passagem(id, preco, dataVoo, tempo, cidade);

            passagemVoo.imprimirPassagem();
        }
        scanner.close();
    }
}*/

import java.util.Scanner;

class LAB5{
    class Data{
        private int dia;
        private int mes;
        private int ano;
    
        public Data(int dia, int mes, int ano){
            this.dia = dia;
            this.mes = mes;
            this.ano = ano;
        }

        @Override
        public String toString(){
            return String.format("%02d/%02d/%d",dia, mes, ano);
        }
    
    } 
    
    class Duracao{
        private int hora;
        private int minuto;

        public Duracao(int hora, int minuto){
            this.hora = hora;
            this.minuto = minuto;
        }

        @Override
        public String toString(){
            return String.format("%02d:%02d",hora, minuto);
        }
    }

    class Passagem{
        private int id;
        private double preco;             //id;data;tempo;cidade;preco
        private Data dataVoo;
        private Duracao tempo;
        String cidade;

        public Passagem(int id, double preco, Data dataVoo, Duracao tempo, String cidade){
            this.id = id;
            this.preco = preco;
            this.dataVoo = dataVoo;
            this.tempo = tempo;
            this.cidade = cidade;
        }

        public void imprimirPassagem(){
            System.out.println(id + ";" + dataVoo + ";" + tempo + ";" + cidade + ";" + preco);
        }
    }
}

class Principal{
    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);

        LAB5 lab5 = new LAB5();

        int n = Integer.parseInt(scanner.nextLine());

        for (int i=0; i<n; i++){
            // Lê a linha inteira e depois divide
            String linha = scanner.nextLine();
            String[] partes = linha.split("\\s+", 5); // Divide em no máximo 5 partes
            
            int id = Integer.parseInt(partes[0]);
            double preco = Double.parseDouble(partes[1]);
            
            String[] dataPartes = partes[2].split("-");
            int ano = Integer.parseInt(dataPartes[0]);
            int mes = Integer.parseInt(dataPartes[1]);
            int dia = Integer.parseInt(dataPartes[2]);
            
            String[] horaPartes = partes[3].split(":");
            int hora = Integer.parseInt(horaPartes[0]);
            int minuto = Integer.parseInt(horaPartes[1]);
            
            String cidade = partes[4];

            LAB5.Data dataVoo = lab5.new Data(dia, mes, ano);
            LAB5.Duracao tempo = lab5.new Duracao(hora, minuto);
            LAB5.Passagem passagemVoo = lab5.new Passagem(id, preco, dataVoo, tempo, cidade);

            passagemVoo.imprimirPassagem();
        }
        scanner.close();
    }
}
