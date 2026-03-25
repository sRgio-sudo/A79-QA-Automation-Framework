package api;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v145.network.Network;

import java.util.Optional;

public class ApiNetworkInterceptor {
    private DevTools devTools;
    private final boolean[] isHitDetected = {false};

    public ApiNetworkInterceptor(ChromeDriver driver) {
        this.devTools = driver.getDevTools();
        this.devTools.createSession();
    }

    public void listenForEndpoint(String endpointPart) {
        devTools.send(Network.enable(
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty()));

        devTools.addListener(Network.responseReceived(), response -> {
            String url = response.getResponse().getUrl();
            int status = response.getResponse().getStatus();

            if (url.contains(endpointPart) && (status == 200 || status == 302)) {
                System.out.println("Download URL: " + url);
                isHitDetected[0] = true;
            }
        });
    }

    public boolean whaitAndCheckStatus(int seconds) {
        for (int i = 0; i < seconds * 2; i++) { // Проверка каждые 500мс
            if (isHitDetected[0]) {
                return true;
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }
        return isHitDetected[0];
    }
}
