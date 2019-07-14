package scrappingUrl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String author;
    String normalPrice;
    String promotionPrice;

    public Book(String title, String author, String normalPrice, String promotionPrice) {
        this.title = title;
        this.author = author;
        this.normalPrice = normalPrice;
        this.promotionPrice = promotionPrice;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", normalPrice='" + normalPrice + '\'' +
                ", promotionPrice='" + promotionPrice + '\'' +
                '}';
    }
}
