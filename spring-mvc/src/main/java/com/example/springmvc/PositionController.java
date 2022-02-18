package com.example.springmvc;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Controller
public class PositionController {
    @NonNull
    private final com.example.springmvc.AircraftRepository repository;
    private WebClient client = WebClient.create("http://localhost:7634/aircraft");

    @GetMapping("/aircraft")
    public String getCurrentAircraftPosition(Model model) {
        repository.deleteAll();

        client.get()
                .retrieve()
                .bodyToFlux(com.example.springmvc.Aircraft.class)
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(repository::save);

        model.addAttribute("currentPositions", repository.findAll());
        return "positions";
    }
}