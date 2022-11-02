package netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderingACardTest {

    @BeforeEach
    void openURL() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSendARequest() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Тарас Бульба-Гоголь");
        form.$("[data-test-id=phone] input").setValue("+79781234567");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSendARequestMistakeName() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Ivan Ivanov");
        form.$("[data-test-id=phone] input").setValue("+79781234567");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSendARequestMistakePhone() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Тарас Бульба-Гоголь");
        form.$("[data-test-id=phone] input").setValue("-79781234567");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendARequestMistakeEmptyValue() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$("[type=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendARequestMistakeWithoutACheckbox() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Тарас Бульба-Гоголь");
        form.$("[data-test-id=phone] input").setValue("+79781234567");
        form.$("[type=button]").click();
        $("[class=checkbox__text]").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}
