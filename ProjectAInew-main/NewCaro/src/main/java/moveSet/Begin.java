package moveSet;

import machineMoveChoice.SelectMove;

public class Begin {
    //duonglt
    public static int firstLoc_x = 0;
    public static int firstLoc_y = 0;
    public static double branch = 0.0;

    public static moveSet.Point BeginMove() {
        Point tmp = new Point(0, 0);
        if (SelectMove.startMove == 1 && SelectMove.E[7][7] == 2) {
            firstLoc_x = 7;
            firstLoc_y = 7;
            if (SelectMove.E[6][6] == 1 || SelectMove.E[8][6] == 1) {
                branch = 1.11;
                return new Point(7,5);
            }if(SelectMove.E[6][8] == 1 || SelectMove.E[8][8] == 1){
                branch = 1.12;
                return new Point(7,9);
            }
          if(SelectMove.E[7][6] == 1){
                branch = 1.51;
                Point a = new Point(9,9);
                return a;
            }
            if(SelectMove.E[7][8] == 1){
                branch = 1.52;
                Point a = new Point(4,6);
                return a;
            }
            if(SelectMove.E[6][7] == 1){
                branch = 1.53;
                Point a = new Point(9,5);
                return a;
            }
            if(SelectMove.E[8][7] == 1){
                branch = 1.54;
                Point a = new Point(5,9);
                return a;
            }
        }
        if(SelectMove.startMove == 2 && branch == 1.11){
            if(SelectMove.E[7][6] == 1){
                branch = 2.11;
                return new Point(8,6);
            }else{
                branch = 2.12;
                return new Point(7,6);
            }
        }
        if(SelectMove.startMove == 2 && branch == 1.12){
            if(SelectMove.E[7][8] == 1){
                branch = 2.21;
                return new Point(6,8);
            }else{
                branch = 2.22;
                return new Point(7,8);
            }
        }
        if(SelectMove.startMove == 2 && branch == 1.51){
            if(SelectMove.E[9][7] == 1){
                branch = 2.51;
                return new Point(5,9);
            }
            else{
                return new Point(9,7);
            }
        }
        if(SelectMove.startMove == 2 && branch == 1.52){
            if(SelectMove.E[6][6] == 1){
                branch = 2.52;
                return new Point(5,7);
            }
            System.out.println(SelectMove.E[5][5]);
            if(SelectMove.E[5][5] == 0){
                branch = 2.53;
                return new Point(5,5);
            }
        }


        if(SelectMove.startMove == 3 && branch == 2.11){
            if(SelectMove.E[9][5] == 1){
                branch = 3.11;
                return new Point(9,7);
            }else{
                branch = 3.12;
                return new Point(9,5);
            }
        }
        if(SelectMove.startMove == 3 && branch == 2.21){
            if(SelectMove.E[9][7] == 1){
                branch = 3.21;
                return new Point(9,9);
            }else{
                branch = 3.22;
                return new Point(9,7);
            }
        }

        return tmp;
    }
}
