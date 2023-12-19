package com.yy.controller;

import com.alibaba.fastjson2.JSONArray;
import com.yy.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.*;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/game")
    public String test(String name, String camp, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (name == null || name.equals("") || name.equals("undefined"))
            return null;
        return gameService.drawLots(name, camp);
    }


    @GetMapping("/result")
    public JSONArray result() {
        return gameService.result();
    }

    @GetMapping("/reset")
    public String reset() {
        gameService.reset();
        return "success";
    }

    @GetMapping("/test")
    public JSONArray test() {
        return gameService.test();
    }
}
