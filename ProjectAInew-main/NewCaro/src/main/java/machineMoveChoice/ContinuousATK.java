package machineMoveChoice;

import static calculateDistance.CalculateDistance.cal;
import static calculateDistance.Check.checkWin;
import static moveSet.Advance.checkSurround;


import moveSet.*;


import java.util.ArrayList;
import java.util.List;



public class ContinuousATK {
    public static int close = 0;
    static int[][] E1 = new int[28][28];
    public static List<Point> ContinuousATK = new ArrayList<>();
    public static List<Point> ContinuousATK2 = new ArrayList<>();
    public static void FinalATK(Point tmp, int[][] E, int a) {

        a = 1;
        int x,y;
        boolean end = false;
        int t1;
        Point pO1 = new Point(0, 0);
        Point pX1 = new Point(0, 0);
        Point pO2 = new Point(0, 0);
        Point pX2 = new Point(0, 0);
        int check = 0;

        int t = checkSurround(tmp,E,2);
        if(t == 5){
            pO1 = tmp;
            x = tmp.getX();
            y = tmp.getY();
            E[x][y] = 2;
            for(int i = x-3; i <=x +3;i++){
                for (int j = y-3; j <=y + 3;j++){
                    if(i<=20 && j <= 20 && i >= 0 && j >= 0  && checkWin(i, j, E, 2) && E[i][j] == 0){
                        pX1 = new Point(i,j);
                        //System.out.println("check " + i + " " + j);
                    }
                }
            }
            t1 = checkSurround(pX2,E,1);
            if(t1 != 1 && t1 != 2 && t1 != 3 && t1 != 5){
                check = 1;
                pO1.setRank(a);
                pX1.setRank(a);
                ContinuousATK2.add(pX1);
                ContinuousATK.add(pO1);

            }
            E[x][y] = 0;
        }
        int l = ContinuousATK.size();
        if(l == 0){
            return;
        }
        if(addPoint(E, 0) == 1){
            return;
        }
        if(l == ContinuousATK.size()){
            ContinuousATK.clear();
            ContinuousATK2.clear();
            return;
        }
        l = ContinuousATK.size();
        if(l == 0){
            return;
        }
//        for(Point p : ContinuousATK){
//            if(p!=null) System.out.print("("+p.getX() + " " + p.getY()+" " +p.getRank()+") ");
//        }
//        System.out.println();
//        for(Point p : ContinuousATK2){
//            if(p!=null) System.out.print("("+p.getX() + " " + p.getY()+" "+p.getRank() +") ");
//        }
//        System.out.println("a");
        if(addPoint(E,1) == 1){
            return;
        }
        if(l == ContinuousATK.size()){
            ContinuousATK.clear();
            ContinuousATK2.clear();
            return;
        }

//        for(Point p : ContinuousATK){
//            if(p!=null) System.out.print("("+p.getX() + " " + p.getY()+" " +p.getRank()+") ");
//        }
//        System.out.println();
//        for(Point p : ContinuousATK2){
//            if(p!=null) System.out.print("("+p.getX() + " " + p.getY()+" "+p.getRank() +") ");
//        }
//        System.out.println("a");
        l = ContinuousATK.size();
        if(addPoint(E,2) == 1) {
            return;
        }
        if(l == ContinuousATK.size()){
            ContinuousATK.clear();
            ContinuousATK2.clear();
            return;
        }
        l = ContinuousATK.size();
        if(l == 0){
            return;
        }
        if(addPoint(E,3) == 1){
            return;
        }
        if(l == ContinuousATK.size()){
            ContinuousATK.clear();
            ContinuousATK2.clear();
            return;
        }
        ContinuousATK.clear();
        ContinuousATK2.clear();
        return;
    }
    public static int addPoint(int[][] E,double layer1){
        int layer = (int)Math.pow(10,layer1);
        int a = 1;
        //System.out.println(layer);
        Point pO2 = new Point(0,0);
        Point pX2 = new Point(0,0);
        boolean end = false;
        int x2 = 0,y2 = 0;
        for(int k = 0; k< ContinuousATK.size();k++){
            Point p = ContinuousATK.get(k);
            if(p.getRank() / layer == 1 || p.getRank() / layer == 2){
                Mark(E,1,p.getRank());
                end:
                for(int i = p.getX()-4; i <=p.getX() + 4;i++){
                    for (int j = p.getY()-4; j <=p.getY() + 4;j++){
                        Point tmp1 = new Point(i,j);
                        if(i<=20 && j <= 20 && i >= 0 && j >= 0  && checkSurround(tmp1,E,2) == 5 && E[i][j] == 0){
                            //System.out.println("check " + i + " " + j);
                            pO2 = tmp1;
                            pO2.setRank(p.getRank()*10 + a);
                            a += 1;
                            if(a == 4){
                                break end;
                            }
                            ContinuousATK.add(pO2);
                        }
                        if(i >= 0 && j >= 0  && cal(tmp1,E,2,0) >= 30000 && cal(tmp1,E,2,0) < 50000 && E[i][j] == 0){
//                            for (int ix = 0; ix < 20; ix++) {
//                                for (int jx = 0; jx < 20; jx++) {
//                                    System.out.print(E[ix][jx] + " ");
//                                }
//                                System.out.println();
//                            }
                            //System.out.println(i + " " + j + " " + cal(tmp1,E,2,0));
                            end = true;
                            pO2 = tmp1;
                            pO2.setRank(p.getRank()*10 + a);
                            ContinuousATK.add(pO2);
                            break end;
                        }
                    }
                }

                if(end){
//                    for(Point px : ContinuousATK){
//                        if(px!=null) System.out.print("("+px.getX() + " " + px.getY() +") ");
//                    }
                    //System.out.println();
                    //System.out.println(a);
                    //System.out.println();
                    //System.out.println("check1");
                    Mark(E,0,p.getRank());
                    for(int i = 2; i<=a;i++){
                        ContinuousATK.remove(ContinuousATK.get(ContinuousATK.size()-2));
                    }
                    Trim();
                    close = 1;
                    return close;
                }
                if(pO2.getX() == 0 && pO2.getY() == 0){
                    Mark(E,0,p.getRank());
                    ContinuousATK.clear();
                    ContinuousATK2.clear();
                    return 0;
                }

//                    for (Point px : ContinuousATK) {
//                        if (px != null) System.out.print("(" + px.getX() + " " + px.getY()+" "+px.getRank() + ") ");
//                    }
                    //System.out.println();
                for(int k1 = 0;k1 < ContinuousATK.size();k1++){
                    Point p2 = ContinuousATK.get(k1);

//                    System.out.println(p2.getX() + " " + p2.getY() + " " + p2.getRank());
//                    for (Point px : ContinuousATK) {
//                        if (px != null) System.out.print("(" + px.getX() + " " + px.getY()+" "+px.getRank() + ") ");
//                    }
//                    System.out.println();
                    if (p2.getRank() / (layer*10) >= 1) {
                        //System.out.println(p2.getX() + " " + p2.getY());
                        x2 = p2.getX();
                        y2 = p2.getY();
                        E[x2][y2] = 2;
                        //System.out.println(x2 + " " + y2 +"**");
                        for (int i = x2 - 3; i <= x2 + 3; i++) {
                            for (int j = y2 - 3; j <= y2 + 3; j++) {
                                if (i >= 0 && j >= 0 && checkWin(i, j, E, 2) && E[i][j] == 0) {
                                    pX2 = new Point(i, j);
                                }
                            }
                        }
                        if (pX2.getX() != 0 && pX2.getY() != 0) {
                            pX2.setRank(p2.getRank());
                            if(!ContinuousATK2.contains(pX2)){
                                ContinuousATK2.add(pX2);
                            }
                            int t1 = checkSurround(pX2, E, 1);
                            //System.out.println(pX2.getX() + " " + pX2.getY() + " " + t1);
                            if (!(t1 != 1 && t1 != 2 && t1 != 3 && t1 != 5)) {
                                ContinuousATK2.remove(pX2);
                                ContinuousATK.remove(p2);
                                k1-=1;
                            }
                        }
                        E[x2][y2] = 0;
                    }
                }

                Mark(E,0,p.getRank());
            }
        }
        return 0;
    }
    public static void Mark(int[][] E,int a,int rank){
        for(int i = 0;i <= 20-1;i++){
            for(int j = 0;j <= 20-1;j++){
                E1[i][j] = E[i][j];
            }
        }
        if(a == 1){
            for(int i = 0;i <= ContinuousATK.size()-1;i++){
                if(ContinuousATK.get(i).getRank() == rank){
                    E[ContinuousATK.get(i).getX()][ContinuousATK.get(i).getY()] = 2;
                }
                else if(ContinuousATK.get(i).getRank() == rank / 10){
                    E[ContinuousATK.get(i).getX()][ContinuousATK.get(i).getY()] = 2;
                    }
                else if(ContinuousATK.get(i).getRank() == rank / 100){
                    E[ContinuousATK.get(i).getX()][ContinuousATK.get(i).getY()] = 2;
                }
                else if(ContinuousATK.get(i).getRank() == rank / 1000){
                    E[ContinuousATK.get(i).getX()][ContinuousATK.get(i).getY()] = 2;
                }
                else if(ContinuousATK.get(i).getRank() == rank / 10000){
                    E[ContinuousATK.get(i).getX()][ContinuousATK.get(i).getY()] = 2;
                }
                else if(ContinuousATK.get(i).getRank() == rank / 100000){
                    E[ContinuousATK.get(i).getX()][ContinuousATK.get(i).getY()] = 2;
                }
            }
            for(int i = 0;i <= ContinuousATK2.size()-1;i++){
                if(ContinuousATK2.get(i).getRank() == rank){
                    E[ContinuousATK2.get(i).getX()][ContinuousATK2.get(i).getY()] = 1;
                }
                else if(ContinuousATK2.get(i).getRank() == rank / 10){
                    E[ContinuousATK2.get(i).getX()][ContinuousATK2.get(i).getY()] = 1;
                }
                else if(ContinuousATK2.get(i).getRank() == rank / 100){
                    E[ContinuousATK2.get(i).getX()][ContinuousATK2.get(i).getY()] = 1;
                }
                else if(ContinuousATK2.get(i).getRank() == rank / 1000){
                    E[ContinuousATK2.get(i).getX()][ContinuousATK2.get(i).getY()] = 1;
                }
                else if(ContinuousATK2.get(i).getRank() == rank / 10000){
                    E[ContinuousATK2.get(i).getX()][ContinuousATK2.get(i).getY()] = 1;
                }
                else if(ContinuousATK2.get(i).getRank() == rank / 100000){
                    E[ContinuousATK2.get(i).getX()][ContinuousATK2.get(i).getY()] = 1;
                }
            }
        }
        else{
            for(int i = 0;i <= ContinuousATK.size()-1;i++){
                if(ContinuousATK.get(i).getRank() == rank){
                    E[ContinuousATK.get(i).getX()][ContinuousATK.get(i).getY()] = 0;
                }
                else if(ContinuousATK.get(i).getRank() == rank / 10){
                    E[ContinuousATK.get(i).getX()][ContinuousATK.get(i).getY()] = 0;
                }
                else if(ContinuousATK.get(i).getRank() == rank / 100){
                    E[ContinuousATK.get(i).getX()][ContinuousATK.get(i).getY()] = 0;
                }
                else if(ContinuousATK.get(i).getRank() == rank / 1000){
                    E[ContinuousATK.get(i).getX()][ContinuousATK.get(i).getY()] = 0;
                }
                else if(ContinuousATK.get(i).getRank() == rank / 10000){
                    E[ContinuousATK.get(i).getX()][ContinuousATK.get(i).getY()] = 0;
                }
                else if(ContinuousATK.get(i).getRank() == rank / 100000){
                    E[ContinuousATK.get(i).getX()][ContinuousATK.get(i).getY()] = 0;
                }
            }
            for(int i = 0;i <= ContinuousATK2.size()-1;i++){
                if(ContinuousATK2.get(i).getRank() == rank){
                    E[ContinuousATK2.get(i).getX()][ContinuousATK2.get(i).getY()] = 0;
                }
                else if(ContinuousATK2.get(i).getRank() == rank / 10){
                    E[ContinuousATK2.get(i).getX()][ContinuousATK2.get(i).getY()] = 0;
                }
                else if(ContinuousATK2.get(i).getRank() == rank / 100){
                    E[ContinuousATK2.get(i).getX()][ContinuousATK2.get(i).getY()] = 0;
                }
                else if(ContinuousATK2.get(i).getRank() == rank / 1000){
                    E[ContinuousATK2.get(i).getX()][ContinuousATK2.get(i).getY()] = 0;
                }
                else if(ContinuousATK2.get(i).getRank() == rank / 10000){
                    E[ContinuousATK2.get(i).getX()][ContinuousATK2.get(i).getY()] = 0;
                }
                else if(ContinuousATK2.get(i).getRank() == rank / 100000){
                    E[ContinuousATK2.get(i).getX()][ContinuousATK2.get(i).getY()] = 0;
                }
            }
        }
    }
    public static void Trim(){
//        for (Point p : ContinuousATK) {
//            if (p != null) System.out.print("(" + p.getX() + " " + p.getY() + " " + p.getRank() + ") ");
//        }
//        System.out.println();
//        for (Point p : ContinuousATK2) {
//            if (p != null) System.out.print("(" + p.getX() + " " + p.getY() + " " + p.getRank() + ") ");
//        }
//        System.out.println();
        int rank = ContinuousATK.get(ContinuousATK.size()-1).getRank();
        ArrayList<Point> tmp = new ArrayList<>();
        for(int i = 0;i <= ContinuousATK.size()-1;i++){
            if(ContinuousATK.get(i).getRank() == rank){
                tmp.add(ContinuousATK.get(i));
            }
            else if(ContinuousATK.get(i).getRank() == rank / 10){
                if(ContinuousATK.get(ContinuousATK.size()-1).getRank() == ContinuousATK.get(i).getRank()){
                    continue;
                }
                tmp.add(ContinuousATK.get(i));
            }
            else if(ContinuousATK.get(i).getRank() == rank / 100){
                if(ContinuousATK.get(ContinuousATK.size()-1).getRank() == ContinuousATK.get(i).getRank()){
                    continue;
                }
                tmp.add(ContinuousATK.get(i));
            }
            else if(ContinuousATK.get(i).getRank() == rank / 1000){
                if(ContinuousATK.get(ContinuousATK.size()-1).getRank() == ContinuousATK.get(i).getRank()){
                    continue;
                }
                tmp.add(ContinuousATK.get(i));
            }
            else if(ContinuousATK.get(i).getRank() == rank / 10000){
                if(ContinuousATK.get(ContinuousATK.size()-1).getRank() == ContinuousATK.get(i).getRank()){
                    continue;
                }
                tmp.add(ContinuousATK.get(i));
            }
            else if(ContinuousATK.get(i).getRank() == rank / 100000){
                if(ContinuousATK.get(ContinuousATK.size()-1).getRank() == ContinuousATK.get(i).getRank()){
                    continue;
                }
                tmp.add(ContinuousATK.get(i));
            }
        }
//        for(Point p:tmp){
//            System.out.print("(" + p.getX() + " " + p.getY() + " " + p.getRank() + ") ");
//        }
//        System.out.println();
//        for (Point p : ContinuousATK2) {
//            if (p != null) System.out.print("(" + p.getX() + " " + p.getY() + " " + p.getRank() + ") ");
//        }
        //System.out.println();
        ContinuousATK.clear();
        for(Point i : tmp){
            ContinuousATK.add(i);
//            for(Point j : ContinuousATK2){
//                if(j.getRank() == i.getRank()){
//                    if(!ContinuousATK.contains(j)){
//                        ContinuousATK.add(j);
//                    }
//                }
//            }
        }
        //ContinuousATK.removeFirst();
        //ContinuousATK = tmp;
    }
}

