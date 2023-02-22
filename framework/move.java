package framework;

public class move{
    public int[] startPos = new int[2];
    public int[] endPos = new int[2];
    public board currentBoard = new board();
    public move(board boardIn, int[] Pos1, int[] Pos2){
        currentBoard = boardIn;
        startPos = Pos1;
        endPos = Pos2;
    }

    public boolean moveOkay(){
        for (move m: currentBoard.possibleMoves(startPos)){
            if(endPos[0] == m.endPos[0] && endPos[1] == m.endPos[1]){
                return true;
            }
        }
        return false;
    }
}