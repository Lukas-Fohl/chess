package bot;

import framework.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class moves{
    //get all possible moves
    //--> check for criteria
    //--> sort list and applay [0] || [length-1]
    private board boardTemp;
    private color colorTemp;
    private List<movePref> movePrefList = new ArrayList<movePref>();
    private color colorTempInv;
    public List<movePref> getMoves(board boardIn,color colIn){
        boardTemp = boardIn;
        colorTemp = colIn;
        colorTempInv = (colorTemp==color.white)?color.black:color.white;
        for(int x = 0; x <= 7; x++){
            for(int y = 0; y <= 7; y++){
                if(boardTemp.boardContent[x][y].TypeOfColor == colorTemp){
                    if(boardTemp.boardContent[x][y].TypeOfPiece == piecesType.rook){
                    }
                    for(move m:boardTemp.possibleMoves(new int[] {x,y})){
                        movePrefList.add(new movePref(m,0));
                    }
                }
            }    
        }
        for(int i = 0; i < movePrefList.size(); i++){
            movePrefList.set(i,new movePref(movePrefList.get(i).move_, getValue(movePrefList.get(i).move_)));
        }
        sortMovePref();
        return movePrefList;
    }

    private void sortMovePref(){
        //bubble sort or smth
        for(int i = 0; i < movePrefList.size(); i++){
            for(int j = 0; j < movePrefList.size()-1; j++){
                if(movePrefList.get(j).rating > movePrefList.get(j+1).rating){
                    Collections.swap(movePrefList, j, (j+1));
                }
            }   
        }
    }
    
    private int getValue(move moveIn){
        int reVal = 0;
        //board tempBoardPref = (board)((board)boardTemp);//.clone()
        board tempBoardPref = new board();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                tempBoardPref.boardContent[i][j] = boardTemp.boardContent[i][j];
            }    
        }
        tempBoardPref.applyMove(moveIn);

        reVal += valueOfOwnnedPieces(tempBoardPref,colorTemp);

        reVal -= valueOfOwnnedPieces(tempBoardPref,colorTempInv)*1.2;

        reVal -= possibleDamageEnemy(tempBoardPref);

        reVal += possibleDamage(tempBoardPref)/2;

        reVal -= spreadOfPieces(tempBoardPref);

        reVal += openMoves(tempBoardPref)/5;

        reVal += MidControl(tempBoardPref);
        
        //last
        reVal += kingInDanger(tempBoardPref);

        return reVal;
    }

    private int kingInDanger(board bIn){
        int reVal = 0;
        int[] kingPos = new int[]{20,20};
        for(int x = 0; x <= 7; x++){
            for(int y = 0; y <= 7; y++){
                if(bIn.boardContent[x][y].TypeOfPiece == piecesType.king && bIn.boardContent[x][y].TypeOfColor == colorTemp){
                    kingPos = new int[]{x,y};
                }
            }
        }
        if(kingPos[0] == 20){
            return -10000;
        }else{
            reVal = (bIn.isInDanger(kingPos))?-10000:reVal;
        }
        return reVal;
    }

    private int valueOfOwnnedPieces(board bIn, color cIn){
        int value = 0;
        for(int x = 0; x <= 7; x++){
            for(int y = 0; y <= 7; y++){
                if(bIn.boardContent[x][y].TypeOfPiece != piecesType.empty && cIn == bIn.boardContent[x][y].TypeOfColor){
                    value += valueOfPiece(bIn.boardContent[x][y].TypeOfPiece);
                }
            }
        }
        return value;
    }

    private int valueOfPiece(piecesType pieceIn){
        int value = 0;
        switch(pieceIn){
            case pawn:
                value += 1;
                break; 
            case king:
                value += 10;
                break; 
            case knight:
                value += 3;
                break; 
            case bishop:
                value += 3;
                break; 
            case rook:
                value += 5;
                break; 
            case queen:
                value += 9;
                break; 
            default:
                value += 0;
                break;
        }
        return value;
    }

    private int possibleDamage(board bIn){
        int value = 0;
        List<move> ownMoves = new ArrayList<move>();
        for(int x = 0; x <= 7; x++){
            for(int y = 0; y <= 7; y++){
                if(bIn.boardContent[x][y].TypeOfPiece != piecesType.empty && colorTemp == bIn.boardContent[x][y].TypeOfColor){
                    ownMoves.addAll(bIn.possibleMoves(new int[]{x,y}));
                }
            }
        }
        for(move m: ownMoves){
            if(bIn.boardContent[m.endPos[0]][m.endPos[1]].TypeOfColor == colorTempInv){
                value += valueOfPiece(bIn.boardContent[m.endPos[0]][m.endPos[1]].TypeOfPiece);
            }
        }
        return value;
    }

    private int possibleDamageEnemy(board bIn){
        int value = 0;
        List<move> enemyMoves = new ArrayList<move>();
        for(int x = 0; x <= 7; x++){
            for(int y = 0; y <= 7; y++){
                if(bIn.boardContent[x][y].TypeOfPiece != piecesType.empty && colorTempInv == bIn.boardContent[x][y].TypeOfColor){
                    enemyMoves.addAll(bIn.possibleMoves(new int[]{x,y}));
                }
            }
        }
        for(move m: enemyMoves){
            if(bIn.boardContent[m.endPos[0]][m.endPos[1]].TypeOfColor == colorTemp){
                value += valueOfPiece(bIn.boardContent[m.endPos[0]][m.endPos[1]].TypeOfPiece);
            }
        }
        return value;
    }

    private int spreadOfPieces(board bIn){
        int reVal = 0;
        List<Integer> poses = new ArrayList<>();

        for(int x = 0; x <= 7; x++){
            for(int y = 0; y <= 7; y++){
                if(bIn.boardContent[x][y].TypeOfPiece != piecesType.empty && colorTemp == bIn.boardContent[x][y].TypeOfColor){
                    poses.add(y);
                }
            }
        }   
        int count = 0;
        for(int i: poses){
            reVal += i;
            count++;
        }
        reVal = (reVal/count);
        for(int i = 0; i < poses.size(); i++){
            for(int j = 0; j < poses.size()-1; j++){
                if(poses.get(j)>poses.get(j+1)){
                    int temp = poses.get(j);
                    poses.set(j,poses.get(j+1));
                    poses.set(j+1,temp);
                }
            }   
        }
        int max = poses.get(poses.size()-1);
        int min = poses.get(0);
        reVal = ((max-reVal)+(reVal-min)/2);
        return reVal;
    }
    private int openMoves(board bIn){
        List<move> tempMoves = new ArrayList<move>();
        for(int x = 0; x <= 7; x++){
            for(int y = 0; y <= 7; y++){
                if(boardTemp.boardContent[x][y].TypeOfPiece != piecesType.empty && colorTemp == boardTemp.boardContent[x][y].TypeOfColor){
                    tempMoves.addAll(boardTemp.possibleMoves(new int[] {x,y}));
                }
            }
        }         
        List<move> prefMoves = new ArrayList<move>();
        for(int x = 0; x <= 7; x++){
            for(int y = 0; y <= 7; y++){
                if(bIn.boardContent[x][y].TypeOfPiece != piecesType.empty && colorTemp == bIn.boardContent[x][y].TypeOfColor){
                    prefMoves.addAll(bIn.possibleMoves(new int[] {x,y}));
                }
            }
        }         
        return prefMoves.size()-tempMoves.size();
    }

    private int MidControl(board bIn){
        int reVal = 0;
        for(int x = 0; x < 2; x++){
            for(int y = 0; y < 2; y++){
                reVal += (bIn.boardContent[3+x][3+y].TypeOfColor == colorTemp)? 1 : -1;
            }   
        }
        return reVal;
    }
}