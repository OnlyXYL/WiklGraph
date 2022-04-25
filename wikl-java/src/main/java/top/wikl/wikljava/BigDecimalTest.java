package top.wikl.wikljava;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author XYL
 * @version 1.2
 * @date 2022/03/09 18:24
 */
public class BigDecimalTest {

    public static void main(String[] args) {

        final BigDecimal decimal = new BigDecimal("1.0");

        final BigDecimal decimal1 = new BigDecimal("1.0");

        if (decimal.equals(decimal1)) {

            System.out.println("true");
        } else {
            System.out.println("false");
        }


        final ArrayList<String> strings = new ArrayList<>();

        strings.add("a");

    }
}
