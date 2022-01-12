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
            while (true) {
                System.out.println("1: check today's USD rate");
                System.out.println("2: check USD rate by date");
                System.out.println("3: check average USD purchase/sale rate by period");

                String choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        DataBaseManipulation.rateByTheDate(em, today.toString());
                        break;
                    case "2":
                        System.out.println("Enter the date in format yyyy-MM-dd (example: 2022-01-10)");
                        String yourDate = sc.nextLine();
                        DataBaseManipulation.rateByTheDate(em, yourDate);
                        break;
                    case "3":
                        System.out.println("Enter the FIRST date in format yyyy-MM-dd (example: 2022-01-10)");
                        String first = sc.nextLine();
                        System.out.println("Enter the SECOND date in format yyyy-MM-dd (example: 2022-01-10)");
                        String second = sc.nextLine();
                        DataBaseManipulation.averageRateByPeriod(em, first, second);
                    default:
                        return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sc.close();
            em.close();
            emf.close();
        }
    }
}