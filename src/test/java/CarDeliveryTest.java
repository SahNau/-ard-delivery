import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.*;

public class CarDeliveryTest {
    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @Test
    void shouldValidateData() {
        $("[data-test-id=\"city\"] [class=\"input__control\"]").setValue("Москва");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(3).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [class=\"input__control\"]").setValue(date);
        $("[data-test-id=\"name\"] [class=\"input__control\"]").setValue("Александр");
        $("[data-test-id=\"phone\"] [class=\"input__control\"]").setValue("+79325459322");
        $("[data-test-id=\"agreement\"] [class=\"checkbox__box\"]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=\"notification\"] .notification__content").shouldHave(text(date));
    }

    @Test
    void shouldErrorInvalidCity() {
        $("[data-test-id=\"city\"] [class=\"input__control\"]").setValue("Подмосковье");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(3).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [class=\"input__control\"]").setValue(date);
        $("[data-test-id=\"name\"] [class=\"input__control\"]").setValue("Александр");
        $("[data-test-id=\"phone\"] [class=\"input__control\"]").setValue("+79325459322");
        $("[data-test-id=\"agreement\"] [class=\"checkbox__box\"]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(("[data-test-id=\"city\"].input_invalid .input__sub")).shouldBe(visible)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldErrorIfDayToDay() {
        $("[data-test-id=\"city\"] [class=\"input__control\"]").setValue("Москва");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [class=\"input__control\"]").setValue(date);
        $("[data-test-id=\"name\"] [class=\"input__control\"]").setValue("Александр");
        $("[data-test-id=\"phone\"] [class=\"input__control\"]").setValue("+79325459322");
        $("[data-test-id=\"agreement\"] [class=\"checkbox__box\"]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(("[data-test-id=\"date\"] .input_invalid .input__sub")).shouldBe(visible)
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldErrorIfInvalidName() {
        $("[data-test-id=\"city\"] [class=\"input__control\"]").setValue("Москва");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(3).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [class=\"input__control\"]").setValue(date);
        $("[data-test-id=\"name\"] [class=\"input__control\"]").setValue("Alexandr");
        $("[data-test-id=\"phone\"] [class=\"input__control\"]").setValue("+79325459322");
        $("[data-test-id=\"agreement\"] [class=\"checkbox__box\"]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(("[data-test-id=\"name\"].input_invalid .input__sub")).shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldErrorIfInvalidPhone() {
        $("[data-test-id=\"city\"] [class=\"input__control\"]").setValue("Москва");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(3).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [class=\"input__control\"]").setValue(date);
        $("[data-test-id=\"name\"] [class=\"input__control\"]").setValue("Александр");
        $("[data-test-id=\"phone\"] [class=\"input__control\"]").setValue("79325459322");
        $("[data-test-id=\"agreement\"] [class=\"checkbox__box\"]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(("[data-test-id=\"phone\"].input_invalid .input__sub")).shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldErrorIfCheckboxOff() {
        $("[data-test-id=\"city\"] [class=\"input__control\"]").setValue("Москва");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().plusDays(3).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [class=\"input__control\"]").setValue(date);
        $("[data-test-id=\"name\"] [class=\"input__control\"]").setValue("Александр");
        $("[data-test-id=\"phone\"] [class=\"input__control\"]").setValue("+79325459322");
        $$("button").find(exactText("Забронировать")).click();
        $(("[data-test-id=\"agreement\"].input_invalid")).shouldBe(visible);
        String color = $(("[data-test-id=\"agreement\"].input_invalid .checkbox__text")).getCssValue("color");
        assertEquals("rgba(255, 92, 92, 1)", color);
    }

    @Test
    void shouldErrorIfPastDate() {
        $("[data-test-id=\"city\"] [class=\"input__control\"]").setValue("Москва");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=\"date\"] [class=\"input__control\"]").sendKeys(Keys.BACK_SPACE);
        String date = LocalDate.now().minusDays(1).format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id=\"date\"] [class=\"input__control\"]").setValue(date);
        $("[data-test-id=\"name\"] [class=\"input__control\"]").setValue("Александр");
        $("[data-test-id=\"phone\"] [class=\"input__control\"]").setValue("+79325459322");
        $("[data-test-id=\"agreement\"] [class=\"checkbox__box\"]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(("[data-test-id=\"date\"] .input_invalid .input__sub")).shouldBe(visible)
                .shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }
}
