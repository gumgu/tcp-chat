package main.java.server.dispatch;

import main.java.server.handler.*;
import main.java.server.manager.RoomManager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dispatcher {

    Socket socket;
    RoomManager roomManager;

    private final Map<String, MyHandlerAdapter> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public Dispatcher(Socket socket) {
        this.socket = socket;
        this.roomManager = new RoomManager();
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("FILE/LIST", new FileListHandler());
        handlerMappingMap.put("FILE/UPLOAD", new FileUploadHandler());
        handlerMappingMap.put("FILE/DOWNLOAD", new FileDownloadHandler());
        handlerMappingMap.put("CHAT/LIST", new ChatListHandler(roomManager));
        handlerMappingMap.put("CHAT/CREATE", new ChatCreateHandler(roomManager));
        handlerMappingMap.put("CHAT/ENTER", new ChatEnterHandler(roomManager));
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
