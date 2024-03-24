package main.java.server.handler;

public interface MyHandlerAdapter {

    boolean support(Object handler);
    void process();

}
