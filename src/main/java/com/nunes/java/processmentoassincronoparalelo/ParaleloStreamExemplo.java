package com.nunes.java.processmentoassincronoparalelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParaleloStreamExemplo {
    public static void main(String[] args){
//        long inicio = System.currentTimeMillis();
//        IntStream.range(1,100000).forEach(num -> fatorial(num));
//        long fim = System.currentTimeMillis();
//        System.out.println("Tempo de execucao: " + (long) (fim - inicio) + " segundos");

//        long inicio1 = System.currentTimeMillis();
//        IntStream.range(1,100000).parallel().forEach(num -> fatorial(num));
//       long fim1 = System.currentTimeMillis();
//        System.out.println("Tempo de execucao: " + (long) (fim1 - inicio1)  + " segundos");

        List<String> nomes = new ArrayList<>();
        nomes.add("Luiz");
        nomes.add("Antonio");
        nomes.add("Nunes");
        nomes.add("Neves");
        nomes.parallelStream().forEach(nome -> {
            System.out.println("Processou " + nome);
        });


    }
    public static long fatorial(long num){
        long fat =1;
        for (long i = 2; i <= num; i++){
            fat =+ i;
        }
        return fat;
    }

}
