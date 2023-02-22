import framework.*;
import bot.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.border.Border;

public class Main {
    public static void main(String[] args) {
        board f = new board();
        f.printBoard();
        boolean running = true;
        //game loop
        while(running){
            System.out.println("enter a move (\"0,1;0,2\") or ask for a move (\"mB) or (\"mW\")");
            String inp = System.console().readLine();
            if(inp.split(";").length>1){
                int[] pos1 = new int[]{Integer.parseInt((inp.split(";")[0]).split(",")[0]),Integer.parseInt((inp.split(";")[0]).split(",")[1])};
                int[] pos2 = new int[]{Integer.parseInt((inp.split(";")[1]).split(",")[0]),Integer.parseInt((inp.split(";")[1]).split(",")[1])};
                move m = new move(f,pos1,pos2);
                if(m.moveOkay()){
                    f.applyMove(m);
                }
            }else if(inp.contains("e")){
                running = false;
                continue;
                //end
            }else if(inp.contains("mB")){
                //move for black
                f.applyMove(getgoodMove(f,color.black));
            }else if(inp.contains("mW")){
                f.applyMove(getgoodMove(f,color.white));
            }
            f.printBoard(); 
            running = gameEnded(f);
        }
    }

    private static move getgoodMove(board boardIn,color colIn){
        moves m = new moves();
        List<movePref> temp = m.getMoves(boardIn, colIn);
        return temp.get(temp.size()-1).move_;
    }

    private static boolean gameEnded(board bIn){
        int[] kingPos = new int[]{20,20};
        for(int x = 0; x <= 7; x++){
            for(int y = 0; y <= 7; y++){
                if(bIn.boardContent[x][y].TypeOfPiece == piecesType.king){
                    kingPos = new int[]{x,y};
                }
            }
        }
        if(kingPos[0] == 20){
            return false;
        }
        return true;
    }

    private static move getFromMultiple(board boardIn,color colIn){
        moves m = new moves();
        //switch  between black and white
        //linked list
        //check for 3 moves
        //process:
        //  -loop through height of
        List<movePref> temp = m.getMoves(boardIn, colIn);
        node Parent = new node(0, null, null, null);
        new node(0,temp.get(temp.size()-1).move_,Parent,new ArrayList<node>());
        new node(0,temp.get(temp.size()-2).move_,Parent,new ArrayList<node>());
        new node(0,temp.get(temp.size()-3).move_,Parent,new ArrayList<node>());
        for(int height = 1; height < 4; height++){
            color tempCol = (height%2!=0)?colIn:((colIn==color.white)?color.black:color.white);
            List<movePref> temp2 = m.getMoves(boardIn, colIn);
            for(int i = 1; i < 4; i++){
                new node(height,temp.get(temp.size()-i).move_,null,null);
            }
        }
        return new move(boardIn, null, null);
    }
}