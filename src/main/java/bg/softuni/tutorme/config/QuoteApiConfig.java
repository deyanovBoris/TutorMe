package bg.softuni.tutorme.config;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "quotes.api")
public class QuoteApiConfig {
    private String url;

    public QuoteApiConfig() {
    }

    public String getUrl() {
        return url;
    }

    public QuoteApiConfig setUrl(String url) {
        this.url = url;
        return this;
    }

    @PostConstruct
    public void checkConfiguration(){
        if (url == null || url.isEmpty()){
            throw new IllegalStateException("Sorry, but no url to connect to quotes is found.");
        }
    }
}
