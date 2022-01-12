import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DataBaseManipulation {
    public static void addInfo(Currency cur, EntityManager em) {
        em.getTransaction().begin();
        try {
            em.persist(cur);
            em.getTransaction().commit();

            System.out.println(cur);
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public static void rateByTheDate(EntityManager em, String date) {
        Query query = em.createQuery(
                "SELECT c FROM Currency c WHERE c.date = :date", Currency.class);
        query.setParameter("date", LocalDate.of(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8, 10))));
        Currency cur = (Currency) query.getSingleResult();
        System.out.println("Date: " + cur.getDate() + " Sale rate: " + cur.getSaleRateNB() + " Purchase rate: " + cur.getPurchaseRateNB());
    }

    public static void averageRateByPeriod(EntityManager em, String from, String to) {
        Query query = em.createQuery("SELECT AVG(c.purchaseRate), AVG(c.saleRate) FROM Currency c" +
                " WHERE c.date BETWEEN :from AND :to");
        query.setParameter("from", LocalDate.of(Integer.parseInt(from.substring(0, 4)), Integer.parseInt(from.substring(5, 7)), Integer.parseInt(from.substring(8, 10))));
        query.setParameter("to", LocalDate.of(Integer.parseInt(to.substring(0, 4)), Integer.parseInt(to.substring(5, 7)), Integer.parseInt(to.substring(8, 10))));
        List<Object[]> list = query.getResultList();

        for (Object[] o : list) {
            System.out.println("Average purchase rate is: " + o[0] + " Average sale rate is: " + o[1]);
        }
    }
}
