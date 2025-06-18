package moveSet;

public class Point {
    int x;
    int y;
    double score;
    int rank;
    public Point() {

    }
    public Point(int x, int y, double score, int rank) {
        this.x = x;
        this.y = y;
        this.score = score;
        this.rank = rank;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
