package framework;

import java.util.ArrayList;
import java.util.List;

public class pieces{
    public piecesType TypeOfPiece;
    public color TypeOfColor;
    public pieces(color col, piecesType type){
        TypeOfPiece = type;
        TypeOfColor = col;
    }
}