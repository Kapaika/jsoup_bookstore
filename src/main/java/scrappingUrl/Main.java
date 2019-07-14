package scrappingUrl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {


       // String s = "https://www.empik.com/promocje?searchCategory=31&hideUnavailable=true&start=31&qtype=facetForm";
        List<Book> listOfBooks = new LinkedList();

        for(int i = 1; i<= 31 ;i= i+30){

            Document doc = Jsoup.connect("https://www.empik.com/promocje?searchCategory=31&hideUnavailable=true&start="
                    + i +"&qtype=facetForm").get();

            Elements elementsByClass = doc.getElementsByClass("product-details-wrapper ta-details-box");


            elementsByClass.forEach(s -> {

                String title = s.getElementsByClass("ta-product-title").text();
                String author = s.getElementsByClass("smartAuthor").text();
                String promotionPrice = s.getElementsByClass("ta-price-tile").text();

                String[] prices = promotionPrice.split(" ");

                Book book = new Book(title,author, prices[0], prices[2]);

                listOfBooks.add(book);

            });

            listOfBooks.forEach(System.out::println);

        }


//        try(Session session = DatabseConnection.getSessionFactory().openSession()){
//
//            for(Book book: listOfBooks){
//
//                EntityTransaction transaction = session.getTransaction();
//                transaction.begin();
//                session.save(book);
//                transaction.commit();
//            }
//
//            session.close();
//
//        }catch (HibernateException e){
//            System.out.println();
//        }

    }
}
