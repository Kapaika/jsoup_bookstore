package scrappingUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class amazonFreeBooks implements IPromotionScrapping {


    @Override
    public List<Book> scrapPromotion() throws IOException {

        for(int i = 1; i<=100; i++){

            String url = "https://bookoutlet.com/Store/Browse?merch=Clearance-Titles&N=isBlowout&page=" +
                    i +
                    "&size=24&sort=popularity_0";

            Document doc = Jsoup.connect(url).get();

            Element mainResults = doc.getElementById("items");

            Elements elementsByClass = mainResults.getElementsByClass("grid-item");

            elementsByClass.forEach(s->{
                System.out.println(s.getElementsByClass("price").text().split("\\$")[1]);
                System.out.println(s.getElementsByClass("one-line").eachText().get(0));
                System.out.println(s.getElementsByClass("one-line").eachText().get(1));
                System.out.println(s.getElementsByTag("h6").text().split("\\$")[1]);
                System.out.println("----------------------------------------");
            });
        }

//        List<String> href = mainResults
//                .getElementsByClass("a-link-normal s-access-detail-page s-color-twister-title-link a-text-normal")
//                .eachAttr("href");
//
//        href.forEach(System.out::println);

        return null;
    }

    public static void main(String[] args) throws IOException {

        amazonFreeBooks am  = new amazonFreeBooks();
        am.scrapPromotion();

    }
}
