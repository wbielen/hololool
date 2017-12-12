package be.bielen.twitterwall;

import be.bielen.twitterwall.services.TwitterService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class TwitterWallUI extends UI {

    @Autowired
    private TwitterService twitterService;

    private Button searchForTweets;
    private TextField textField;

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setSpacing(true);

        Button button = new Button("Click me", e -> Notification.show("Hello Spring+Vaadin user!"));
        textField = new TextField("Search String:");
        textField.setWidth(100, Unit.PIXELS);
        searchForTweets = new Button("Search for tweets", this::onButtonSeearchForTweetsClick);


        verticalLayout.addComponent(button);
        verticalLayout.addComponent(textField);
        verticalLayout.addComponent(searchForTweets);

        setContent(verticalLayout);
    }

    private void onButtonSeearchForTweetsClick(Button.ClickEvent clickEvent) {
        String searchValue = textField.getValue();
        twitterService.searchForTweets(searchValue);
    }
}
