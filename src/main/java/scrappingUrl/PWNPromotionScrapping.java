package scrappingUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PWNPromotionScrapping implements IPromotionScrapping {

    @Override
    public List<Book> scrapPromotion() throws IOException {

        List<Book> listOfBooks = new LinkedList<>();
        String url = "https://ksiegarnia.pwn.pl/promocje?vt=list&page=";

        for (int i = 1; i <= 2; i++) {

            Document doc = Jsoup.connect(url + i).get();
            Elements elementsByClass = doc.getElementsByClass("emp-info-container");

            mappingToBook(listOfBooks, elementsByClass);
        }


        return listOfBooks;
    }

    private void mappingToBook(List<Book> listOfBooks, Elements elementsByClass) {
        elementsByClass.stream()
               .map(s -> {
                   String title = s.getElementsByClass("emp-info-title").text();
                   String author = s.getElementsByClass("emp-info-authors").text();
                   author = deleteAuthorTag(author);
                   String promotionPrice = s.getElementsByClass("emp-sale-price-value").text();
                   promotionPrice = deleteCurrencyFromPrice(promotionPrice);
                   String basePrice = s.getElementsByClass("emp-base-price").text();
                   basePrice = deleteCurrencyFromPrice(basePrice);

                   return new Book(title, author, basePrice, promotionPrice);
               }).forEach(listOfBooks::add);
    }

    private String deleteAuthorTag(String author){
        return author.replace("Autor: ", "");
    }

    private String deleteCurrencyFromPrice(String price){
        return price.replace(" zł", "");
    }

    public static void main(String[] args) throws IOException {
        PWNPromotionScrapping pr = new PWNPromotionScrapping();
        pr.scrapPromotion().forEach(System.out::println);
    }

}
