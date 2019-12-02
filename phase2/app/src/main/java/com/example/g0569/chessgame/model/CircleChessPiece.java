package com.example.g0569.chessgame.model;

/** The type Circle chess piece. */
public class CircleChessPiece extends ChessPiece {
  /**
   * Instantiates a new Circle chess piece.
   *
   * @param x The x coordinate for chess piece.
   * @param y The y coordinate for chess piece.
   */
  CircleChessPiece(float x, float y) {
        super(x, y);
    }

    @Override
    public Integer[][] createTargetList() {
        Integer[][] target = new Integer[1][2];
        float column = this.getCoordinate().getX();
        if(column == (1 | 2)){// this means this chess piece is on player's side.
            // Circle piece can only attack enemy chess piece in "frontline"
            target[0][0] = 3;
        }
        else {// this means this chess piece is on NPC's side.
            target[0][0] = 2;
        }
        // Circle piece can only attack chess piece in the same row
        target[0][1] = this.getCoordinate().getIntY();
        return target;
    }
}
