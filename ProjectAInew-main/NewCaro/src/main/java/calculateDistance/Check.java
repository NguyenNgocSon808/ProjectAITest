package calculateDistance;

public class Check {
    //duonglt
    public static boolean checkWin(int x,int y,int[][] E,int a){

        if(x >= 4 && E[x-4][y] == a && E[x-3][y] == a && E[x-2][y] == a && E[x-1][y] == a){
            return true;
        }
        if(x >= 3 && E[x-3][y] == a && E[x-2][y] == a && E[x-1][y] == a && E[x+1][y] == a){
            return true;
        }
        if(x >= 2 && E[x-2][y] == a && E[x-1][y] == a && E[x+1][y] == a && E[x+2][y] == a){
            return true;
        }
        if(x >= 1 && E[x-1][y] == a && E[x+1][y] == a && E[x+2][y] == a && E[x+3][y] == a){
            return true;
        }
        if(E[x+1][y] == a && E[x+2][y] == a && E[x+3][y] == a && E[x+4][y] == a){
            return true;
        }

        if(y >= 4 && E[x][y-4] == a && E[x][y-3] == a && E[x][y-2] == a && E[x][y-1] == a){
            return true;
        }
        if(y >= 3 && E[x][y-3] == a && E[x][y-2] == a && E[x][y-1] == a && E[x][y+1] == a){
            return true;
        }
        if(y >= 2 && E[x][y-2] == a && E[x][y-1] == a && E[x][y+1] == a && E[x][y+2] == a){
            return true;
        }
        if(y >= 1 && E[x][y-1] == a && E[x][y+1] == a && E[x][y+2] == a && E[x][y+3] == a){
            return true;
        }
        if(E[x][y+1] == a && E[x][y+2] == a && E[x][y+3] == a && E[x][y+4] == a){
            return true;
        }

        if(y >= 4 && x >= 4 && E[x-4][y-4] == a && E[x-3][y-3] == a && E[x-2][y-2] == a && E[x-1][y-1] == a){
            return true;
        }
        if(y >= 3 && x >= 3 && E[x-3][y-3] == a && E[x-2][y-2] == a && E[x-1][y-1] == a && E[x+1][y+1] == a){
            return true;
        }
        if(y >= 2 && x >= 2 && E[x-2][y-2] == a && E[x-1][y-1] == a && E[x+1][y+1] == a && E[x+2][y+2] == a){
            return true;
        }
        if(y >= 1 && x >= 1 && E[x-1][y-1] == a && E[x+1][y+1] == a && E[x+2][y+2] == a && E[x+3][y+3] == a){
            return true;
        }
        if(E[x+1][y+1] == a && E[x+2][y+2] == a && E[x+3][y+3] == a && E[x+4][y+4] == a){
            return true;
        }


        if(y >= 4 && E[x+4][y-4] == a && E[x+3][y-3] == a && E[x+2][y-2] == a && E[x+1][y-1] == a){
            return true;
        }
        if(y >= 3 && x >= 1 && E[x+3][y-3] == a && E[x+2][y-2] == a && E[x+1][y-1] == a && E[x-1][y+1] == a){
            return true;
        }
        if(y >= 2 && x >= 2 && E[x+2][y-2] == a && E[x+1][y-1] == a && E[x-1][y+1] == a && E[x-2][y+2] == a){
            return true;
        }
        if(y >= 1 && x >= 3 && E[x+1][y-1] == a && E[x-1][y+1] == a && E[x-2][y+2] == a && E[x-3][y+3] == a){
            return true;
        }
        if(x >=4 && E[x-1][y+1] == a && E[x-2][y+2] == a && E[x-3][y+3] == a && E[x-4][y+4] == a){
            return true;
        }

        return false;
    }
}
