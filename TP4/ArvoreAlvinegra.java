import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class ArvoreAlvinegra {
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

    public static int comparacoes = 0;

    static class NoAN {
        public boolean cor;
        public Show elemento;
        public NoAN esq, dir;

        public NoAN(Show elemento) {
            this(elemento, false, null, null);
        }

        public NoAN(Show elemento, boolean cor) {
            this(elemento, cor, null, null);
        }

        public NoAN(Show elemento, boolean cor, NoAN esq, NoAN dir) {
            this.cor = cor;
            this.elemento = elemento;
            this.esq = esq;
            this.dir = dir;
        }
    }

    static class Alvinegra {
        private NoAN raiz;
        private int comparacoes = 0;

        public int getComparacoes() {
            return comparacoes;
        }

        public Alvinegra() {
            raiz = null;
        }

        public void pesquisar(String title) {
            System.out.print(" =>raiz  ");
            boolean achou = pesquisar(title, raiz);
            System.out.println(achou ? "SIM" : "NAO");
        }

        private boolean pesquisar(String title, NoAN i) {
            if (i == null) {
                return false;
            }
            comparacoes++;
            if (title.equals(i.elemento.title)) {
                return true;
            }
            comparacoes++;
            if (title.compareTo(i.elemento.title) < 0) {
                System.out.print("esq ");
                return pesquisar(title, i.esq);
            } else {
                System.out.print("dir ");
                return pesquisar(title, i.dir);
            }
        }

        public void inserir(Show elemento) throws Exception {
            if (raiz == null) {
                raiz = new NoAN(elemento);
            } else if (raiz.esq == null && raiz.dir == null) {
                comparacoes++;
                if (elemento.title.compareTo(raiz.elemento.title) < 0) {
                    raiz.esq = new NoAN(elemento);
                } else {
                    raiz.dir = new NoAN(elemento);
                }
                if (raiz.esq != null) raiz.esq.cor = false;
                if (raiz.dir != null) raiz.dir.cor = false;
                
            } else if (raiz.esq == null) {
                comparacoes++;
                if (elemento.title.compareTo(raiz.elemento.title) < 0) {
                    raiz.esq = new NoAN(elemento);
                } else {
                    comparacoes++;
                    if (elemento.title.compareTo(raiz.dir.elemento.title) < 0) {
                        raiz.esq = new NoAN(raiz.elemento);
                        raiz.elemento = elemento;
                    } else {
                        raiz.esq = new NoAN(raiz.elemento);
                        raiz.elemento = raiz.dir.elemento;
                        raiz.dir.elemento = elemento;
                    }
                }
                raiz.esq.cor = raiz.dir.cor = false;
            } else if (raiz.dir == null) {
                comparacoes++;
                if (elemento.title.compareTo(raiz.elemento.title) > 0) {
                    raiz.dir = new NoAN(elemento);
                } else {
                    comparacoes++;
                    if (elemento.title.compareTo(raiz.esq.elemento.title) > 0) {
                        raiz.dir = new NoAN(raiz.elemento);
                        raiz.elemento = elemento;
                    } else {
                        raiz.dir = new NoAN(raiz.elemento);
                        raiz.elemento = raiz.esq.elemento;
                        raiz.esq.elemento = elemento;
                    }
                }
                raiz.esq.cor = raiz.dir.cor = false;
            } else {
                inserir(elemento, null, null, null, raiz);
            }
            raiz.cor = false;
        }

        private void inserir(Show elemento, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) throws Exception {
            if (i == null) {
                comparacoes++;
                if (elemento.title.compareTo(pai.elemento.title) < 0) {
                    i = pai.esq = new NoAN(elemento, true);
                } else {
                    i = pai.dir = new NoAN(elemento, true);
                }

                if (pai.cor) {
                    balancear(bisavo, avo, pai, i);
                }

            } else {
                if (i.esq != null && i.dir != null && i.esq.cor && i.dir.cor) {
                    i.cor = true;
                    i.esq.cor = i.dir.cor = false;
                    if (i == raiz) {
                        i.cor = false;
                    } else if (pai.cor) {
                        balancear(bisavo, avo, pai, i);
                    }
                }

                comparacoes++;
                int cmp = elemento.title.compareTo(i.elemento.title);
                if (cmp < 0) {
                    inserir(elemento, avo, pai, i, i.esq);
                } else if (cmp > 0) {
                    inserir(elemento, avo, pai, i, i.dir);
                } else {
                    throw new Exception("Erro ao inserir: elemento repetido!");
                }
            }
        }

        private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
            if (pai.cor) {
                comparacoes++;
                if (pai.elemento.title.compareTo(avo.elemento.title) > 0) {
                    comparacoes++;
                    if (i.elemento.title.compareTo(pai.elemento.title) > 0) {
                        avo = rotacaoEsq(avo);
                    } else {
                        avo = rotacaoDirEsq(avo);
                    }
                } else {
                    comparacoes++;
                    if (i.elemento.title.compareTo(pai.elemento.title) < 0) {
                        avo = rotacaoDir(avo);
                    } else {
                        avo = rotacaoEsqDir(avo);
                    }
                }

                if (bisavo == null) {
                    raiz = avo;
                } else {
                    comparacoes++;
                    if (avo.elemento.title.compareTo(bisavo.elemento.title) < 0) {
                        bisavo.esq = avo;
                    } else {
                        bisavo.dir = avo;
                    }
                }

                avo.cor = false;
                avo.esq.cor = avo.dir.cor = true;
            }
        }

        private NoAN rotacaoDir(NoAN no) {
            NoAN noEsq = no.esq;
            NoAN noEsqDir = noEsq.dir;
            noEsq.dir = no;
            no.esq = noEsqDir;
            return noEsq;
        }

        private NoAN rotacaoEsq(NoAN no) {
            NoAN noDir = no.dir;
            NoAN noDirEsq = noDir.esq;
            noDir.esq = no;
            no.dir = noDirEsq;
            return noDir;
        }

        private NoAN rotacaoDirEsq(NoAN no) {
            no.dir = rotacaoDir(no.dir);
            return rotacaoEsq(no);
        }

        private NoAN rotacaoEsqDir(NoAN no) {
            no.esq = rotacaoEsq(no.esq);
            return rotacaoDir(no);
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(new File("/tmp/disneyplus.csv"));
        Scanner in = new Scanner(System.in);

        Show[] shows = new Show[1368];
        sc.nextLine(); // pular cabeçalho

        for (int i = 0; i < 1368; i++) {
            shows[i] = Show.ler(sc.nextLine());
        }
        Alvinegra arvore = new Alvinegra();
        String entrada = in.nextLine();

        while (!entrada.equals("FIM")) {
            int index = Integer.parseInt(entrada.substring(1)) - 1;
            arvore.inserir(shows[index]);
            entrada = in.nextLine();
        }
        entrada = in.nextLine();

        long inicio = System.currentTimeMillis();

        while (!entrada.equals("FIM")) {
            arvore.pesquisar(entrada);
            entrada = in.nextLine();
        }

        long fim = System.currentTimeMillis();
        long tempoExecucao = fim - inicio;

        FileWriter fw = new FileWriter("872284_alvinegra.txt");
        fw.write("872284\t" + tempoExecucao + "\t" + arvore.comparacoes + "\n");
        fw.close();

        sc.close();
        in.close();
    }
}
