package io.panshi.config.agent;

public class Main {
    public static void main(String[] args){
        ConfigAgent.instance.start();
        Runtime.getRuntime().addShutdownHook(new Thread(ConfigAgent.instance::stop));
    }
}
