package framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.lang.Math;

public class Main {
    public static void main(String[] args) {
        /*
        board n = new board();
        n.printBoard();
        int[] poss = new int[] {1,0};
        System.out.println(n.possibleMoves(poss).size());
        if(n.possibleMoves(poss).size()>0) {
            n.applyMove(n.possibleMoves(poss).get(0));
        }
        n.printBoard();
        */
    }
}
/*
board:
new board();                                board with standard positions
board.content[][]                           8*8 stores pieces
board.isInDanger(new int[] {int,in});       looks if pieces can be captured
board.applyMove(new move());                applays provided move
board.possibleMoves(new int[] {int,in});    returns all possible moves for the provided position
board.printBoard();                         prints out current board

move:
move(board,new int[] {int,in},new int[] {int,in})
                                            enter board, start- and end- Position
move.moveOkay()                             returns bool --> does the move follow the rules

pieces:
pieces(color,type)                          creates new piece --> can be placed at board.content
*/