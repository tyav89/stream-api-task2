import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Collection<Person> persons = Arrays.asList(
                new Person("Jack", "Evans", 16, Sex.MAN, Education.SECONDARY),
                new Person("Connor", "Young", 23, Sex.MAN, Education.FURTHER),
                new Person("Emily", "Harris", 42, Sex.WOMEN, Education.HIGHER),
                new Person("Harry", "Wilson", 69, Sex.MAN, Education.HIGHER),
                new Person("George", "Davies", 45, Sex.MAN, Education.FURTHER),
                new Person("Samuel", "Adamson", 40, Sex.MAN, Education.HIGHER),
                new Person("John", "Brown", 44, Sex.MAN, Education.HIGHER)
        );

        Stream<Person> minorStream = persons.stream();
        Stream<Person> recruitStream = persons.stream();
        Stream<Person> workableStream = persons.stream();

        long count = minorStream.filter(x -> x.getAge() < 18).count();

        List<String> listRecruit = recruitStream
                .filter(x -> x.getSex().equals(Sex.MAN))
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList())
        ;

        List<Person> workableList = workableStream
                .filter(x -> x.getEducation().equals(Education.HIGHER))
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 60
                        || x.getSex().equals(Sex.MAN) && x.getAge() >= 18 && x.getAge() <= 65)
                .sorted(Comparator.comparing(Person::getFamily)).collect(Collectors.toList());
        ;

    }
}
