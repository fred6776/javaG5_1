import com.codeborne.selenide.Selenide;
import data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;


class CallbackTest {
    //private  Faker faker;

    @BeforeEach
    void setup() {
        //faker = new Faker(new Locale("ru"));
        Selenide.open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        Selenide.$("[data-test-id=city] input").setValue(validUser.getCity());
        Selenide.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        Selenide.$("[data-test-id=date] input").setValue(firstMeetingDate);
        Selenide.$("[data-test-id=name] input").setValue(validUser.getName());
        Selenide.$("[data-test-id=phone] input").setValue(validUser.getPhone());
        Selenide.$("[data-test-id=agreement]").click();
        Selenide.$(byText("Запланировать")).click();
        Selenide.$("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate)).shouldBe(visible, Duration.ofSeconds(15));

        Selenide.$("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        Selenide.$("[data-test-id=date] input").setValue(secondMeetingDate);
        Selenide.$(byText("Запланировать")).click();
        Selenide.$(byText("Перепланировать")).shouldBe(visible, Duration.ofSeconds(15)).click();
        Selenide.$("[data-test-id=success-notification]").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate)).shouldBe(visible, Duration.ofSeconds(15));

    }
}