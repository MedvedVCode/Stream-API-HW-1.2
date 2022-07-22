package populationCensusTask;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Marry", "Harry", "Anna", "Samuel", "Lisa");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();

        Instant start = Instant.now();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)]
            ));
        }
        Instant finish = Instant.now();
        System.out.println("Persons creation time, msec: " + Duration.between(start, finish).toMillis());

        start = Instant.now();
        Stream<Person> streamUnderage = persons.stream();
        long underage = streamUnderage.filter(person -> person.getAge() < 18).count();
        finish = Instant.now();
        System.out.println("Underage finding time, msec: " + Duration.between(start, finish).toMillis());

        start = Instant.now();
        Stream<Person> streamRecruit = persons.parallelStream();
        List<String> recruitFamily = streamRecruit.filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() < 27)
                .filter(person -> person.getSex() == Sex.MAN)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        finish = Instant.now();
        System.out.println("List of recruit families finding time, msec: " + Duration.between(start, finish).toMillis());

        start = Instant.now();
        Stream<Person> streamEducatedWorker = persons.parallelStream();
        List<Person> educatedWorker = streamEducatedWorker
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getAge() >= 18)
                .filter(person -> (person.getSex() == Sex.WOMAN && person.getAge() < 60) ||
                        (person.getSex() == Sex.MAN && person.getAge() < 65))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        finish = Instant.now();
        System.out.println("List of educated workers finding time, msec: " + Duration.between(start, finish).toMillis());
    }
}