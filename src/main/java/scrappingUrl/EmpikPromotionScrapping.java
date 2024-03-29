package scrappingUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class EmpikPromotionScrapping implements IPromotionScrapping {

    private List<Book> listOfBooks = new LinkedList<>();

    @Override
    public List<Book> scrapPromotion() throws IOException {

        for(int i = 1; i<= 31 ; i=i+30){

            String url = createUrl(i);
            Document doc = Jsoup.connect(url).get();
            Elements elementsByClass = doc.getElementsByClass("product-details-wrapper ta-details-box");

            mappingToBookList(listOfBooks, elementsByClass);

        }

        return listOfBooks;
    }

    private String createUrl(int i) {

        String startOfUrl = "https://www.empik.com/promocje?searchCategory=31&hideUnavailable=true&start=";
        String endOfUrl = "&qtype=facetForm";

         return startOfUrl +
                 i +
                 endOfUrl;
    }

    private void mappingToBookList(List<Book> listOfBooks, Elements elementsByClass) {
        elementsByClass.stream()
               .map(s -> {
                   String title = s.getElementsByClass("ta-product-title").text();
                   String author = s.getElementsByClass("smartAuthor").text();
                   String promotionPrice = s.getElementsByClass("ta-price-tile").text();
                   String[] prices = promotionPrice.split(" ");
                   return new Book(title, author, prices[0], prices[2]);
               }).forEach(listOfBooks::add);
    }

    public static void main(String[] args) throws IOException {
        EmpikPromotionScrapping em  = new EmpikPromotionScrapping();
        em.scrapPromotion().forEach(System.out::println);
    }

}
