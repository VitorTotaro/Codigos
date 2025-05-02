import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

class Show {
    String show_id, type, title, director, country, duration;
    String date;
    String release_year;
    String rating;
    String listed_in;
    private String[] cast1;
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

        // Processar data
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

public class Principal {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("/tmp/disneyplus.csv"));
        Scanner in = new Scanner(System.in);

        Show[] shows = new Show[1368];
        sc.nextLine(); // pular cabeçalho

        for (int i = 0; i < 1368; i++) {
            shows[i] = Show.ler(sc.nextLine());
        }
        Show[] array = new Show[300];
        String entrada = in.nextLine();

        int i = 0;
        while (!entrada.equals("FIM")) {
            int index = Integer.parseInt(entrada.substring(1)) - 1;
            array[i] = shows[index];
            i++;
            entrada = in.nextLine();
        }

        // ordenar

        for (int j = 0; j < array.length; j++) {
            array[j].imprimir();
        }

        sc.close();
        in.close();
    }

}
