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
        //save values in 2d order ( f(x) = 2^(x) ) -> point hash map ([x,y] as index and board as value)
        for(int height = 0; height < 4; height++){
            if(height == 0){
                //add boardIn at (0;1)
            }else{
                color tempCol = (height%2!=0)?colIn:((colIn==color.white)?color.black:color.white);
                //int x = height;
                int YMAX = (int)Math.pow(2,height);
                for(int i = 0; i < YMAX; i++){
                    //get last              done
                    //get move              
                    //get point to save at  done
                    int last[] = {height--, ((i%2==0)?i:i++)/2,};
                    int savePint[] = {height,i};
                    moves m = new moves();
                    //get last move --> applay to board and loop for next good one

                }
                //save value at x,y

            }
        }
        return new move(boardIn, null, null);
    }
}