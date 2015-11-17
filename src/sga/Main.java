/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sga;

/**
 *
 * @author Atakan
 */
public class Main {

    public static void main(String[] args) {
        int x = 5;
        if (args.length != 4) {
            System.err.println("Usage: SGA 1000 100 inFile outFile");
            System.exit(-1);
        }

        //ThreeCNF.generateRandomInput("input.txt", 15, 40); //Generate an input file with 15 variables and 40 clauses.

        int genNum = Integer.parseInt(args[0]); //Number of generations
        int popSize = Integer.parseInt(args[1]); //Population size
        String inFile = args[2]; //Relative input file path
        String outFile = args[3]; //Relative output file path

        ThreeCNF CNF = new ThreeCNF(inFile); //Generate a 3-CNF by reading the input file

        Individual solution = SGA.solve(CNF, genNum, popSize); //Solve the 3-SAT problem and return the solution

        if (CNF.isSatisfied(solution)) {
            solution.writeOutput(outFile);
            System.out.println("Problem is solved and the final output is written to the file " + outFile);
        } else {
            System.out.println("Problem is NOT solved after " + genNum + " generations.");
        }
    }
}
