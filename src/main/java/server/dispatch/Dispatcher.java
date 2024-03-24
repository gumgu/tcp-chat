package main.java.server.dispatch;

import main.java.server.handler.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dispatcher {

    Socket socket;

    private final Map<String, MyHandlerAdapter> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public Dispatcher(Socket socket) {
        this.socket = socket;
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("FILE/LIST", new FileListHandler());
        handlerMappingMap.put("FILE/UPLOAD", new FileUploadHandler());
        handlerMappingMap.put("FILE/DOWNLOAD", new FileDownloadHandler());
        handlerMappingMap.put("CHAT/LIST", new ChatListHandler());
        handlerMappingMap.put("CHAT/CREATE", new ChatCreateHandler());
        handlerMappingMap.put("CHAT/ENTER", new ChatEnterHandler());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new FileListHandler());
    }

    public void run(String protocol) {
        MyHandlerAdapter handler = getHandler(protocol);
        handler.process();
    }

    private MyHandlerAdapter getHandler(String protocol) {
        return handlerMappingMap.get(protocol);
    }

}
