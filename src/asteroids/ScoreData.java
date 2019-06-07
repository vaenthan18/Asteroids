package asteroids;

public class ScoreData implements Comparable<ScoreData> {

    private int score;
    private String name;

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public ScoreData(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(ScoreData scores) {
        return (scores.score - this.score);
    }
}
