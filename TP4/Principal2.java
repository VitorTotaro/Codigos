import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

public class Principal2 {
    static class Show {
        String show_id, type, title, director, country, duration;
        String date;
        String release_year;
        String rating;
        String listed_in;
        String[] cast1;
        int release;
        Data data;

        public String gettitle() {
            return title;
        }

        public static class Data {
            int ano, mes, dia;

            public Data(int ano, int mes, int dia) {
                this.ano = ano;
                this.mes = mes;
                this.dia = dia;
            }
        }

        // Ordena vetor de Strings ignorando espaços e letras maiúsculas/minúsculas
        public static String[] ordenar(String[] array) {
            for (int i = 0; i < array.length; i++) {
                for (int j = i + 1; j < array.length; j++) {
                    if (array[j].trim().compareToIgnoreCase(array[i].trim()) < 0) {
                        String tmp = array[i];
                        array[i] = array[j];
                        array[j] = tmp;
                    }
                }
            }
            return array;
        }

        public static String tratarCampo(String campo) {
            return campo.equals("") ? "NaN" : campo;
        }

        public static int tratarMes(String mes) {
            switch (mes) {
                case "January":
                    return 1;
                case "February":
                    return 2;
                case "March":
                    return 3;
                case "April":
                    return 4;
                case "May":
                    return 5;
                case "June":
                    return 6;
                case "July":
                    return 7;
                case "August":
                    return 8;
                case "September":
                    return 9;
                case "October":
                    return 10;
                case "November":
                    return 11;
                case "December":
                    return 12;
                default:
                    return 0; // caso não seja nenhum mês conhecido
            }
        }

        public static Show ler(String linha) {
            Show show = new Show();
            String[] campos = new String[12];
            int virgula = 0;
            boolean aspas = false;

            campos[0] = "";

            for (int i = 0; i < linha.length(); i++) {
                char c = linha.charAt(i);

                if (c == '"' && !aspas) {
                    aspas = true;
                } else if (c == '"' && aspas) {
                    aspas = false;
                } else if (c == ',' && !aspas) {
                    virgula++;
                    campos[virgula] = "";
                } else {
                    campos[virgula] += c;
                }
            }

            // Tratamento de campos
            show.show_id = tratarCampo(campos[0]);
            show.type = tratarCampo(campos[1]);
            show.title = tratarCampo(campos[2]);
            show.director = tratarCampo(campos[3]);
            show.country = tratarCampo(campos[5]);
            show.date = tratarCampo(campos[6]);
            show.release_year = tratarCampo(campos[7]);
            show.rating = tratarCampo(campos[8]);
            show.duration = tratarCampo(campos[9]);
            show.listed_in = tratarCampo(campos[10]);

            // Elenco
            if (campos[4].equals("")) {
                show.cast1 = new String[] { "NaN" };
            } else {
                String[] castTemp = campos[4].split(",");
                show.cast1 = ordenar(castTemp);
                // remove espaço só do primeiro
                show.cast1[0] = show.cast1[0].trim();
            }

            // Processar a data
            if (show.date.equals("NaN")) {
                show.data = new Data(1900, 3, 1);
            } else {
                String[] partesData = show.date.trim().split("\\s+");
                if (partesData.length == 3) {
                    int dia = Integer.parseInt(partesData[1].replace(",", ""));
                    int mes = tratarMes(partesData[0]);
                    int ano = Integer.parseInt(partesData[2]);
                    show.data = new Data(ano, mes, dia);
                }
            }

            show.release = Integer.parseInt(campos[7]);
            return show;
        }

        public void imprimir() {
            System.out.printf("=> %s ## %s ## %s ## %s ##", show_id, title, type, director);

            // Cast
            System.out.print(" [");
            for (int i = 0; i < cast1.length; i++) {
                System.out.print(cast1[i].trim());
                if (i < cast1.length - 1)
                    System.out.print(", ");
            }
            System.out.print("]");

            // Restante
            System.out.printf(" ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
                    country, date, release_year, rating, duration, listed_in);
        }
    }

     // Implementação da Árvore Alvinegra
    static class ArvoreAlvinegra {
        private static final boolean RED = true;
        private static final boolean BLACK = false;

        private class No {
            Show elemento;
            No esq, dir;
            boolean cor;

            public No(Show elemento) {
                this(elemento, RED); // Novos nós são sempre vermelhos
            }

            public No(Show elemento, boolean cor) {
                this.elemento = elemento;
                this.cor = cor;
                this.esq = this.dir = null;
            }
        }

        private No raiz;
        public int comparacoes = 0;

        public ArvoreAlvinegra() {
            raiz = null;
        }

        // --- MÉTODOS DE BALANCEAMENTO (ROTAÇÕES E FLIP) ---

        private boolean isRed(No no) {
            return no != null && no.cor == RED;
        }

        private No rotacaoEsq(No h) {
            No x = h.dir;
            h.dir = x.esq;
            x.esq = h;
            x.cor = h.cor;
            h.cor = RED;
            return x;
        }

        private No rotacaoDir(No h) {
            No x = h.esq;
            h.esq = x.dir;
            x.dir = h;
            x.cor = h.cor;
            h.cor = RED;
            return x;
        }
        
        
         private void flipColors(No h) {
            h.cor = !h.cor;
            h.esq.cor = !h.esq.cor;
            h.dir.cor = !h.dir.cor;
        }

        public void inserir(Show x) {
            if (x == null || x.title == null || x.title.isEmpty()) return;
            raiz = inserir(x, raiz);
            if (raiz != null) {
                raiz.cor = BLACK;
            }
        }

        private No inserir(Show x, No h) {
            if (h == null) {
                return new No(x);
            }

            int cmp = x.title.trim().compareTo(h.elemento.title.trim());

            if (cmp < 0) {
                h.esq = inserir(x, h.esq);
            } else if (cmp > 0) {
                h.dir = inserir(x, h.dir);
            }

            if (isRed(h.dir) && !isRed(h.esq)) h = rotacaoEsq(h);
            if (isRed(h.esq) && isRed(h.esq.esq)) h = rotacaoDir(h);
            if (isRed(h.esq) && isRed(h.dir)) flipColors(h);

            return h;
        }

        public void pesquisar(String title) {
            System.out.print(" =>raiz  ");
            boolean achou = pesquisar(raiz, title);
            System.out.println(achou ? "SIM" : "NAO");
        }

        private boolean pesquisar(No no, String title) {
            if (no == null) {
                return false;
            }

            comparacoes++;
            int cmp = title.trim().compareTo(no.elemento.title.trim());

            if (cmp == 0) {
                return true;
            } else if (cmp < 0) {
                System.out.print("esq ");
                return pesquisar(no.esq, title);
            } else {
                System.out.print("dir ");
                return pesquisar(no.dir, title);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("/tmp/disneyplus.csv"));
        Scanner in = new Scanner(System.in);

        Show[] shows = new Show[1450]; // Aumentar para garantir que todos os dados caibam
        sc.nextLine(); // Pula o cabeçalho do CSV

        int count = 0;
        while (sc.hasNextLine() && count < shows.length) {
            shows[count] = Show.ler(sc.nextLine());
            count++;
        }

        ArvoreAlvinegra arvore = new ArvoreAlvinegra();
        long tempoInicio = System.currentTimeMillis();

        // --- FASE DE INSERÇÃO ---
        String entrada = in.nextLine();
        while (!entrada.equals("FIM")) {
            Show showParaInserir = null;
            for (int i = 0; i < count; i++) {
                if (shows[i] != null && shows[i].show_id.equals(entrada)) {
                    showParaInserir = shows[i];
                    break;
                }
            }
            if (showParaInserir != null) {
                arvore.inserir(showParaInserir);
            }
            entrada = in.nextLine();
        }

        // --- FASE DE PESQUISA ---
        entrada = in.nextLine();
        arvore.comparacoes = 0; // Zera o contador antes das pesquisas

        while (!entrada.equals("FIM")) {
            arvore.pesquisar(entrada);
            entrada = in.nextLine();
        }

        long tempoFim = System.currentTimeMillis();

        // --- CRIAÇÃO DO ARQUIVO DE LOG ---
        long tempoTotal = tempoFim - tempoInicio;
        FileWriter fw = new FileWriter("872284_alvinegra.txt");
        fw.write("872284\t" + tempoTotal + "\t" + arvore.comparacoes + "\n");
        fw.close();

        sc.close();
        in.close();
    }
}
