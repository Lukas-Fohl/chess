import framework.*;
import bot.*;

import java.util.List;

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
        }
    }

    private static move getgoodMove(board boardIn,color colIn){
        moves m = new moves();
        List<movePref> temp = m.getMoves(boardIn, colIn);
        for(int i = 0; i < temp.size(); i++){
            System.out.println(temp.get(i).rating);
        }
        return temp.get(temp.size()-1).move_;
    }
}