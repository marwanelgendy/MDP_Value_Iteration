package com.company;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        int[][] reward =  { {100,-1,10} , {-1,-1,-1} , {-1,-1,-1} } ;

        Value_Iteration_Solver solver = new Value_Iteration_Solver(reward);

        solver.solve();
    }


}


