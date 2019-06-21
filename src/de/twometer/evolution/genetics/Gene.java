package de.twometer.evolution.genetics;

public class Gene {

    private Gender genderRestriction;

    private float value;

    Gene(Gender genderRestriction, float value) {
        this.genderRestriction = genderRestriction;
        this.value = value;
    }

    public Gender getGenderRestriction() {
        return genderRestriction;
    }

    public float getValue() {
        return value;
    }
}
