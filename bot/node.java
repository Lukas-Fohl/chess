package bot;
import framework.*;

import java.util.*;

public class node{
    public move data;
    public node parentNode = null;
    public List<node> children;
    public int height;
    public node(int heightIn, move dataIn, node ParentIn, List<node> childrenIn){
        height = heightIn;
        data = dataIn;
        parentNode = ParentIn;
        children = childrenIn;
    }
    public node first(){
        node temp = this;
        while(true){
            if(parentNode != null){
                temp = parentNode;
            }else{
                return temp;
            }
        }
    }
    public List<node> last(){
        List<node> reVal = new ArrayList<node>();
        //find max height --> list all nodes with same height 
        this.lastHeight();
        return reVal;
    }
    private int lastHeight(){
        int reVal;
        node temp = this;
        while(true){
            if(temp.children.size()>0||temp.children.get(0)!=null){
                reVal = temp.height;
                break;
            }
            temp = temp.children.get(0);
        }
        return reVal;
    }
}