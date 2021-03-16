package com.nunes.java.processmentoassincronoparalelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class FutureExemplo {
    private static final ExecutorService pessoaParaExecutarAtividade = Executors.newFixedThreadPool(3);
    public static void main(String[] args) throws InterruptedException{
        Casa casa = new Casa(new Quarto());
        //casa.obterAfazeresDoComodo().forEach(atividade ->
        //        pessoaParaExecutarAtividade.execute(() -> atividade.realizar()));
        //pessoaParaExecutarAtividade.shutdown();
        List<Future<String>> futuros = new CopyOnWriteArrayList( casa.obterAfazeresDoComodo().stream()
                .map(atividade -> pessoaParaExecutarAtividade.submit(() ->
                        {
                            atividade.realizar();

                        }
                        ))

                .collect(Collectors.toList()));

        while (true){
            int numeroDeAtividadesNaoFinalizadas = 0;
            for(Future<?> futuro : futuros){
                if (futuro.isDone()){
                    try {
                        System.out.println("Parabens voce terminou de: " + futuro.get());
                        futuros.remove(futuro);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }catch (ExecutionException e){
                        e.printStackTrace();
                    }

                }
                else {
                    numeroDeAtividadesNaoFinalizadas++;
                }
            }
            if(futuros.stream().allMatch(Future::isDone)){
                break;
            }
            Thread.sleep(1000);
            System.out.println("Numero de atividades nao finalizadas:" + numeroDeAtividadesNaoFinalizadas);

        }
        pessoaParaExecutarAtividade.shutdown();
    }
}

class Casa{
    private List<Comodo> comodos;
    Casa(Comodo... comodos) {this.comodos = Arrays.asList(comodos);}
    List<Atividade> obterAfazeresDoComodo(){
        return this.comodos.stream().map(Comodo::obterAfazeresDoComodo)
                .reduce(new ArrayList<Atividade>(), (pivo, atividades) ->{
                    pivo.addAll(atividades);
                    return pivo;
                });
    }
}

@FunctionalInterface
interface Atividade{
    void realizar();
}

abstract class Comodo{
    abstract List<Atividade> obterAfazeresDoComodo();
}
class espera{
    public void sleep() {

        try {
            long sleep = (long) (Math.random() * 10 * 1000);
            System.out.println("Esperando " + (sleep / 1000)+ " segundos para executar a thread ");
            Thread.sleep(sleep);

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}

class Quarto extends Comodo{
    @Override
    List<Atividade> obterAfazeresDoComodo(){
        return Arrays.asList(
              this::arrumarGuardaRoupa,
              this::varrerQuarto,
              this::getArrumarCama

        );
    }


    private String arrumarGuardaRoupa(){
        String arrumar_o_guarda_roupa = "arrumar o guarda roupa";
        espera Espera = new espera();
        Espera.sleep();
        System.out.println(arrumar_o_guarda_roupa);
        return arrumar_o_guarda_roupa;
    }
    private String varrerQuarto(){
        String varrer_o_quarto = "varrer o quarto";
        espera Espera = new espera();
        Espera.sleep();
        System.out.println(varrer_o_quarto);
        return varrer_o_quarto;
    }
    private String getArrumarCama(){
        String Arrumar_a_cama = "Arrumar a cama";
        espera Espera = new espera();
        Espera.sleep();
        System.out.println(Arrumar_a_cama);
        return Arrumar_a_cama;
    }
}