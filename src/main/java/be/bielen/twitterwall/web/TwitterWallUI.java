package be.bielen.twitterwall.web;

import be.bielen.twitterwall.web.delegate.TwitterWallDelegate;
import be.bielen.twitterwall.web.dto.TweetDTO;
import be.bielen.twitterwall.web.exception.TwitterWallBaseException;
import com.vaadin.annotations.Theme;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringUI
@Theme("valo")
public class TwitterWallUI extends UI {

    @Value("${twitter.search}")
    private String searchString;

    @Autowired
    private TwitterWallDelegate twitterWallDelegate;

    private HorizontalLayout mainLayout;
    private VerticalLayout leftLayout;
    private VerticalLayout centerLayout;
    private VerticalLayout rightLayout;
    private HorizontalLayout searchLayout;
    private GridLayout gridLayout;

    private Button btnSearch;
    private TextField txtSearchInput;

    @PostConstruct
    public void initLayout() {
        mainLayout = new HorizontalLayout();
        mainLayout.setWidth(100, Unit.PERCENTAGE);

        centerLayout = new VerticalLayout();
        centerLayout.setWidth(600, Unit.PIXELS);
        centerLayout.setSpacing(true);

        leftLayout = new VerticalLayout();
        leftLayout.setWidth(300, Unit.PIXELS);
        leftLayout.setHeight(100, Unit.PERCENTAGE);
        leftLayout.setSpacing(true);

        rightLayout = new VerticalLayout();
        rightLayout.setWidth(300, Unit.PIXELS);
        rightLayout.setHeight(100, Unit.PERCENTAGE);
        rightLayout.setSpacing(true);

        // Build center layout
        searchLayout = new HorizontalLayout();
        searchLayout.setSpacing(true);

        txtSearchInput = new TextField("Search String:");
        txtSearchInput.setWidth(200, Unit.PIXELS);
        txtSearchInput.setValue(searchString);
        btnSearch = new Button("Search for tweets", this::onButtonSearchForTweetsClick);
        btnSearch.focus();

        searchLayout.addComponent(txtSearchInput);
        searchLayout.addComponent(btnSearch);
        searchLayout.setComponentAlignment(txtSearchInput, Alignment.BOTTOM_LEFT);
        searchLayout.setComponentAlignment(btnSearch, Alignment.BOTTOM_LEFT);

        gridLayout = new GridLayout(1, 20);
        //gridLayout.setWidth(800, Unit.PIXELS);

        centerLayout.addComponent(searchLayout);
        centerLayout.addComponent(gridLayout);
        centerLayout.setComponentAlignment(searchLayout, Alignment.MIDDLE_CENTER);
        centerLayout.setComponentAlignment(gridLayout, Alignment.MIDDLE_CENTER);
        centerLayout.setExpandRatio(gridLayout, 1.0f);

        // Build left layout
        Button left = new Button("Left");
        left.setSizeFull();
        leftLayout.addComponent(left);

        // Build right layout
        Button right = new Button("Right");
        right.setSizeFull();
        rightLayout.addComponent(right);

        // Compose main layout
        mainLayout.addComponent(leftLayout);
        mainLayout.addComponent(centerLayout);
        mainLayout.addComponent(rightLayout);
        mainLayout.setComponentAlignment(leftLayout, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(centerLayout, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(rightLayout, Alignment.TOP_CENTER);
    }

    @Override
    protected void init(VaadinRequest request) {
        setContent(mainLayout);
    }

    private void onButtonSearchForTweetsClick(Button.ClickEvent clickEvent) {
        String searchValue = txtSearchInput.getValue();

        // Clear grid
        gridLayout.removeAllComponents();

        try {
            List<TweetDTO> tweetDTOList = twitterWallDelegate.findTweets(searchValue);
            for (TweetDTO tweetDTO : tweetDTOList) {
                if (tweetDTO != null) {
                    gridLayout.addComponent(createTweetLayout(tweetDTO));
                }
            }

        } catch (TwitterWallBaseException be) {
            // Swallow
        }

    }

    private VerticalLayout createTweetLayout(TweetDTO tweetDTO) {
        VerticalLayout tweetLayout = new VerticalLayout();
        if (tweetDTO != null) {
            //tweetLayout.setWidth(800, Unit.PIXELS);
            //tweetLayout.setHeight(100, Unit.PERCENTAGE);

            Image image = new Image();
            image.setWidth(50, Unit.PIXELS);
            image.setHeight(50, Unit.PIXELS);
            image.setSource(new ExternalResource(tweetDTO.getProfileImageUrl()));
            Label nameLabel = new Label(tweetDTO.getFromUser());
            nameLabel.setWidth(200, Unit.PIXELS);
            HorizontalLayout profileLayout = new HorizontalLayout();
            //profileLayout.setWidth(800, Unit.PIXELS);
            profileLayout.setSpacing(true);
            profileLayout.addComponent(image);
            profileLayout.addComponent(nameLabel);
            profileLayout.setComponentAlignment(image, Alignment.MIDDLE_LEFT);
            profileLayout.setComponentAlignment(nameLabel, Alignment.MIDDLE_LEFT);
            profileLayout.setExpandRatio(nameLabel, 1.0f);

            Label textLabel = new Label(tweetDTO.getText());
            textLabel.setWidth(500, Unit.PIXELS);

            tweetLayout.addComponent(profileLayout);
            tweetLayout.addComponent(textLabel);
        }
        return tweetLayout;
    }
}
