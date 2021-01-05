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

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getReponses() {
        return reponses;
    }

    public void setReponses(ArrayList<String> reponses) {
        this.reponses = reponses;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }
}
