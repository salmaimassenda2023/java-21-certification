package com.certification.arrays.copying;

import com.certification.arrays.model.Person;
import java.util.Arrays;

public class A2_ShallowVsDeep {

    static Person[] deepCopy(Person[] src) {
        Person[] copy = new Person[src.length];
        for (int i = 0; i < src.length; i++) {
            copy[i] = (src[i] == null) ? null : new Person(src[i].name);
        }
        return copy;
    }
    public static void main(String[] args) {
        Person[] people ={new Person("SALMA"),new Person("ALI"),};
        Person[] clone= people.clone();
        Person[] copyOf = Arrays.copyOf(people,2);



        people[0].name="FADWA";
        people[1] = new Person("New");

        System.out.println(people[0]+""+clone[0]+""+copyOf[0]);
        System.out.println(people[1]+""+clone[1]+""+copyOf[1]);

    }
}
