package blocks;

import driver.SingletonDriver;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import spring.annotations.Block;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Block
@Getter
public class VideoPlayer {


    @FindBy(xpath = "//button[@class='ytp-play-button ytp-button']")
    private WebElement playerPlayPauseButton;
    @FindBy(xpath = "//button[@class='ytp-next-button ytp-button']")
    private WebElement playerNextButton;
    @FindBy(xpath = "//button[@class='ytp-mute-button ytp-button']")
    private WebElement playerMuteButton;
    @FindBy(xpath = "//*[@class='ytp-volume-slider-handle']")
    //style="left: 40px;" from 0 to 40px
    private WebElement playerVolumeSlider;
    @FindBy(xpath = "//span[@class='ytp-time-current']")
    private WebElement playerCurrentTime;
    @FindBy(xpath = "//span[@class='ytp-time-duration']")
    private WebElement playerDurationTime;
    @FindBy(xpath = "//div[@class='ytp-autonav-toggle-button']")
    //aria-checked='true'; true - turned on, false - turned off
    private WebElement playerAutonavToggleButton;
    @FindBy(xpath = "//button[@class='ytp-subtitles-button ytp-button']")
    private WebElement playerSubtitlesButton;
    @FindBy(xpath = "//button[contains(@class,'ytp-button ytp-settings-button')]")
    // //button[@class='ytp-button ytp-settings-button' and @aria-expanded='true']
    //or css ariaExpanded=true
    private WebElement playerSettingsButton;
    @FindBy(xpath = "//button[@class='ytp-mute-button ytp-button']")
    private WebElement playerMiniPlayerButton;
    @FindBy(xpath = "//button[@class='ytp-size-button ytp-button']")
    private WebElement playerWideScreenButton;
    @FindBy(xpath = "//button[@class='ytp-fullscreen-button ytp-button']")
    private WebElement playerFullScreenButton;
    @FindBy(xpath = "//div[@class='ytp-popup ytp-settings-menu']/descendant::div[@class='ytp-menuitem-toggle-checkbox']")
    private WebElement playerAnnotationsCheckbox;
//    @FindBy(xpath = "//div[@class='ytp-popup ytp-settings-menu']/descendant::div[@class='ytp-menuitem-content'][2]")
    @FindBy(xpath = "//div[@class='ytp-menuitem']//div[contains(text(),'Скорость воспроизведения')]")
    private WebElement playerPlaybackSpeedSettings;
    @FindBy(xpath = "//div[@class='ytp-menuitem']//div[contains(text(),'Качество')]")
    private WebElement playerVideoQuality;


    /*
    Player subtitles settings
     */
    @FindBy(xpath = "//div[@class='ytp-menuitem-label']//span[contains(text(),'Субтитры')]")
    private WebElement playerSubtitlesSettings;
    @FindBy(xpath = "//div[@role='menuitemradio']//div[@class='ytp-menuitem-label']")
    private List<WebElement> playerSubtitlesList;

    public void checkSubs(){
        var texts = getPlayerSubtitlesList().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }



    /*
    Player play speed change and check
     */
    @FindBy(xpath = "//div[@role='menuitemradio']//div[@class='ytp-menuitem-label']")
    private List<WebElement> playerSpeed;
    @Getter
    public enum PlayerSpeed{
        O_TWENTY_FIVE("0.25"),
        O_FIFTY("0.5"),
        O_SEVENTY_FIVE("0.75"),
        REGULAR("Обычная"),
        ONE_TWENTY_FIVE("1.25"),
        ONE_SEVENTY_FIVE("1.75"),
        ONE_FIFTY("1.5"),
        TWICE("2");
        String speedText;
        PlayerSpeed(String speed){
            this.speedText = speed;
        }
        public static List<String> getTexts(){
            return Arrays.stream(values()).map(PlayerSpeed::getSpeedText).collect(Collectors.toList());
        }
    }
    public void clickOnSpeed(PlayerSpeed speed){
        var element = getPlayerSpeed().stream().filter(i->i.getText().equals(speed.getSpeedText())).findFirst();
        element.ifPresent(WebElement::click);
    }
    public void checkTexts(){
        var texts = getPlayerSpeed().stream().map(WebElement::getText).collect(Collectors.toList());
        Assert.assertTrue(PlayerSpeed.getTexts().containsAll(texts));
    }




    /*
    Player Quality change and check
     */
    @FindBy(xpath = "//div[@role='menuitemradio']//div[@class='ytp-menuitem-label']//self::span[not(contains(@class, 'ytp-swatch-color'))]")
    private List<WebElement> playerQuality;
    @Getter
    public enum PlayerQuality{
        TWENTY_ONE_SIXTY("2160p"),
        FOURTEEN_FORTY("1440p"),
        TEN_EIGHTY("1080p"),
        SEVEN_TWO_O("720p"),
        FOUR_EIGHT_O("480p"),
        THREE_SIX_O("360p"),
        TWO_FOUR_O("240p"),
        ONE_FOUR_FOUR("144p"),
        AUTO("Автонастройка");

        String qualityText;
        PlayerQuality(String quality){
            this.qualityText = quality;
        }
        public static List<String> getTexts(){
            return Arrays.stream(values())
                    .map(PlayerQuality::getQualityText)
                    .collect(Collectors.toList());
        }
    }
    public void clickOnQuality(PlayerQuality quality){
        var element = getPlayerQuality().stream().filter(i->i.getText().equals(quality.getQualityText())).findFirst();
        element.ifPresent(WebElement::click);
    }
    public void checkTextsQuality(){
        var texts = getPlayerQuality().stream()
                .map(WebElement::getText)
                .map(s->s.split(" ")[0])
                .collect(Collectors.toList());
        Assert.assertTrue(VideoPlayer.PlayerQuality.getTexts().containsAll(texts));
    }

}
