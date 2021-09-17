package top.wikl.wikljava.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Optional测试
 *
 * @author XYL
 * @version 1.2
 * @since 2020/10/21 11:33
 */
public class OptionalTest {

    public static void main(String[] args) {

        ArrayList<String> strings = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();

        final User user = new User();

        user.setName("ccc");

        users.add(user);
        strings.add("1");

        final String s1 = Optional.ofNullable(users).map(s -> Optional.ofNullable(s.stream().findFirst().orElse(null)).map(User::getName).orElse("空对象")).orElse("空");

        System.out.println(s1);

        final List<String> list = Optional.ofNullable(strings).map(n -> n.stream().limit(2).collect(Collectors.toList())).orElse(null);

        final List<String> collect = list.stream().filter(r -> !strings.contains(r)).collect(Collectors.toList());

        System.out.println(collect.toString());

    }

}
