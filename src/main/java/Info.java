import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;


public class Info {

    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        Scanner sc = new Scanner(System.in);
        String date = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int i = 0;

        try {
            emf = Persistence.createEntityManagerFactory("CurrencyInfo");
            em = emf.createEntityManager();
            while (!date.equals("10-01-2022")) {// how many days u want to load to DB
                DataBaseManipulation.addInfo(DataUSD.usdRateThatDay(today.minusDays(i)), em);
                date = LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                i++;
            }
            DataBaseManipulation.rateByTheDate(em,"2022-01-11");

            DataBaseManipulation.averageRateByPeriod(em,"2022-01-11","2022-01-12");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sc.close();
            em.close();
            emf.close();
        }
    }
}