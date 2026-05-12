package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DartsController {

    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private boolean gameStarted = false;
    
    // Liste der aktuellen 3 Darts als Objekte (für Punkte & Labels)
    private List<DartThrow> currentTurnDarts = new ArrayList<>();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("players", players);
        model.addAttribute("gameStarted", gameStarted);
        model.addAttribute("currentTurnDarts", currentTurnDarts);
        
        // Summe für die Anzeige berechnen
        int turnSum = currentTurnDarts.stream().mapToInt(DartThrow::getPoints).sum();
        model.addAttribute("turnSum", turnSum);

        if (gameStarted && !players.isEmpty()) {
            model.addAttribute("activePlayer", players.get(currentPlayerIndex));
        }
        return "index";
    }

    @PostMapping("/setup")
    public String setup(@RequestParam String name1, @RequestParam String name2) {
        players.clear();
        players.add(new Player(name1));
        players.add(new Player(name2));
        currentPlayerIndex = 0;
        gameStarted = true;
        currentTurnDarts.clear();
        return "redirect:/";
    }

    @PostMapping("/addDart")
    public String addDart(@RequestParam int value, @RequestParam int multiplier) {
        if (gameStarted && currentTurnDarts.size() < 3) {
            currentTurnDarts.add(new DartThrow(value, multiplier));
        }
        return "redirect:/";
    }

    @PostMapping("/removeDart")
    public String removeDart(@RequestParam int index) {
        if (index >= 0 && index < currentTurnDarts.size()) {
            currentTurnDarts.remove(index);
        }
        return "redirect:/";
    }

    @PostMapping("/confirmTurn")
    public String confirmTurn() {
        if (gameStarted && !currentTurnDarts.isEmpty()) {
            Player current = players.get(currentPlayerIndex);
            int total = currentTurnDarts.stream().mapToInt(DartThrow::getPoints).sum();
            
            current.calculateScore(total);
            
            currentTurnDarts.clear();
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
        return "redirect:/";
    }

    @PostMapping("/reset")
    public String reset() {
        players.clear();
        gameStarted = false;
        currentTurnDarts.clear();
        currentPlayerIndex = 0;
        return "redirect:/";
    }
}