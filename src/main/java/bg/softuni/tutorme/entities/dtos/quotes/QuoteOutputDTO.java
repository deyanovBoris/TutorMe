package bg.softuni.tutorme.entities.dtos.quotes;

public class QuoteOutputDTO {
    private String author;
    private String quote;

    public QuoteOutputDTO() {
    }

    public String getAuthor() {
        return author;
    }

    public QuoteOutputDTO setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getQuote() {
        return quote;
    }

    public QuoteOutputDTO setQuote(String quote) {
        this.quote = quote;
        return this;
    }
}
