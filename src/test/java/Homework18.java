import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework18 extends BaseTest {
    @Test
    public void playSong(){
        navigatingToPage();
        provideEmail(validEmail);
        providePassword(validPassword);
        clickSubmit();
        selectNextSong();
        clickPlayButton();
        Assert.assertTrue(songPlayingCheck());
    }
}