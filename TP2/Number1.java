import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Number1 {
  public static void main(String[] args) throws FileNotFoundException {
    Scanner sc = new Scanner(new File("/tmp/disneyplus.csv"));
    Scanner in = new Scanner(System.in);

    Show[] shows = new Show[1368];
    sc.nextLine(); // pular cabeçalho

    for (int i = 0; i < 1368; i++) {
      shows[i] = Show.ler(sc.nextLine());
    }

    String entrada = in.nextLine();
    while (!entrada.equals("FIM")) {
      int index = Integer.parseInt(entrada.substring(1)) - 1;
      shows[index].imprimir();
      entrada = in.nextLine();
    }

    sc.close();
    in.close();
  }

}
