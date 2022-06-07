package delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class NoValidDataGenerator {

    private NoValidDataGenerator() {
    }

    @Value
    public static class UserNoValidInfo {
        String city;
        String name;
    }

    private static final Faker fakerEn = new Faker(new Locale("en"));


    public static String generateCity() {
        String[] cities = {"Moscow", "Irkutsk", "Ufa", "Tel Aviv", "New York"};
        String city = cities[new Random().nextInt(cities.length)];
        return city;
    }

    public static String generateName(String locale) {
        String firstName = fakerEn.name().firstName();
        String lastName = fakerEn.name().lastName();
        return firstName + " " + lastName;
    }

    public static String generateDate(int shift) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserNoValidInfo generateUser(String locale) {
            UserNoValidInfo user = new UserNoValidInfo(
                    NoValidDataGenerator.generateCity(),
                    NoValidDataGenerator.generateName(locale));
            return user;
        }
    }

}