package moveSet;

import machineMoveChoice.SelectMove;

public class Advance {
    public static int checkSurround(Point tmp, int[][] E, int a){
        int b = 0;
        int c = 0;
        if(a == 1){
            b = 2;
        }
        else if(a == 2){
            b = 1;
        }
        else if(a == 3){ //a == 3 là xét cho X trong lượt của O
            a = 1;
            b = 2;
            c = 3;
        }
        // a == 1 là X <=> b là O
        // a == 2 là O <=> b là X

        int x = tmp.x;
        int y = tmp.y;
        int rowleft = 0; //đếm các giống với a ở bên trái
        int rowright = 0; //đếm các giống với a ở bên phải
        int row; // bằng rowleft + rowright trong trường hợp trái và phải của tmp không là ô trống
        int blockleft = 0; // đếm ô bị chặn ngay cạnh bên trái
        int blockright = 0; // đếm ô bị chặn ngay cạnh bên phải
        int spaceleft = 0;  // đếm ô trống bên trái thỏa mãn ngay sau đó là a
        int spaceright = 0; // đếm ô trống bên phải thỏa mãn ngay sau đó là a
        int blleft = 0; // đếm ô bị chặn bên trái nhưng có ô trống bên cạnh
        int blright = 0; // đếm ô bị chặn bên phải nhưng có ô trống bên cạnh
        int spacecount = 0; // đếm ô trống thỏa mãn sau đó là ô trống
                            // nếu spacecount + 1 thì spaceleft hoặc spaceright cũng sẽ + 1

        int absoluteWin = 0; // đếm trường hợp sau khi đánh tại tmp sẽ thắng luôn
        int nearWin = 0; // đếm trường hợp sau khi đánh tại tmp sẽ có nước 4 chưa bị chặn
        int pushATK_3 = 0; // đếm trường hợp sau khi đánh tại tmp sẽ có nước 3 chưa bị chặn
        int pushATK_4 = 0; // đếm trường hợp sau khi đánh tại tmp sẽ có nước 4 bị chặn 1 đầu
        int close = 0;
        int notValid = 0; // đếm trường hợp không nên đánh tại tmp
        //hang ngang

        for (int i = x - 1; i > x - 6; i--) {
            if (i >= 0) {
                if (E[i][y] == a) {
                    rowleft += 1;
                }
                if (E[i][y] == b) {
                    blockleft += 1;
                    break;
                }
                if (i-1 >=0 && E[i][y] == 0 && E[i -1][y] == b) {
                    blleft += 1;
                    break;
                }
                if (E[i][y] == 0) {
                    spaceleft += 1;
                }
                if (i-1 >=0 && E[i][y] == 0 && E[i - 1][y] == 0) {
                    spacecount +=1;
                    break;
                }
            }
        }
        for (int i = x + 1; i < x + 6; i++) {
            if (i <= 20) {
                if (E[i][y] == a) {
                    rowright += 1;
                }
                if (E[i][y] == b) {
                    blockright += 1;
                    break;
                }
                if (E[i][y] == 0 && E[i + 1][y] == b) {
                    blright += 1;
                    break;
                }
                if (E[i][y] == 0) {
                    spaceright += 1;
                }
                if (E[i][y] == 0 && E[i + 1][y] == 0) {
                    spacecount +=1;
                    break;
                }
            }
        }
        //E là vị trí xét đánh (tmp)
        //? là vị trí bât kỳ ( _ ; O ; X )
        if(x-1>=0 && E[x-1][y] != 0 || E[x+1][y] != 0) { // xét TH cả 1 trong 2 bên của tmp khác ô trống
            row = rowleft + rowright;
            if(row == 4){
                // Dạng ?EOOOO?
                absoluteWin++;
                //9999
            }
            if(row == 3 && blockleft == 0 && blockright == 0 && spaceleft + spaceright == 3){
                close++;
                // Dạng _EO_OOE_
            }
            if(row == 3 && blockleft == 0 && blockright == 0 && spaceleft + spaceright <= 1){
                nearWin++;
                // Dạng _EOOO_X
                //      X_EOOO_X
                //9999*
            }
            if(row == 3 && blockleft == 0 && blockright == 0 && spacecount == 2 && spaceleft + spaceright == 2){
                nearWin++;
                // Dạng _EOOO_
                //9999*
            }
            if(row == 3 && blockleft == 1 && blockright == 1 && spaceleft + spaceright == 0){
                notValid ++;
            }
            if(row <= 2 && blockleft == 1 && blockright == 1 && spaceleft + spaceright <= 1){
                notValid ++;
            }
            if(row <=2 && (blockleft + blockright == 1 || blleft + blright == 1) && spaceleft+ spaceright ==0){
                notValid ++;
            }
            if(row == 3 && blockleft == 1 && blockright == 0 && spaceleft <= 1){
                //Dạng XOOOE_? hoặc XOOOE__
                //Dạng XOOO_E_
                pushATK_4++;
                //Prep (nuoc 4 chan 1 dau)
            }
            if(row == 3 && blockleft == 0 && blockright == 1 && spaceright <= 1){
                //Dạng __EOOOX  hoặc ?_EOOOX
                //Dạng ?E_OOOX
                pushATK_4++;
                //Prep (nuoc 4 chan 1 dau)
            }
//            if(row == 3 && blockleft + blockright == 1 && spaceright + spaceleft == 2){
//                //System.out.println(tmp.getX() + "," + tmp.getY());
//                pushATK_4++;
//                //Prep (nuoc 4 chan 1 dau)
//            }
            if(row == 3 && blleft + blright == 2 && spaceleft + spaceright == 1){
                // Dạng X_OOO_E_X
                pushATK_4++;
                //Prep (nuoc 4 chan 1 dau)
            }
            if(row == 2 && spaceleft + spaceright <= 3 && (blockleft + blockright == 0 || blleft + blright == 1)){
                // Dang _OO_E_
                // Dang _OOE__
                // Dang _OO_E_X ; X_OO_E_
                pushATK_3++;
                //(nuoc 3 lien tiep)
            }
        }
        else if(x-1>=0 && E[x-1][y] == 0 && E[x+1][y] == 0){
            if(rowright >= 2 || rowleft >= 2){
                if(rowright == 2){
                    if((blockright == 0 || (blright == 1 && spaceright == 1)) && spaceright <= 2){
                        pushATK_3++;
                    }
                }
                if(rowleft == 2){
                    if((blockleft == 0 || (blleft == 1 && spaceleft == 1)) && spaceleft <= 2){
                        pushATK_3++;
                    }
                }
                if(rowleft == 3 && blockleft == 1 && spaceleft == 1){
                    pushATK_4++;

                }
                if(rowright == 3 && blockright == 1 && spaceright == 1){
                    pushATK_4++;
                    //if(a==2) System.out.println(tmp.getX() + "," + tmp.getY());
                }
            }
        }
        rowright = 0;
        rowleft = 0;
        blockleft = 0;
        blockright = 0;
        spaceleft = 0;
        spaceright = 0;
        blleft = 0;
        blright = 0;
        spacecount = 0;
        //hang doc
        for (int i = y - 1; i > y - 6; i--) {
            if (i >= 0) {
                if (E[x][i] == a) {
                    rowleft += 1;
                }
                if (E[x][i] == b) {
                    blockleft += 1;
                    break;
                }
                if (i-1 >=0 && E[x][i] == 0 && E[x][i - 1] == b) {
                    blleft += 1;
                    break;
                }
                if (E[x][i] == 0) {
                    spaceleft += 1;
                }
                if (i-1 >=0 && E[x][i] == 0 && E[x][i - 1] == 0) {
                    spacecount+=1;
                    break;
                }
            }
        }
        for (int i = y + 1; i < y + 6; i++) {
            if (i <= 20) {
                if (E[x][i] == a) {
                    rowright += 1;
                }
                if (E[x][i] == b) {
                    blockright += 1;
                    break;
                }
                if (E[x][i] == 0 && E[x][i + 1] == b) {
                    blright += 1;
                    break;
                }
                if (E[x][i] == 0) {
                    spaceright += 1;
                }
                if (E[x][i] == 0 && E[x][i + 1] == 0) {
                    spacecount += 1;
                    break;
                }
            }
        }
        if (y-1>=0 &&E[x][y-1] != 0 || E[x][y+1] != 0) {
            row = rowleft + rowright;
            if(row == 4){
                absoluteWin++;
                //9999
            }
            if(row == 3 && blockleft == 0 && blockright == 0 && spaceleft + spaceright <= 1){
                //System.out.println(tmp.getX()+" " + tmp.getY());
                nearWin++;
                //9999*
            }
            if(row == 3 && blockleft == 0 && blockright == 0 && spacecount == 2 && spaceleft + spaceright == 2){
                //System.out.println(tmp.getX()+" " + tmp.getY());
                nearWin++;
                //9999*
            }
            if(row == 3 && blockleft == 0 && blockright == 0 && spaceleft + spaceright == 3){
                close++;
                //9999*
            }
            if(row == 3 && blockleft == 1 && blockright == 1 && spaceleft + spaceright == 0){
                notValid ++;
            }
            if(row <= 2 && blockleft == 1 && blockright == 1 && spaceleft + spaceright <= 1){
                notValid ++;
            }
            if(row <=2 && (blockleft + blockright == 1 || blleft + blright == 1) && spaceleft+ spaceright ==0){
                notValid ++;
            }
            if(row == 3 && blockleft == 1 && blockright == 0 && spaceleft <= 1){
                pushATK_4++;
                //Prep (nuoc 4 chan 1 dau)
            }
            if(row == 3 && blockleft == 0 && blockright == 1 && spaceright <= 1){
                pushATK_4++;
                //Prep (nuoc 4 chan 1 dau)
            }
//            if(row == 3 && blockleft + blockright == 1 && spaceright + spaceleft == 2){
//                pushATK_4++;
//                //Prep (nuoc 4 chan 1 dau)
//            }
            if(row == 3 && blleft + blright == 2 && spaceleft + spaceright == 1){
                pushATK_4++;
                //Prep (nuoc 4 chan 1 dau)
            }

            if(row == 2 && spaceleft + spaceright <= 3 && (blockleft + blockright == 0 || blleft + blright == 1)){
                pushATK_3++;
                //(nuoc 3 lien tiep)
            }
        }
        else if(y-1>=0 &&E[x][y-1] == 0 && E[x][y+1] == 0){
            if(rowright >= 2 || rowleft >= 2){
                if(rowright == 2){
                    if((blockright == 0 || (blright == 1 && spaceright == 1)) && spaceright <= 2){
                        pushATK_3++;
                    }
                }
                if(rowleft == 2){
                    if((blockleft == 0 || (blleft == 1 && spaceleft == 1)) && spaceleft <= 2){
                        pushATK_3++;
                    }
                }
                if(rowleft == 3 && blockleft == 1 && spaceleft == 1){
                    pushATK_4++;
                }
                if(rowright == 3 && blockright == 1 && spaceright == 1){
                    pushATK_4++;
                }
            }
        }
        rowright = 0;
        rowleft = 0;
        blockleft = 0;
        blockright = 0;
        spaceleft = 0;
        spaceright = 0;
        blleft = 0;
        blright = 0;
        spacecount = 0;
        //cheo chinh
        for (int i = 1; i < 6; i++) {
            if (x - i >= 0 && y - i >= 0) {
                if (E[x - i][y - i] == a) {
                    rowleft += 1;
                }
                if (E[x - i][y - i] == b) {
                    blockleft += 1;
                    break;
                }
                if (x - i - 1 >= 0 && y - i - 1 >= 0 && E[x - i][y - i] == 0 && E[x - i - 1][y - i - 1] == b) {
                    blleft += 1;
                    break;
                }
                if (E[x - i][y - i] == 0) {
                    spaceleft += 1;
                }
                if (x - i - 1 >= 0 && y - i - 1 >= 0 && E[x - i][y - i] == 0 && E[x - i - 1][y - i - 1] == 0) {
                    spacecount+=1;
                    break;
                }
            }
        }
        for (int i = 1; i < 6; i++) {
            if (E[x + i][y + i] == a) {
                rowright += 1;
            }
            if (E[x + i][y + i] == b) {
                blockright += 1;
                break;
            }
            if (E[x + i][y + i] == 0 && E[x + i + 1][y + i + 1] == b) {
                blright += 1;
                break;
            }
            if (E[x + i][y + i] == 0) {
                spaceright += 1;
            }
            if (E[x + i][y + i] == 0 && E[x + i + 1][y + i + 1] == 0) {
                spacecount+=1;
                break;
            }
        }
        if (x-1>=0 &&y-1>=0 &&(E[x-1][y-1] != 0 || E[x+1][y+1] != 0)) {
            row = rowleft + rowright;
            if(row == 4){
                absoluteWin++;
                //9999
            }
            if(row == 3 && blockleft == 0 && blockright == 0 && spaceleft + spaceright <= 2){
                nearWin++;
                //9999*
            }
//            if(row == 3 && blockleft == 0 && blockright == 0 && spacecount == 2 && spaceleft + spaceright == 2){
//                nearWin++;
//                //9999*
//            }
            if(row == 3 && blockleft == 0 && blockright == 0 && spaceleft + spaceright == 3){
                close++;
                //9999*
            }
            if(row == 3 && blockleft == 1 && blockright == 1 && spaceleft + spaceright == 0){
                notValid ++;
            }
            if(row <= 2 && blockleft == 1 && blockright == 1 && spaceleft + spaceright <= 1){
                notValid ++;
            }
            if(row <=2 && (blockleft + blockright == 1 || blleft + blright == 1) && spaceleft+ spaceright ==0){
                notValid ++;
            }
            if(row == 3 && blockleft == 1 && blockright == 0 && spaceleft == 0){
                pushATK_4++;
                //Prep (nuoc 4 chan 1 dau)
            }
            if(row == 3 && blockleft == 0 && blockright == 1 && spaceright == 0){
                pushATK_4++;
                //Prep (nuoc 4 chan 1 dau)
            }
//            if(row == 3 && blockleft + blockright == 1 && spaceright + spaceleft == 2){
//                pushATK_4++;
//                //Prep (nuoc 4 chan 1 dau)
//            }
            if(row == 3 && blleft + blright == 2 && spaceleft + spaceright == 1){
                pushATK_4++;
                //Prep (nuoc 4 chan 1 dau)
            }

            if(row == 2 && spaceleft + spaceright <= 3 && (blockleft + blockright == 0 || blleft + blright == 1)){
                pushATK_3++;
                //(nuoc 3 lien tiep)
            }
        }
        else if(x-1>=0 &&y-1>=0 &&E[x-1][y-1] == 0 && E[x+1][y+1] == 0){
            if(rowright >= 2 || rowleft >= 2){
                if(rowright == 2){
                    if((blockright == 0 || (blright == 1 && spaceright == 1)) && spaceright <= 2){
                        pushATK_3++;
                    }
                }
                if(rowleft == 2){
                    if((blockleft == 0 || (blleft == 1 && spaceleft == 1)) && spaceleft <= 2){
                        pushATK_3++;
                    }
                }
                if(rowleft == 3 && blockleft == 1 && spaceleft == 1){
                    pushATK_4++;
                }
                if(rowright == 3 && blockright == 1 && spaceright == 1){
                    pushATK_4++;
                }
            }
        }
        rowright = 0;
        rowleft = 0;
        blockleft = 0;
        blockright = 0;
        spaceleft = 0;
        spaceright = 0;
        blleft = 0;
        blright = 0;
        spacecount = 0;
        //cheo phu
        for (int i = 1; i < 6; i++) {
            if (x - i >= 0 && y + i <= 20) {
                if (E[x - i][y + i] == a) {
                    rowleft += 1;
                }
                if (E[x - i][y + i] == b) {
                    blockleft += 1;
                    break;
                }
                if (x - i - 1 >= 0 && E[x - i][y + i] == 0 && E[x - i - 1][y + i + 1] == b) {
                    blleft += 1;
                    break;
                }
                if (E[x - i][y + i] == 0) {
                    spaceleft += 1;
                }
                if (x - i - 1 >= 0 && E[x - i][y + i] == 0 && E[x - i - 1][y + i + 1] == 0) {
                    spacecount+=1;
                    break;
                }
            }
        }
        for (int i = 1; i < 6; i++) {
            if (y - i >= 0 && x + i <= 20) {
                if (E[x + i][y - i] == a) {
                    rowright += 1;
                }
                if (E[x + i][y - i] == b) {
                    blockright += 1;
                    break;
                }
                if (y - i - 1 >= 0 && E[x + i][y - i] == 0 && E[x + i + 1][y - i - 1] == b) {
                    blright += 1;
                    break;
                }
                if (E[x + i][y - i] == 0) {
                    spaceright += 1;
                }
                if (y - i - 1 >= 0 && E[x + i][y - i] == 0 && E[x + i + 1][y - i - 1] == 0) {
                    spacecount+=1;
                    break;
                }
            }
        }
        if (x-1>=0 && y-1>=0 && (E[x+1][y-1] != 0 || E[x-1][y+1] != 0)) {
            row = rowleft + rowright;
            if(row == 4){
                absoluteWin++;
                //9999
            }
            if(row == 3 && blockleft == 0 && blockright == 0 && spaceleft + spaceright <= 1){
                nearWin++;
                //9999*
            }
            if(row == 3 && blockleft == 0 && blockright == 0 && spacecount == 2 && spaceleft + spaceright == 2){
                nearWin++;
                //9999*
            }
            if(row == 3 && blockleft == 0 && blockright == 0 && spaceleft + spaceright == 3){
                close++;
                //9999*
            }
            if(row == 3 && blockleft == 1 && blockright == 1 && spaceleft + spaceright == 0){
                notValid ++;
            }
            if(row <= 2 && blockleft == 1 && blockright == 1 && spaceleft + spaceright <= 1){
                notValid ++;
            }
            if(row <=2 && (blockleft + blockright == 1 || blleft + blright == 1) && spaceleft+ spaceright ==0){
                notValid ++;
            }
            if(row == 3 && blockleft == 1 && blockright == 0 && spaceleft == 0){
                pushATK_4++;
                //Prep (nuoc 4 chan 1 dau)
            }
            if(row == 3 && blockleft == 0 && blockright == 1 && spaceright == 0){
                pushATK_4++;
                //Prep (nuoc 4 chan 1 dau)
            }
//            if(row == 3 && blockleft + blockright == 1 && spaceright + spaceleft == 2){
//                pushATK_4++;
//                //Prep (nuoc 4 chan 1 dau)
//            }
            if(row == 3 && blleft + blright == 2 && spaceleft + spaceright == 1){
                pushATK_4++;
                //Prep (nuoc 4 chan 1 dau)
            }
            if(row == 2 && spaceleft + spaceright <= 3 && (blockleft + blockright == 0 || blleft + blright == 1)){
                pushATK_3++;
                //(nuoc 3 lien tiep)
            }
        }
        else if(x-1>=0 &&y-1>=0 &&E[x+1][y-1] == 0 && E[x-1][y+1] == 0){
            if(rowright >= 2 || rowleft >= 2){
                if(rowright == 2){
                    if((blockright == 0 || (blright == 1 && spaceright == 1)) && spaceright <= 2){
                        pushATK_3++;
                    }
                }
                if(rowleft == 2){
                    if((blockleft == 0 || (blleft == 1 && spaceleft == 1)) && spaceleft <= 2){
                        pushATK_3++;
                    }
                }
                if(rowleft == 3 && blockleft == 1 && spaceleft == 1){
                    pushATK_4++;
                }
                if(rowright == 3 && blockright == 1 && spaceright == 1){
                    pushATK_4++;
                }
            }
        }
        // xét các TH
        if(c == 3){
            if(nearWin == 1){
                return 1;
            }
            if(pushATK_4 == 2){
                return 2;
            }
            if(pushATK_4 == 1 && pushATK_3 >= 1){
                return 3;
            }
            if(pushATK_3 == 2){
                return 4;
            }
            if(close >= 1){
                return 5;
            }

        }
        if(c == 0){
            if (absoluteWin >= 1) {
                return 1;
            }
            if (nearWin >= 1) {
                return 2;
            }
            if ((pushATK_4 >= 1 && pushATK_3 >= 1) || pushATK_4 >= 2) {
                //System.out.println(pushATK_4 + "*");
                return 3;
            }
            if (pushATK_4 == 0 && pushATK_3 >= 2) {
                return 4;
            }
            if (pushATK_4 == 1) {
                return 5;
            }
            if (pushATK_3 == 1) {
                return 6;
            }
            if(a == 2 && notValid >= 2){
                return 7;
            }

        }
        return 0;
    }
    static int Def = 0;
    static int Atk = 0;
    public static int findBestloc(Point tmp, int[][] E,int a){
        int x = tmp.x;
        int y = tmp.y;
        int range = 3;
        if(SelectMove.startMove > 5){
            range = 5;
        }
        int nearX = 0;
        int count_space = 0;
        int count_ally = 0;
        int count_enemy = 0;
        int lv1 = 0;
        int lv2 = 0;
        int lv3 = 0;
        int lv0 = 0;
        int b = 1;

        // ngang
        for (int i = 1; i < 4; i++) {
            if (x - i >= 0 && E[x - i][y] == b) {
                count_enemy += 1;
            }
            if (x - i >= 0 && E[x - i][y] == a) {
                break;
            }
        }
        if(count_enemy >= 2){
            nearX +=1;
        }
        for (int i = 0; i < 4; i++) {
            if (E[x + i][y] == b) {
                count_enemy += 1;
            }
            if (E[x + i][y] == a) {
                break;
            }
        }
        if(count_enemy >= 2){
            nearX +=1;
        }
        for(int i = 1; i < range; i++){
            if(x-i < 0){
                break;
            }
            if(E[x-i][y] == 0){
                count_space +=1;
            }
            if(E[x-i][y] == a){
                count_ally +=1;
            }
            if(E[x-i][y] == b){
                count_enemy +=1;
                if(i<4){
                    lv0 +=1;
                }
                break;
            }
        }
        if(lv0 == 0){
            if(count_ally == 1){
                lv1 +=1;
            }
            if(count_ally == 2){
                lv2 +=1;
            }
            if(count_ally == 3 && count_enemy == 0){
                lv3 +=1;
            }
        }
        count_enemy = 0;
        count_space = 0;
        count_ally = 0;
        for(int i = 1; i < range; i++){
            if(E[x+i][y] == 0){
                count_space +=1;
            }
            if(E[x+i][y] == a){
                count_ally +=1;
            }
            if(E[x+i][y] == b){
                count_enemy +=1;
                if(i<4){
                    lv0 +=1;
                }
                break;
            }
        }
        if(lv0 == 0){
            if(count_ally == 1){
                lv1 +=1;
            }
            if(count_ally == 2){
                lv2 +=1;
            }
            if(count_ally == 3 && count_enemy == 0){
                lv3 +=1;
            }
        }
        count_enemy = 0;
        count_space = 0;
        count_ally = 0;


        // doc
        for (int i = 1; i < 4; i++) {
            if (y - i >= 0 && E[x ][y - i] == b) {
                count_enemy += 1;
            }
            if (y - i >= 0 && E[x ][y - i] == a) {
                break;
            }
        }
        if(count_enemy >= 2){
            nearX +=1;
        }
        for (int i = 0; i < 4; i++) {
            if (E[x][y + i] == b) {
                count_enemy += 1;
            }
            if (E[x][y + i] == a) {
                break;
            }
        }
        if(count_enemy >= 2){
            nearX +=1;
        }
        for(int i = 1; i < range; i++){
            if(y-i < 0){
                break;
            }
            if(E[x][y-i] == 0){
                count_space +=1;
            }
            if(E[x][y-i] == a){
                count_ally +=1;
            }
            if(E[x][y-i] == b){
                count_enemy +=1;
                if(i<4){
                    lv0 +=1;
                }
                break;
            }
        }
        if(lv0 == 0){
            if(count_ally == 1){
                lv1 +=1;
            }
            if(count_ally == 2){
                lv2 +=1;
            }
            if(count_ally == 3 && count_enemy == 0){
                lv3 +=1;
            }
        }
        count_enemy = 0;
        count_space = 0;
        count_ally = 0;
        for(int i = 1; i < range; i++){
            if(E[x][y+i] == 0){
                count_space +=1;
            }
            if(E[x][y+i] == a){
                count_ally +=1;
            }
            if(E[x][y+i] == b){
                count_enemy +=1;
                if(i<4){
                    lv0 +=1;
                }
                break;
            }
        }
        if(lv0 == 0){
            if(count_ally == 1){
                lv1 +=1;
            }
            if(count_ally == 2){
                lv2 +=1;
            }
            if(count_ally == 3 && count_enemy == 0){
                lv3 +=1;
            }
        }
        count_enemy = 0;
        count_space = 0;
        count_ally = 0;

        // cheo chinh

        for (int i = 1; i < 4; i++) {
            if (x - i >= 0 && y - i >= 0 && E[x - i][y - i] == b) {
                count_enemy += 1;
            }
            if (x - i >= 0 && y - i >= 0 && E[x - i][y - i] == a) {
                break;
            }
        }
        if(count_enemy >= 2){
            nearX +=1;
        }
        for (int i = 0; i < 4; i++) {
            if (E[x + i][y+i] == b) {
                count_enemy += 1;
            }
            if (E[x + i][y+i] == a) {
                break;
            }
        }
        if(count_enemy >= 2){
            nearX +=1;
        }
        for(int i = 1; i < range; i++){
            if(x - i < 0 || y - i  < 0){
                break;
            }
            if(E[x-i][y-i] == 0){
                count_space +=1;
            }
            if(E[x-i][y-i] == a){
                count_ally +=1;
            }
            if(E[x-i][y-i] == b){
                count_enemy +=1;
                if(i<4){
                    lv0 +=1;
                }
                break;
            }
        }
        if(lv0 == 0){
            if(count_ally == 1){
                lv1 +=1;
            }
            if(count_ally == 2){
                lv2 +=1;
            }
            if(count_ally == 3 && count_enemy == 0){
                lv3 +=1;
            }
        }
        count_enemy = 0;
        count_space = 0;
        count_ally = 0;
        for(int i = 1; i < range; i++){
            if(E[x+i][y+i] == 0){
                count_space +=1;
            }
            if(E[x+i][y+i] == a){
                count_ally +=1;
            }
            if(E[x+i][y+i] == b){
                count_enemy +=1;
                if(i<4){
                    lv0 +=1;
                }
                break;
            }
        }
        if(lv0 == 0){
            if(count_ally == 1){
                lv1 +=1;
            }
            if(count_ally == 2){
                lv2 +=1;
            }
            if(count_ally == 3 && count_enemy == 0){
                lv3 +=1;
            }
        }
        count_enemy = 0;
        count_space = 0;
        count_ally = 0;

        // cheo phu

        for (int i = 1; i < 4; i++) {
            if (x - i >= 0 && E[x - i][y + i] == b) {
                count_enemy += 1;
            }
            if (x - i >= 0 && E[x - i][y + i] == a) {
                break;
            }
        }
        if(count_enemy >= 2){
            nearX +=1;
        }
        for (int i = 0; i < 4; i++) {
            if (y - i >= 0 && E[x + i][y-i] == b) {
                count_enemy += 1;
            }
            if (y - i >= 0 && E[x + i][y-i] == a) {
                break;
            }
        }
        if(count_enemy >= 2){
            nearX +=1;
        }
        for(int i = 1; i < range; i++){
            if(x - i < 0){
                break;
            }
            if(E[x-i][y+i] == 0){
                count_space +=1;
            }
            if(E[x-i][y+i] == a){
                count_ally +=1;
            }
            if(E[x-i][y+i] == b){
                count_enemy +=1;
                if(i<4){
                    lv0 +=1;
                }
                break;
            }
        }
        if(lv0 == 0){
            if(count_ally == 1){
                lv1 +=1;
            }
            if(count_ally == 2){
                lv2 +=1;
            }
            if(count_ally == 3 && count_enemy == 0){
                lv3 +=1;
            }
        }
        count_enemy = 0;
        count_space = 0;
        count_ally = 0;
        for(int i = 1; i < range; i++){
            if(y - i < 0){
                break;
            }
            if(E[x+i][y-i] == 0){
                count_space +=1;
            }
            if(E[x+i][y-i] == a){
                count_ally +=1;
            }
            if(E[x+i][y-i] == b){
                count_enemy +=1;
                if(i<4){
                    lv0 +=1;
                }
                break;
            }
        }
        if(lv0 == 0){
            if(count_ally == 1){
                lv1 +=1;
            }
            if(count_ally == 2){
                lv2 +=1;
            }
            if(count_ally == 3 && count_enemy == 0){
                lv3 +=1;
            }
        }
        count_enemy = 0;
        count_space = 0;
        count_ally = 0;
        int score = 0;
        if(nearX == 2){
            score +=100;
        }
        if(nearX == 3){
            score +=200;
        }
        if(nearX == 4){
            score +=400;
        }
        if(lv1 >= 2){
            return 80*lv1 + score;
        }
        if(lv2 >= 2){
            return 200*lv2 + score;
        }
        if(lv3 >= 1 && (lv1 == 1 || lv2 == 1)){
            return 400*lv3 + 80*lv1 + 200*lv2 + score;
        }

        return 0;
    }
    public static int findBestloc2(Point tmp, int[][] E,int a){
        int x = tmp.x;
        int y = tmp.y;

        int block = 0;
        int row = 0;
        int check = 0;
        int check2 = 0;
        for(int i = 1; i<= 4;i++){
            if(x>=1 && E[x-1][y] == 0){
                if(E[x+i][y] == 1){
                    block+=1;
                    if(i == 4){
                        check2 = 1;
                    }
                }
                if(E[x+i][y] == 2){
                    row +=1;
                    if(i == 4){
                        check = 1;
                    }
                }
            }
        }
        Count(row, block, check, check2);
        block = 0;
        row = 0;
        check = 0;
        check2 = 0;
        for(int i = 1; i<= 4;i++){
            if(E[x+1][y] == 0 && x-i>=0){
                if(E[x-i][y] == 1){
                    block+=1;
                    if(i == 4){
                        check2 = 1;
                    }
                }
                if(E[x-i][y] == 2){
                    row +=1;
                    if(i == 4){
                        check = 1;
                    }
                }
            }
        }
        Count(row, block, check, check2);
        block = 0;
        row = 0;
        check = 0;
        check2 = 0;
        for(int i = 1; i<= 4;i++){
            if(y>=1 && E[x][y-1] == 0){
                if(E[x][y+i] == 1){
                    block+=1;
                    if(i == 4){
                        check2 = 1;
                    }
                }
                if(E[x][y+i] == 2){
                    row +=1;
                    if(i == 4){
                        check = 1;
                    }
                }
            }
        }
        Count(row, block, check, check2);
        block = 0;
        row = 0;
        check = 0;
        check2 = 0;
        for(int i = 1; i <= 4;i++){
            if(y >= i && E[x][y+1] == 0){
                if(E[x][y-i] == 1){
                    block+=1;
                    if(i == 4){
                        check2 = 1;
                    }
                }
                if(E[x][y-i] == 2){
                    row +=1;
                    if(i == 4){
                        check = 1;
                    }
                }
            }
        }
        Count(row, block, check, check2);
        block = 0;
        row = 0;
        check = 0;
        check2 = 0;
        for(int i = 1; i<= 4;i++){
            if(x >=1 && y >= 1 && E[x-1][y-1] == 0){
                if(E[x+i][y+i] == 1){
                    block+=1;
                    if(i == 4){
                        check2 = 1;
                    }
                }
                if(E[x+i][y+i] == 2){
                    row +=1;
                    if(i == 4){
                        check = 1;
                    }
                }
            }
        }
        Count(row, block, check, check2);
        block = 0;
        row = 0;
        check = 0;
        check2 = 0;
        for(int i = 1; i<= 4;i++){
            if(x >=i && y >= i && E[x+1][y+1] == 0){
                if(E[x-i][y-i] == 1){
                    block+=1;
                    if(i == 4){
                        check2 = 1;
                    }
                }
                if(E[x-i][y-i] == 2){
                    row +=1;
                    if(i == 4){
                        check = 1;
                    }
                }
            }
        }
        Count(row, block, check, check2);
        block = 0;
        row = 0;
        check = 0;
        check2 = 0;

        for(int i = 1; i<= 4;i++){
            if(x >=1 && y >= i && E[x-1][y+1] == 0){
                if(E[x+i][y-i] == 1){
                    block+=1;
                    if(i == 4){
                        check2 = 1;
                    }
                }
                if(E[x+i][y-i] == 2){
                    row +=1;
                    if(i == 4){
                        check = 1;
                    }
                }
            }
        }
        Count(row, block, check, check2);
        block = 0;
        row = 0;
        check = 0;
        check2 = 0;
        for(int i = 1; i<= 4;i++){
            if(x >= i && y >= 1 && E[x+1][y-1] == 0){
                if(E[x-i][y+i] == 1){
                    block+=1;
                    if(i == 4){
                        check2 = 1;
                    }
                }
                if(E[x-i][y+i] == 2){
                    row +=1;
                    if(i == 4){
                        check = 1;
                    }
                }
            }
        }
        Count(row, block, check, check2);
        block = 0;
        row = 0;
        check = 0;
        check2 = 0;

        int c = 30*Atk + 20*Def;
        Atk = 0;
        Def = 0;
        return c;
    }
    public static void Count(int row,int block,int check,int check2){
        if(row == 0){
            if(block == 1){
                Def+=1;
            }
            if(block == 2){
                Def+=2;
            }
        }
        if(row == 1 && check == 1){
            if(block >= 1){
                Def+=1;
            }
        }

        if(block == 0){
            if(row == 1){
                Atk+=1;
            }
            if(row == 2){
                Atk+=2;
            }
        }
        if(block == 1 && check2 == 1){
            if(row >= 1){
                Atk+=1;
            }
        }
    }
}

