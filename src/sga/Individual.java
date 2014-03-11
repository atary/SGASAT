/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sga;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Atakan
 */
public class Individual{

    private boolean[] variables; //Current values assigned to the variables
    private int nv; //Number of variables

    public Individual(int nv) {
        Random r = new Random();
        this.nv = nv;
        variables = new boolean[nv];
        for (int i = 0; i < nv; i++) {
            variables[i] = r.nextBoolean();
        }
    }
    
    public Individual(Individual ind){
        nv = ind.nv;
        variables = new boolean[nv];
        for (int i = 0; i < nv; i++) {
            variables[i] = ind.varAt(i);
        }
    }

    public void writeOutput(String outFile) { //Write variable values to the output file
        try {
            File file = new File(outFile);
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter out = new BufferedWriter(fw)) {
                out.write(nv + "\n"); //First line is the number of variables
                for (int i = 0; i < nv; i++) { //Write values line by line
                    out.write((variables[i] ? 1 : 0) + "\n");
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(-1);
        }
    }

    public boolean varAt(int i) {
        return variables[i];
    }
    
    public void varAt(int i, boolean v) {
        variables[i] = v;
    }
    
    @Override
    public String toString(){
        return Arrays.toString(variables);
    }
}
