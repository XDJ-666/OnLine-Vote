package com.cg.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Player {
  private int playerId;
  private String phone;
  private String playerName;
  private String image;



}
