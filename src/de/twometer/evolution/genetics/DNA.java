package de.twometer.evolution.genetics;

import java.util.HashMap;
import java.util.Map;

public class DNA {

    private Map<String, Gene> genes = new HashMap<>();

    public void registerGene(String id, float defaultValue) {
        genes.put(id, new Gene(Gender.Any, defaultValue));
    }

    public void registerGene(String id, float defaultValue, Gender restriction) {
        genes.put(id, new Gene(restriction, defaultValue));
    }

    public Gene getGene(String id) {
        return genes.get(id);
    }

    public Map<String, Gene> getGenes() {
        return genes;
    }
}
