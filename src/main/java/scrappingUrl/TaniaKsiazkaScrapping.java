package scrappingUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TaniaKsiazkaScrapping implements IPromotionScrapping {

    @Override
    public List<Book> scrapPromotion() throws IOException {
        List<Book> listOfBooks = new LinkedList<>();
        String url = "https://www.taniaksiazka.pl/tanie-ksiazki/page-";

        for (int i = 1; i <= 2; i++) {

            Document doc = Jsoup.connect(url + i).get();
            Elements elementsByClass = doc.getElementsByClass("product-main");

            mappingToBook(listOfBooks, elementsByClass);
        }


        return listOfBooks;
    }

    private void mappingToBook(List<Book> listOfBooks, Elements elementsByClass) {
        elementsByClass.stream()
                .map(s -> {
                    String title = s.getElementsByClass("product-title").text();
                    String author = s.getElementsByClass("product-authors").text();
                    String promotionPrice = s.getElementsByClass("product-price").text();
                    promotionPrice = deleteCurrencyFromPrice(promotionPrice);
                    String basePrice = s.getElementsByTag("del").text();
                    basePrice = deleteCurrencyFromPrice(basePrice);
                    return new Book(title, author, promotionPrice, basePrice);
                }).forEach(listOfBooks::add);
    }

    private String deleteCurrencyFromPrice(String price){
        return price.replace(" zł", "");
    }

    public static void main(String[] args) throws IOException {
        TaniaKsiazkaScrapping tk = new TaniaKsiazkaScrapping();
        tk.scrapPromotion().forEach(System.out::println);
    }

}
