package database;

public class TopStudent {
    private final String surname;
    private final String name;
    private final int scoreEx;
    private final int scorePr;

    public TopStudent(String surname, String name, int scoreEx, int scorePr) {
        this.surname = surname;
        this.name = name;
        this.scoreEx = scoreEx;
        this.scorePr = scorePr;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public int getScoreEx() {
        return scoreEx;
    }

    public int getScorePr() {
        return scorePr;
    }
}
