package bg.softuni.tutorme.entities.dtos.quotes;

public class QuoteDTO {

    private String content;
    private String author;

    public QuoteDTO() {
    }

    public String getContent() {
        return content;
    }

    public QuoteDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public QuoteDTO setAuthor(String author) {
        this.author = author;
        return this;
    }
}
