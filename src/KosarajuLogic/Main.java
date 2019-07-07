package com.kosaraju.logic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        //Главный объект алгоритма
        KosarajuAlgorithm ka = new KosarajuAlgorithm();

        List<String> v = new ArrayList<String>();

        //Добавление вершин в массив
        v.add("C");
        v.add("B");
        v.add("H");
        v.add("E");
        v.add("D");
        v.add("G");
        v.add("I");
        v.add("A");
        v.add("F");
        v.add("K");

        //Добавление ребер в список
        List<String[]> e = new ArrayList<String[]>();
        e.add(new String[]{"B", "C"});
        e.add(new String[]{"H", "B"});
        e.add(new String[]{"E", "H"});
        e.add(new String[]{"E", "D"});
        e.add(new String[]{"E", "G"});
        e.add(new String[]{"D", "H"});
        e.add(new String[]{"D", "A"});
        e.add(new String[]{"A", "I"});
        e.add(new String[]{"I", "C"});
        e.add(new String[]{"B", "D"});
        e.add(new String[]{"I", "D"});
        e.add(new String[]{"D", "H"});
        e.add(new String[]{"F", "A"});
        e.add(new String[]{"F", "G"});
        e.add(new String[]{"G", "K"});
        e.add(new String[]{"K", "F"});

        //Создания графа
        ka.createGraph(v, e);
        //Вызов метода выполнения алгоритма Косараю
        ka.Algorithm();
    }

}
