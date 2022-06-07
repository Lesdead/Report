package delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private DataGenerator() {
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }

    private static final Faker faker = new Faker(new Locale("ru"));


    public static String generateCity() {
        String[] cities = {"Москва", "Иркутск", "Уфа", "Тула", "Курск"};
        String city = cities[new Random().nextInt(cities.length)];
        return city;
    }

    public static String generateName(String locale) {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        return firstName + " " + lastName;
        //Потому что Faker иногда выдает тройное имя, а для формы оно не валидное
    }

    public static String generatePhone(String locale) {
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            UserInfo user = new UserInfo(
                    DataGenerator.generateCity(),
                    DataGenerator.generateName(locale),
                    DataGenerator.generatePhone(locale));
            return user;
        }
    }

}