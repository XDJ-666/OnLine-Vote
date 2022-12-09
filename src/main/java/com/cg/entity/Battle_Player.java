package com.cg.entity;

import lombok.Data;
import org.springframework.stereotype.Component;
@Component
@Data
public class Battle_Player {
    private int playerId;
    private int battleId;
    private String phone;
    private String playerName;
    private String image;
    private float scoreA;
    private float scoreB;
    private float totalA;
    private float totalB;
    private int pollA;
    private int pollB;

}
