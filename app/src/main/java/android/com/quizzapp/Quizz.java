package android.com.quizzapp;

import java.util.ArrayList;

public class Quizz {
    private String question;
    private ArrayList<String> reponses;
    // @attribute si il y a plusieurs reponses concatener les
    private String correctAnswer;
    private QuestionType type;

    public Quizz(String question, ArrayList<String> reponses, String correctAnswer, QuestionType type) {
        this.question = question;
        this.reponses = reponses;
        this.correctAnswer = correctAnswer;
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getReponses() {
        return reponses;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public QuestionType getType() {
        return type;
    }
}
