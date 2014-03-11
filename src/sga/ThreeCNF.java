/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sga;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Atakan
 */
public class ThreeCNF {

    private int nc; //Number of clauses
    private int nv; //Number of variables
    private int[][] clauses; //3-CNF clauses

    public ThreeCNF(String inFile) {
        FileReader fr = null;
        try {
            File file = new File(inFile);
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line0 = br.readLine(); //nc and nv are in the first line
            nv = Integer.parseInt(line0.split(" ")[0]);
            nc = Integer.parseInt(line0.split(" ")[1]);

            //variables = new boolean[nv];

            clauses = new int[nc][nv];

            for (int i = 0; i < nc; i++) { //Read lines one by one
                String line = br.readLine();
                String[] variableValues = line.split(" ");
                for (int j = 0; j < nv; j++) { //Read rows one by one
                    clauses[i][j] = Integer.parseInt(variableValues[j]);
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(-1);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                System.err.println(ex);
                System.exit(-1);
            }
        }
    }

    public int getFitness(Individual ind) { //Calculates number of unsatisfied clauses using the given values for the variables
        int num = 0;
        boolean satisfied;
        for (int i = 0; i < nc; i++) { //For each clause
            satisfied = false;
            for (int j = 0; j < nv; j++) {
                if (ind.varAt(j) && clauses[i][j] == 1) { //One match is enough to satisfy the clause
                    satisfied = true;
                    break;
                }
                if (!ind.varAt(j) && clauses[i][j] == -1) { //One match is enough to satisfy the clause
                    satisfied = true;
                    break;
                }
            }
            if (!satisfied) { //If no variable satisfies the clause then increase the counter
                num++;
            }
        }
        return nc-num;
    }

    public boolean isSatisfied(Individual ind) {
        return getFitness(ind) == nc; //3-CNF is satisfied if there are no unsatisfied clauses
    }

    public static void generateRandomInput(String outFile, int nv, int nc) { //Generate an input file
        try {
            Random r = new Random();
            File file = new File(outFile);
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter out = new BufferedWriter(fw)) {
                out.write(nv + " " + nc + "\n");
                for (int i = 0; i < nc; i++) {
                    ArrayList<Integer> clause = new ArrayList<>();
                    for (int j = 0; j < nv; j++) {
                        clause.add(0);
                    }
                    clause.set(0, r.nextBoolean() ? 1 : -1); //
                    clause.set(1, r.nextBoolean() ? 1 : -1); //Exactly 2 non zero values (1 or -1)
                    clause.set(2, r.nextBoolean() ? 1 : -1); //
                    Collections.shuffle(clause); //Shuffle the array so that non zero values are randomly distributed
                    for (int j = 0; j < nv; j++) {
                        out.write(clause.get(j) + " ");
                    }
                    out.write("\n");
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(-1);
        }
    }
    
    public int getNv(){
        return nv;
    }
}
