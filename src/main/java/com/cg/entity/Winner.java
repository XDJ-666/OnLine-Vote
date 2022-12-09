package com.cg.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Winner {
    private int battleId;
    private int winner;
    private String playerName;
    private float winnerScore;
}
