import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Number10 {
    static int comparacoes = 0;

    static class Show {
        String show_id, type, title, director, country, duration;
        String date;
        String release_year;
        String rating;
        String listed_in;
        private String[] cast1;
        int release;
        Data data;

        static class Data {
            int ano, mes, dia;

            public Data(int ano, int mes, int dia) {
                this.ano = ano;
                this.mes = mes;
                this.dia = dia;
            }
        }

        public void imprimir() {
            System.out.printf("=> %s ## %s ## %s ## %s ##", show_id, title, type, director);
            System.out.print(" [");
            for (int i = 0; i < cast1.length; i++) {
                System.out.print(cast1[i].trim());
                if (i < cast1.length - 1) System.out.print(", ");
            }
            System.out.print("]");
            System.out.printf(" ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
                    country, date, release_year, rating, duration, listed_in);
        }

        public static Show ler(String linha) {
            Show show = new Show();
            String[] campos = new String[12];
            int virgula = 0;
            boolean aspas = false;

            campos[0] = "";

            for (int i = 0; i < linha.length(); i++) {
                char c = linha.charAt(i);
                if (c == '"' && !aspas) aspas = true;
                else if (c == '"' && aspas) aspas = false;
                else if (c == ',' && !aspas) {
                    virgula++;
                    campos[virgula] = "";
                } else {
                    campos[virgula] += c;
                }
            }

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

            if (campos[4].equals("")) {
                show.cast1 = new String[]{"NaN"};
            } else {
                String[] castTemp = campos[4].split(",");
                show.cast1 = ordenar(castTemp);
                show.cast1[0] = show.cast1[0].trim();
            }

            if (show.date.equals("NaN")) {
                show.data = new Data(1900, 3, 1);
            } else {
                String[] partes = show.date.trim().split("\\s+");
                if (partes.length == 3) {
                    int dia = Integer.parseInt(partes[1].replace(",", ""));
                    int mes = tratarMes(partes[0]);
                    int ano = Integer.parseInt(partes[2]);
                    show.data = new Data(ano, mes, dia);
                }
            }

            show.release = Integer.parseInt(campos[7]);
            return show;
        }

        public static String tratarCampo(String campo) {
            return campo.equals("") ? "NaN" : campo;
        }

        public static int tratarMes(String mes) {
            return switch (mes) {
                case "January" -> 1;
                case "February" -> 2;
                case "March" -> 3;
                case "April" -> 4;
                case "May" -> 5;
                case "June" -> 6;
                case "July" -> 7;
                case "August" -> 8;
                case "September" -> 9;
                case "October" -> 10;
                case "November" -> 11;
                case "December" -> 12;
                default -> 0;
            };
        }

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
    }

    static class Celula {
        Show elemento;
        Celula ant, prox;

        Celula(Show s) {
            this.elemento = s;
            this.ant = null;
            this.prox = null;
        }
    }

    static Celula novaCelula(Show s) {
        return new Celula(s);
    }

    static void swapCelulas(Celula a, Celula b) {
        Show temp = a.elemento;
        a.elemento = b.elemento;
        b.elemento = temp;
    }

    static Celula particao(Celula esq, Celula dir) {
        Show pivo = dir.elemento;
        Celula i = esq.ant;

        for (Celula j = esq; j != dir; j = j.prox) {
            comparacoes++;
            if (j.elemento.data.ano < pivo.data.ano ||
                    (j.elemento.data.ano == pivo.data.ano && j.elemento.data.mes < pivo.data.mes) ||
                    (j.elemento.data.ano == pivo.data.ano && j.elemento.data.mes == pivo.data.mes && j.elemento.data.dia < pivo.data.dia) ||
                    (j.elemento.data.ano == pivo.data.ano && j.elemento.data.mes == pivo.data.mes && j.elemento.data.dia == pivo.data.dia &&
                            j.elemento.title.compareTo(pivo.title) < 0)) {
                i = (i == null) ? esq : i.prox;
                swapCelulas(i, j);
            }
        }
        i = (i == null) ? esq : i.prox;
        swapCelulas(i, dir);
        return i;
    }

    static void quicksort(Celula esq, Celula dir) {
        if (dir != null && esq != dir && esq != dir.prox) {
            Celula p = particao(esq, dir);
            quicksort(esq, p.ant);
            quicksort(p.prox, dir);
        }
    }

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));
            String linha;
            br.readLine(); // cabeçalho
            List<Show> todos = new ArrayList<>();
            while ((linha = br.readLine()) != null && todos.size() < 1368) {
                todos.add(Show.ler(linha));
            }
            br.close();

            Scanner sc = new Scanner(System.in);
            Celula inicio = null, fim = null;

            while (true) {
                String entrada = sc.nextLine().trim();
                if (entrada.equals("FIM")) break;
                int idx = Integer.parseInt(entrada.substring(1)) - 1;
                Celula nova = novaCelula(todos.get(idx));
                if (inicio == null) {
                    inicio = fim = nova;
                } else {
                    fim.prox = nova;
                    nova.ant = fim;
                    fim = nova;
                }
            }

            long inicioTempo = System.currentTimeMillis();
            quicksort(inicio, fim);
            long fimTempo = System.currentTimeMillis();
            double tempo = (fimTempo - inicioTempo) / 1000.0;

            try (PrintWriter log = new PrintWriter("872284_quicksort3.txt")) {
                log.printf("Matricula: 872284\tTempo: %.6f s\tComparacoes: %d\n", tempo, comparacoes);
            }

            for (Celula c = inicio; c != null; c = c.prox) {
                c.elemento.imprimir();
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
