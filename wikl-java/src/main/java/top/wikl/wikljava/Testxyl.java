package top.wikl.wikljava;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/8/23 0023 17:14
 */
public class Testxyl {

    private final static Pattern REGEX_CONCEPT_CLASS = Pattern.compile("^[对象类，]?$");


    public static void main(String[] args) {

        String str = "员工";


        t();


    }

    public static boolean validConceptType(String str) {
        final Matcher matcher = REGEX_CONCEPT_CLASS.matcher(str);

        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }


    public static void t(){

        final List<Integer> list = Arrays.asList(1, 2, 3, 4);

        final Stream<Integer> stream = list.stream().filter(s -> s < 1);

        final Integer integer = Optional.ofNullable(list.stream().filter(s -> s < 1)).map(a -> a.findFirst().orElse(null)).orElse(null);

        final Optional<Integer> first = list.stream().filter(s -> s < 1).findFirst();

        System.out.println("");

    }
}
