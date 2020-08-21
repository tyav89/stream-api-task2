import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Gensus {
    public static void main(String[] args) {
        long startTime;
        long result;
        long stopTime;
        double processTime;
        List<String> listResult;
        List<Person> listPersonResult;

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        startTime = System.nanoTime();
        result = calculate(persons); // Process time: 0.088073069 s
        stopTime = System.nanoTime();
        processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");

        startTime = System.nanoTime();
        result = calculateParallel(persons); // Process time: 0.064671692 s
        stopTime = System.nanoTime();
        processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");

        startTime = System.nanoTime();
        listResult = getListRecruit(persons);
        stopTime = System.nanoTime(); // Process time: 0.261211754 s
        processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");

        startTime = System.nanoTime();
        listResult = getListRecruitParalle(persons);
        stopTime = System.nanoTime(); // Process time: 0.147186427 s
        processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");

        startTime = System.nanoTime();
        listPersonResult = getListWorkable(persons); // Process time: 0.743252562 s
        stopTime = System.nanoTime();
        processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");

        startTime = System.nanoTime();
        listPersonResult = getListWorkableParallel(persons); // Process time: 0.915704935 s
        stopTime = System.nanoTime();
        processTime = (double) (stopTime - startTime) / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");

    }
    public static long calculate(Collection<Person> list) {
        long result;
        Stream<Person> minorStream = list.stream();
        result = minorStream.filter(x -> x.getAge() < 18).count();
        return result;
    }

    public static long calculateParallel(Collection<Person> list) {
        long result;
        Stream<Person> minorParallelStream = list.parallelStream();
        result = minorParallelStream.filter(x -> x.getAge() < 18).count();
        return result;
    }

    public static List<String> getListRecruit (Collection<Person> list) {
        List<String> listResult;
        Stream<Person> recruitStream = list.stream();
        listResult = recruitStream
                .filter(x -> x.getSex().equals(Sex.MAN))
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        return listResult;
    }

    public static List<String> getListRecruitParalle (Collection<Person> list) {
        List<String> listResult;
        Stream<Person> recruitParallelStream = list.parallelStream();
        listResult = recruitParallelStream
                .filter(x -> x.getSex().equals(Sex.MAN))
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        return listResult;
    }

    public static List<Person> getListWorkable (Collection<Person> list) {
        List<Person> listResult;
        Stream<Person> workableStream = list.stream();
        listResult = workableStream
                .filter(x -> x.getEducation().equals(Education.HIGHER))
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 60
                        || x.getSex().equals(Sex.MAN) && x.getAge() >= 18 && x.getAge() <= 65)
                .sorted(Comparator.comparing(Person::getFamily)).collect(Collectors.toList());
        return listResult;
    }

    public static List<Person> getListWorkableParallel (Collection<Person> list) {
        List<Person> listResult;
        Stream<Person> workableStreamParallel = list.parallelStream();
        listResult = workableStreamParallel
                .filter(x -> x.getEducation().equals(Education.HIGHER))
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 60
                        || x.getSex().equals(Sex.MAN) && x.getAge() >= 18 && x.getAge() <= 65)
                .sorted(Comparator.comparing(Person::getFamily)).collect(Collectors.toList());
        return listResult;
    }
}



