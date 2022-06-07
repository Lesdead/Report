package delivery.test;

import delivery.data.NoValidDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import delivery.data.DataGenerator;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
    NoValidDataGenerator.UserNoValidInfo noValidEnUser = NoValidDataGenerator.Registration.generateUser("en");

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
    }

    @Test
    void shouldValidFirstRegistration() {
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        String dateDelivery = DataGenerator.generateDate(3);
        $("[data-test-id=date] .input__control").setValue(dateDelivery);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + dateDelivery), Duration.ofSeconds(15));
    }

    @Test
    void shouldValidReRegistration() {
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        String firstDateDelivery = DataGenerator.generateDate(3);
        $("[data-test-id=date] .input__control").setValue(firstDateDelivery);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + firstDateDelivery), Duration.ofSeconds(15));
        String secondDateDelivery = DataGenerator.generateDate(5);
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"),
                        Duration.ofSeconds(15));
        $(byText("Перепланировать")).click();
        $("[data-test-id=\"success-notification\"] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + secondDateDelivery),
                        Duration.ofSeconds(15));
    }

    @Test
    void shouldNoValidCity() {
        $("[data-test-id=city] .input__control").setValue(noValidEnUser.getCity());
        String dateDelivery = DataGenerator.generateDate(3);
        $("[data-test-id=date] .input__control").setValue(dateDelivery);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $(byText("Доставка в выбранный город недоступна")).should(visible);
    }

    @Test
    void shouldNoValidDate() {
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        String dateDelivery = NoValidDataGenerator.generateDate(3);
        $("[data-test-id=date] .input__control").setValue(dateDelivery);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $(byText("Заказ на выбранную дату невозможен")).should(visible);
    }

    @Test
    void shouldNoValidName() {
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        String dateDelivery = DataGenerator.generateDate(3);
        $("[data-test-id=date] .input__control").setValue(dateDelivery);
        $("[data-test-id=name] .input__control").setValue(noValidEnUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."))
                .should(visible);
    }

    @Test
    void shouldNoValidNoAgree() {
        $("[data-test-id=city] .input__control").setValue(validUser.getCity());
        String dateDelivery = DataGenerator.generateDate(3);
        $("[data-test-id=date] .input__control").setValue(dateDelivery);
        $("[data-test-id=name] .input__control").setValue(validUser.getName());
        $("[data-test-id=phone] .input__control").setValue(validUser.getPhone());
        $(byText("Запланировать")).click();
        $(byText("Я соглашаюсь с условиями обработки и использования моих персональных данных"))
                .should(visible);
    }

}