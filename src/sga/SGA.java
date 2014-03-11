/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sga;

import java.util.Random;

/**
 *
 * @author Atakan
 */
public class SGA {

    public static Individual solve(ThreeCNF CNF, int genNum, int popSize) {

        Random r = new Random();

        Individual[] population = new Individual[popSize];
        for (int i = 0; i < popSize; i++) {
            population[i] = new Individual(CNF.getNv()); //Uniformly randomize the population at the beginning
        }
        
        for (int gen = 0; gen < genNum; gen++) {
            System.out.println(gen);
            
            //Tournament selection
            Individual[] matingPool = new Individual[popSize];
            for (int i = 0; i < popSize; i++) {
                Individual i1 = population[r.nextInt(popSize)];
                Individual i2 = population[r.nextInt(popSize)];
                Individual winner = i2;
                if (CNF.getFitness(i1) > CNF.getFitness(i2)) {
                    winner = i1;
                }
                matingPool[i] = new Individual(winner);
            }
            
            //Two-point crossover
            Individual[] offspring = new Individual[popSize];
            for (int i = 0; i < popSize; i = i + 2) {
                Individual parent1 = matingPool[r.nextInt(popSize)];
                Individual parent2 = matingPool[r.nextInt(popSize)];
                Individual offspring1 = new Individual(parent1);
                Individual offspring2 = new Individual(parent2);
                if (r.nextDouble() < 0.8) {
                    int point1 = r.nextInt(CNF.getNv() - 1) + 1;
                    int point2 = r.nextInt(CNF.getNv() - 1) + 1;
                    if (point1 == point2) {
                        point2++;
                        if (point2 == CNF.getNv()) {
                            point2 = 1;
                        }
                    }
                    if (point1 > point2) {
                        int temp = point1;
                        point1 = point2;
                        point2 = temp;
                    }

                    for (int j = point1; j < point2; j++) {
                        offspring1.varAt(j, parent2.varAt(j));
                        offspring2.varAt(j, parent1.varAt(j));
                    }
                }
                offspring[i] = new Individual(offspring1);
                offspring[i + 1] = new Individual(offspring2);
            }
            
            //Bitwise mutation
            for (int i = 0; i < popSize; i++) {
                for (int j = 0; j < CNF.getNv(); j++) {
                    if (r.nextDouble() < (1 / CNF.getNv())) {
                        offspring[i].varAt(j, !offspring[i].varAt(j));
                    }
                }
            }
            
            //Elite and worst individual selection
            int eliteIndividual = 0, worstOffspring = 0;
            for (int i = 1; i < popSize; i++) {
                if (CNF.getFitness(population[i]) > CNF.getFitness(population[eliteIndividual])) {
                    eliteIndividual = i;
                }
                if (CNF.getFitness(population[i]) < CNF.getFitness(population[worstOffspring])) {
                    worstOffspring = i;
                }
            }
            Individual elite = new Individual(population[eliteIndividual]);
            
            //Elitism
            for (int i = 0; i < popSize; i++) {
                if (i == worstOffspring) {
                    population[i] = new Individual(elite);
                } else {
                    population[i] = new Individual(offspring[i]);
                }
            }

            //Check if solution
            for (int i = 0; i < popSize; i++) {
                if (CNF.isSatisfied(population[i])) {
                    return population[i];
                }
            }
        }

        return population[0];
    }
}
