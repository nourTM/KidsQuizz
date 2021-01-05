package android.com.quizzapp;

import java.util.ArrayList;

public class Quizz {
    private String question;
    private ArrayList<String> reponses;
    private int correctAnswerIndex;

    public Quizz(String question, ArrayList<String> reponses, int correctAnswerIndex) {
        this.question = question;
        this.reponses = reponses;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getReponses() {
        return reponses;
    }

    public void setReponses(ArrayList<String> reponses) {
        this.reponses = reponses;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }
}
