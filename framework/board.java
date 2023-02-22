package framework;

import java.io.*;
import java.util.*;
import java.lang.Math;

public class board implements Cloneable{
    //[0][0] = black
    public pieces[][] boardContent = new pieces[8][8];
    public board(){
        for(int i = 0; i < 8; i++){
            boardContent[i][1] = new pieces(color.white, piecesType.pawn);
        }
        for(int i = 0; i < 8; i++){
            boardContent[i][6] = new pieces(color.black, piecesType.pawn);
        }

        boardContent[0][0] = new pieces(color.white, piecesType.rook);
        boardContent[7][0] = new pieces(color.white, piecesType.rook);
        boardContent[0][7] = new pieces(color.black, piecesType.rook);
        boardContent[7][7] = new pieces(color.black, piecesType.rook);

        boardContent[1][0] = new pieces(color.white, piecesType.knight);
        boardContent[6][0] = new pieces(color.white, piecesType.knight);
        boardContent[1][7] = new pieces(color.black, piecesType.knight);
        boardContent[6][7] = new pieces(color.black, piecesType.knight);

        boardContent[2][0] = new pieces(color.white, piecesType.bishop);
        boardContent[5][0] = new pieces(color.white, piecesType.bishop);
        boardContent[2][7] = new pieces(color.black, piecesType.bishop);
        boardContent[5][7] = new pieces(color.black, piecesType.bishop);

        boardContent[4][0] = new pieces(color.white, piecesType.king);
        boardContent[3][0] = new pieces(color.white, piecesType.queen);

        boardContent[4][7] = new pieces(color.black, piecesType.king);
        boardContent[3][7] = new pieces(color.black, piecesType.queen);

        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 8; x++){
                boardContent[x][y+2] = new pieces(color.empty, piecesType.empty);
            }
        }
    }

    public boolean isInDanger(int[] pos){
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                if(this.boardContent[x][y].TypeOfPiece != piecesType.empty && this.boardContent[pos[0]][pos[1]].TypeOfColor != this.boardContent[x][y].TypeOfColor){
                    for(move m: this.possibleMoves(new int[] {x,y})){
                        if(m.endPos[0] == pos[0] && m.endPos[1] == pos[1]){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void applyMove(move moveIn){
        if((moveIn.startPos[1] == 1 || moveIn.startPos[1] == 6) &&
            (boardContent[moveIn.startPos[0]][moveIn.startPos[1]].TypeOfPiece == piecesType.pawn) &&
            (moveIn.endPos[1] == 7 || moveIn.endPos[1] == 0)){
                boardContent[moveIn.endPos[0]][moveIn.endPos[1]] = new pieces(boardContent[moveIn.startPos[0]][moveIn.startPos[1]].TypeOfColor, piecesType.queen);
                boardContent[moveIn.startPos[0]][moveIn.startPos[1]] = new pieces(color.empty, piecesType.empty);
        }else{
            boardContent[moveIn.endPos[0]][moveIn.endPos[1]] = boardContent[moveIn.startPos[0]][moveIn.startPos[1]];
            boardContent[moveIn.startPos[0]][moveIn.startPos[1]] = new pieces(color.empty, piecesType.empty);
        }
    }

    public List<move> possibleMoves(int[] pos){
        List<move> reVal = new ArrayList<move>();
        color curCol = this.boardContent[pos[0]][pos[1]].TypeOfColor;
        switch(this.boardContent[pos[0]][pos[1]].TypeOfPiece){
            case pawn:
                color thisCol = color.white;
                if(pos[1]==1 && this.boardContent[pos[0]][pos[1]+2].TypeOfPiece==piecesType.empty && this.boardContent[pos[0]][pos[1]+1].TypeOfPiece==piecesType.empty && this.boardContent[pos[0]][pos[1]].TypeOfColor == thisCol){
                    move temp = new move(this, pos, new int[] {pos[0],pos[1]+2});
                    reVal.add(temp);
                }
                if(this.boardContent[pos[0]][pos[1]].TypeOfColor == thisCol) {
                    if(pos[1]<7){
                        if (this.boardContent[pos[0]][pos[1]+1].TypeOfPiece == piecesType.empty) {
                            move temp = new move(this, pos, new int[] {pos[0],pos[1]+1});
                            reVal.add(temp);
                        }
                    }
                    if (pos[0] < 7 && pos[1] < 7) {
                        if (this.boardContent[pos[0]+1][pos[1]+1].TypeOfPiece != piecesType.empty && this.boardContent[pos[0]+1][pos[1]+1].TypeOfColor != thisCol) {
                            move temp = new move(this, pos, new int[] {pos[0]+1,pos[1]+1});
                            reVal.add(temp);
                        }
                    }
                    if (pos[0] > 0 && pos[1] < 7) {
                        if (this.boardContent[pos[0]-1][pos[1]+1].TypeOfPiece != piecesType.empty && this.boardContent[pos[0]-1][pos[1]+1].TypeOfColor != thisCol) {
                            move temp = new move(this, pos, new int[] {pos[0]-1,pos[1]+1});
                            reVal.add(temp);
                        }
                    }
                }
                thisCol = color.black;
                if(pos[1]==6 && this.boardContent[pos[0]][pos[1]-2].TypeOfPiece==piecesType.empty && this.boardContent[pos[0]][pos[1]-1].TypeOfPiece==piecesType.empty && this.boardContent[pos[0]][pos[1]].TypeOfColor == thisCol){
                    move temp = new move(this, pos, new int[] {pos[0],pos[1]-2});
                    reVal.add(temp);
                }
                if(this.boardContent[pos[0]][pos[1]].TypeOfColor == thisCol) {
                    if(pos[1]>0){
                        if (this.boardContent[pos[0]][pos[1]-1].TypeOfPiece == piecesType.empty) {
                            move temp = new move(this, pos, new int[] {pos[0],pos[1]-1});
                            reVal.add(temp);
                        }
                    }
                    if(pos[0]<0 && pos[1]>0){
                        if (this.boardContent[pos[0]-1][pos[1]-1].TypeOfPiece != piecesType.empty && this.boardContent[pos[0]-1][pos[1]-1].TypeOfColor != thisCol) {
                            move temp = new move(this, pos, new int[] {pos[0]-1,pos[1]-1});
                            reVal.add(temp);
                        }
                    }
                    if(pos[0]<7 && pos[1]>0){
                        if (this.boardContent[pos[0]+1][pos[1]-1].TypeOfPiece != piecesType.empty && this.boardContent[pos[0]+1][pos[1]-1].TypeOfColor != thisCol) {
                            move temp = new move(this, pos, new int[] {pos[0]+1,pos[1]-1});
                            reVal.add(temp);
                        }
                    }
                }
                break;
            case rook:
                reVal.addAll(rookMoves(pos,curCol));
                break;
            case king:
                for(int x_ = 0; x_ < 3; x_ ++){
                    for(int y_ = 0; y_ < 3; y_ ++){
                        int x = x_-1;
                        int y = y_-1;
                        if(pos[0]+x <= 7 && pos[0]+x >= 0 && pos[1]+y <= 7 && pos[1]+y >= 0){
                            if(boardContent[pos[0]+x][pos[1]+y].TypeOfColor != curCol){
                                move temp = new move(this,pos,new int[] {pos[0]+x,pos[1]+y});
                                reVal.add(temp);
                            }
                        }   
                    }
                }
                break;
            case bishop:
                reVal.addAll(bishopMoves(pos,curCol));
                break;
            case queen:
                reVal.addAll(bishopMoves(pos,curCol));
                reVal.addAll(rookMoves(pos,curCol));
                break;
            case knight:
                int[] offset = new int[] {-2,2,1,-1};
                for(int off1: offset){
                    for(int off2: offset){
                        if(Math.abs(off1) != Math.abs(off2)){
                            if(isOkay(new int[] {pos[0]+off1,pos[1]+off2},curCol)){
                                reVal.add(new move(this,pos,new int[] {pos[0]+off1,pos[1]+off2}));
                            }
                        }
                    }    
                }
                break;
            default:
                break;
        }
        //-pawn, knight, -Läufer, -rook, -König, -Königen
        return reVal;
    }

    private List<move> bishopMoves(int[] pos, color curCol){
        List<move> reVal = new ArrayList<move>();

        //positive x

        for(int i = 0; i <= (7-pos[0]); i++){
            if(i != 0 && pos[1]+i <= 7){
                if(this.boardContent[pos[0]+i][pos[1]+i].TypeOfColor == curCol){
                    break;
                }
                //if(this.boardContent[pos[0]+i][pos[1]+i].TypeOfColor != curCol){
                else{
                    reVal.add(new move(this,pos,new int[] {pos[0]+i,pos[1]+i}/*tempPos2L*/));
                            /*for(int s = 0; s < reVal.size(); s++){
                                System.out.println("for ("+s+") :\t"+reVal.get(s).endPos[0]);
                            }*/
                    if(this.boardContent[pos[0]+i][pos[1]+i].TypeOfColor != color.empty){
                        break;
                    }
                }
            }
        }

        for(int i = 0; i <= (7-pos[0]); i++){
            if(i != 0 && pos[1]-i >= 0){
                if(this.boardContent[pos[0]+i][pos[1]-i].TypeOfColor == curCol){
                    break;
                }
                else{
                    reVal.add(new move(this,pos,new int[] {pos[0]+i,pos[1]-i}));
                    if(this.boardContent[pos[0]+i][pos[1]-i].TypeOfColor != color.empty){
                        break;
                    }
                }
            }
        }

        //negative x

        for(int i = 0; i <= pos[0]; i++){
            if(i != 0 && pos[1]+i <= 7){
                if(this.boardContent[pos[0]-i][pos[1]+i].TypeOfColor == curCol){
                    break;
                }
                else{
                    reVal.add(new move(this,pos,new int[] {pos[0]-i,pos[1]+i}));
                    if(this.boardContent[pos[0]-i][pos[1]+i].TypeOfColor != color.empty){
                        break;
                    }
                }
            }
        }

        for(int i = 0; i <= pos[0]; i++){
            if(i != 0 && pos[1]-i >= 0){
                if(this.boardContent[pos[0]-i][pos[1]-i].TypeOfColor == curCol){
                    break;
                }
                else{
                    reVal.add(new move(this,pos,new int[] {pos[0]-i,pos[1]-i}));
                    if(this.boardContent[pos[0]-i][pos[1]-i].TypeOfColor != color.empty){
                        break;
                    }
                }
            }
        }
        return reVal;
    }

    private List<move> rookMoves(int[] pos, color curCol){
        List<move> reVal = new ArrayList<move>();
        //vert + & -
        //hor  + & -
        for(int x = pos[0]; x < 8; x++){
            if(x != pos[0]){
                if(this.boardContent[x][pos[1]].TypeOfColor == curCol){
                    break;
                }
                if(this.boardContent[x][pos[1]].TypeOfColor != curCol){
                    move temp = new move(this,pos,new int[] {x,pos[1]});
                    reVal.add(temp);
                    if(this.boardContent[x][pos[1]].TypeOfColor != color.empty){
                        break;
                    }
                }
            }
        }

        for(int x = pos[0]; x >= 0; x--){
            if(x != pos[0]){
                if(this.boardContent[x][pos[1]].TypeOfColor == curCol){
                    break;
                }
                if(this.boardContent[x][pos[1]].TypeOfColor != curCol){
                    move temp = new move(this,pos,new int[] {x,pos[1]});
                    reVal.add(temp);
                    if(this.boardContent[x][pos[1]].TypeOfColor != color.empty){
                        break;
                    }
                }
            }
        }

        for(int y = pos[1]; y < 8; y++){
            if(y != pos[1]){
                if(this.boardContent[pos[0]][y].TypeOfColor == curCol){
                    break;
                }
                if(this.boardContent[pos[0]][y].TypeOfColor != curCol){
                    move temp = new move(this,pos,new int[] {pos[0],y});
                    reVal.add(temp);
                    if(this.boardContent[pos[0]][y].TypeOfColor != color.empty){
                        break;
                    }
                }
            }
        }

        for(int y = pos[1]; y >= 0; y--){
            if(y != pos[1]){
                if(this.boardContent[pos[0]][y].TypeOfColor == curCol){
                    break;
                }
                if(this.boardContent[pos[0]][y].TypeOfColor != curCol){
                    move temp = new move(this,pos,new int[] {pos[0],y});
                    reVal.add(temp);
                    if(this.boardContent[pos[0]][y].TypeOfColor != color.empty){
                        break;
                    }
                }
            }
        }
        return reVal;
    }

    private boolean isOkay(int[] pos, color curCol){
        if(pos[0] <= 7 && pos[0] >= 0 && pos[1] <= 7 && pos[1] >= 0){
            if(curCol != this.boardContent[pos[0]][pos[1]].TypeOfColor || this.boardContent[pos[0]][pos[1]].TypeOfColor == color.empty){
                return true;
            }
        }
        return false;
    }

    public void printBoard(){
        for(int y = 7; y >= 0; y--){
            System.out.print("│");
            for(int x = 0; x < 8; x++){
                char charOut = '\u2644';
                if(this.boardContent[x][y].TypeOfColor == color.white){
                    switch(this.boardContent[x][y].TypeOfPiece){
                        case king:
                        charOut = '\u2654';
                        break;
                        case queen:
                            charOut = '\u2655';
                            break;
                        case rook:
                            charOut = '\u2656';
                            break;
                            case bishop:
                            charOut = '\u2657';
                            break;
                        case knight:
                            charOut = '\u2658';
                            break;
                        case pawn:
                            charOut = '\u2659';
                            break;
                        default:
                            charOut = ' ';
                            break;
                    }
                }else if (this.boardContent[x][y].TypeOfColor == color.black){
                    switch(this.boardContent[x][y].TypeOfPiece){
                        case king:
                            charOut = '\u265A';
                            break;
                        case queen:
                            charOut = '\u265B';
                            break;
                        case rook:
                            charOut = '\u265C';
                            break;
                        case bishop:
                            charOut = '\u265D';
                            break;
                        case knight:
                            charOut = '\u265E';
                            break;
                        case pawn:
                            charOut = '\u265F';
                            break;
                        default:
                            charOut = ' ';
                            break;
                    }
                }else{
                    charOut = ' ';
                }
                System.out.print(" "+charOut+" |");
            }
            System.out.print(" "+y+"\n"+"─────────────────────────────────"+"\n");
        }
        for(int i = 0; i < 8;i++){
            System.out.print("  "+i+" ");
        }
        System.out.print("\n");
    }
}