package com.sam.enterpriseai.provider.vectorstore.inmemory;

import java.util.List;

public final class CosineSimilarity {

    private CosineSimilarity() {
    }

    public static double calculate(
            List<Double> first,
            List<Double> second) {

        double dotProduct = 0.0;
        double firstMagnitude = 0.0;
        double secondMagnitude = 0.0;

        for (int i = 0; i < first.size(); i++) {

            dotProduct += first.get(i) * second.get(i);

            firstMagnitude += first.get(i) * first.get(i);

            secondMagnitude += second.get(i) * second.get(i);
        }

        if (firstMagnitude == 0 || secondMagnitude == 0) {
            return 0.0;
        }

        return dotProduct /
                (Math.sqrt(firstMagnitude)
                        * Math.sqrt(secondMagnitude));
    }
}