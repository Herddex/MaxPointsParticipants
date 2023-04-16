package ro.ubb.domain;

public class Grade implements HasID<Pair<String, String>> {
    Pair<String, String> id;
    private double value;
    private int deliveryWeek;
    private String feedback;

    public Grade(Pair<String, String> id, double value, int deliveryWeek, String feedback) {
        this.id = id;
        this.value = value;
        this.deliveryWeek = deliveryWeek;
        this.feedback = feedback;
    }

    @Override
    public Pair<String, String> getID() { return id; }

    @Override
    public void setID(Pair<String, String> idNota) { this.id = idNota; }

    public double getValue() { return value; }

    public void setValue(double value) { this.value = value; }

    public int getDeliveryWeek() { return deliveryWeek; }

    public void setDeliveryWeek(int deliveryWeek) { this.deliveryWeek = deliveryWeek; }

    public String getFeedback() { return feedback; }

    public void setFeedback(String feedback) { this.feedback = feedback; }

    @Override
    public String toString() {
        return "Nota{" +
                "id_student = " + id.getObject1() +
                ", id_tema = " + id.getObject2() +
                ", nota = " + value +
                ", saptamanaPredare = " + deliveryWeek +
                ", feedback = '" + feedback + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return id.equals(grade.id);
    }
}
