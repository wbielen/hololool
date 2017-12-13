package be.bielen.twitterwall.web;

import be.bielen.twitterwall.web.delegate.TwitterWallDelegate;
import be.bielen.twitterwall.web.dto.TweetDTO;
import be.bielen.twitterwall.web.exception.TwitterWallBaseException;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringUI
@Theme("valo")
public class TwitterWallUI extends UI {

    @Autowired
    private TwitterWallDelegate twitterWallDelegate;

    private VerticalLayout mainLayout;
    private HorizontalLayout searchLayout;
    private GridLayout gridLayout;

    private Button btnSearch;
    private TextField txtSearchInput;

    @Override
    protected void init(VaadinRequest request) {

        Panel mainWindow = new Panel();
        mainWindow.setSizeFull();

        mainLayout = new VerticalLayout();
        mainLayout.setWidth(100, Unit.PERCENTAGE);
        mainLayout.setSpacing(true);

        searchLayout = new HorizontalLayout();
        searchLayout.setSpacing(true);

        txtSearchInput = new TextField("Search String:");
        txtSearchInput.setWidth(200, Unit.PIXELS);
        btnSearch = new Button("Search for tweets", this::onButtonSeearchForTweetsClick);

        searchLayout.addComponent(txtSearchInput);
        searchLayout.addComponent(btnSearch);
        searchLayout.setComponentAlignment(txtSearchInput, Alignment.BOTTOM_LEFT);
        searchLayout.setComponentAlignment(btnSearch, Alignment.BOTTOM_LEFT);

        gridLayout = new GridLayout(1, 20);
        gridLayout.setWidth(1000, Unit.PIXELS);

        mainLayout.addComponent(searchLayout);
        mainLayout.addComponent(gridLayout);
        mainLayout.setComponentAlignment(searchLayout, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(gridLayout, Alignment.TOP_CENTER);
        mainLayout.setExpandRatio(gridLayout, 1.0f);

        mainWindow.setContent(mainLayout);

        setContent(mainWindow);
    }

    private void onButtonSeearchForTweetsClick(Button.ClickEvent clickEvent) {
        String searchValue = txtSearchInput.getValue();

        try {
            List<TweetDTO> tweetDTOList = twitterWallDelegate.findTweets(searchValue);
            for (TweetDTO tweetDTO : tweetDTOList) {
                if (tweetDTO != null) {
                    gridLayout.addComponent(new Label(tweetDTO.toString()));
                }
            }

        } catch (TwitterWallBaseException be) {
            // Swallow
        }

    }
}
