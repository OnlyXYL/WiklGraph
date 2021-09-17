package top.wikl.wikljava;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/4/29 0029 15:45
 */
public class Testa {

    public static void main(String[] args) {

        final ArrayList<String> strings = new ArrayList<>();

        strings.add("1");
        strings.add("1");
        strings.add("2");
        strings.add("");

        trueFirst();

        System.out.println("=============");

        falseFirst();
    }

    public static  void trueFirst (){


        boolean flag = true;

        System.out.println(flag|=true);
        System.out.println(flag|=false);

    }

    public static  void falseFirst (){


        boolean flag = false;

        System.out.println(flag|=true);
        System.out.println(flag|=false);

    }


    public static void testSet(List<String> strings){

        Set<String> list = new HashSet<>();
        boolean result = false;

        for (String s : strings) {
            result |= list.add(s);

            System.out.println(result);
        }
    }

    public void testList(List<String> strings){

        final ArrayList<String> list = new ArrayList<>();

        boolean result = false;

        for (String s : strings) {
            result |= list.add(s);

            System.out.println(result);
        }
    }

}

