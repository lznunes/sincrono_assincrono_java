package com.nunes.java.processmentoassincronoparalelo;

public class ThreadExemplo {
    public static void main(String[] args){
        //barradecarregamento Barradecarregamento = new barradecarregamento();
        //Barradecarregamento.run();
        //barradecarregamento2 Barradecarregamento2 = new barradecarregamento2();
        //Barradecarregamento2.run();
        //gerarpdf GerarPDF = new gerarpdf();
        //GerarPDF.run();
        gerarpdf iniciarGerarPDF1 = new gerarpdf();
        gerarpdf iniciarGerarPDF2 = new gerarpdf();
        barradecarregamento3 Barradecarregamento3 = new barradecarregamento3(iniciarGerarPDF1);
        barradecarregamento3 Barradecarregamento4 = new barradecarregamento3(iniciarGerarPDF2);
        iniciarGerarPDF1.start();
        iniciarGerarPDF2.start();
        Barradecarregamento3.start();
        Barradecarregamento4.start();

    }
}

class barradecarregamento extends Thread {
    @Override
    public void run(){
        super.run();
        System.out.println("Barra de carregamento 1: " + this.getName() + " em execucao!");
        try {
            Thread.sleep( 5000);
            System.out.println("Barra de carregamento 1 finalizda!");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class barradecarregamento2 extends Thread {
    @Override
    public void run(){
        super.run();
        System.out.println("Barra de carregamento 2: " + this.getName() + " em execusao!");
        try {
            long sleep = (long) (Math.random() * 10 * 100);
            System.out.println("Barra de carregamento 2 aguardando " + (sleep / 1000) + " segundos");
            Thread.sleep(sleep);
            System.out.println("Barra de carregamento 2 finalizada!");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class gerarpdf extends Thread{
    @Override
    public void run(){
        System.out.println("Gerar PDF iniciado " + this.getName());
        try{
            long sleep = (long) (Math.random() * 10 * 1000);
            System.out.println("Gerando PDF em " + (sleep/ 1000) + " segundos..");
            Thread.sleep(sleep);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Gerar PDF " + this.getName() + " finalizado");
    }
}

class barradecarregamento3 extends Thread{
    private Thread iniciaGerarPDF;
    public  barradecarregamento3(Thread iniciaGerarPDF){
        this.iniciaGerarPDF = iniciaGerarPDF;
    }


    @Override
    public void run(){
        while (true){
            try {
                Thread.sleep(1000);
                if (!iniciaGerarPDF.isAlive()){
                    break;
                }
                System.out.println("Loadin " + iniciaGerarPDF.getName() + "...");
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }
}