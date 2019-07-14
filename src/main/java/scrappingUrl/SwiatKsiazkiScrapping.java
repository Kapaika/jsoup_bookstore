package scrappingUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SwiatKsiazkiScrapping implements  IPromotionScrapping{

    @Override
    public List<Book> scrapPromotion() throws IOException {
        List<Book> listOfBooks = new LinkedList<>();

        for (int i = 1; i <= 10; i++) {

            String url = createUrl(i);
            Document doc = Jsoup.connect(url).get();
            Elements elementsByClass = doc.getElementsByClass("item product product-item");

            mappingToBook(listOfBooks, elementsByClass);
        }


        return listOfBooks;
    }

    private String createUrl(int i) {

        String startOfUrl = "https://www.swiatksiazki.pl/Ksiazki/outlet-3255.html?p=";
        String endOfUrl = "&product_list_limit=30&product_list_mode=grid";

        return startOfUrl +
                i +
                endOfUrl;
    }

    private void mappingToBook(List<Book> listOfBooks, Elements elementsByClass) {
        elementsByClass.stream()
                .map(s -> {
                    String title = s.getElementsByClass("product name product-item-name").text();
                    title = deleteOutletSign(title);
                    String author = s.getElementsByClass("product author product-item-author").text();
                    String promotionPrice = s.getElementsByClass("special-price").text();
                    promotionPrice = deleteCurrencyFromPrice(promotionPrice);
                    String basePrice = s.getElementsByClass("old-price").text();
                    basePrice = deleteCurrencyFromPrice(basePrice);
                    return new Book(title, author, promotionPrice, basePrice);
                }).forEach(listOfBooks::add);
    }

    private String deleteOutletSign(String title){
        return title.replace("[OUTLET] ", "");
    }

    private String deleteCurrencyFromPrice(String price){
        return price.replace(" zł", "");
    }

    public static void main(String[] args) throws IOException {
        SwiatKsiazkiScrapping tk = new SwiatKsiazkiScrapping();
        tk.scrapPromotion().forEach(System.out::println);
    }
}
