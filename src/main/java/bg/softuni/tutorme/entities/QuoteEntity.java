package bg.softuni.tutorme.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Quotes")
public class QuoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String quote;

    @Column(nullable = false)
    private String author;

    public QuoteEntity() {
    }

    public long getId() {
        return id;
    }

    public QuoteEntity setId(long id) {
        this.id = id;
        return this;
    }

    public String getQuote() {
        return quote;
    }

    public QuoteEntity setQuote(String quote) {
        this.quote = quote;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public QuoteEntity setAuthor(String author) {
        this.author = author;
        return this;
    }
}
