package scrappingUrl;

import java.io.IOException;
import java.util.List;

public interface IPromotionScrapping {

    public List<Book> scrapPromotion() throws IOException;
}
