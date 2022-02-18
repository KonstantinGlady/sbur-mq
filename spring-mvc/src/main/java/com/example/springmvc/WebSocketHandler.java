package com.example.springmvc;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessionList = new ArrayList<>();
    @NonNull
    private AircraftRepository repository;

    public List<WebSocketSession> getSessionList() {
        return sessionList;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);
        System.out.println("Connection established from " + session.toString() +
                " @ " + Instant.now().toString());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            System.out.println("message received " + message + " from " + session.toString());
            for (WebSocketSession sessionInList : sessionList) {
                if (sessionInList != session) {
                    sessionInList.sendMessage(message);
                    System.out.println("--> sending message " + message + " to " + sessionList.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Exception handling message " + e.getLocalizedMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        System.out.println("Connection closed by " + session.toString() +
                " @ " + Instant.now().toString());

    }
}
