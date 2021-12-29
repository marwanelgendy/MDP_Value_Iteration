package com.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Value_Iteration_Solver {

    private final int ROWS = 3 ;

    private final int COLS = 3 ;

    private final int MOVES = 4 ;

    private final float gamma = 0.99f ;

    private final float p1 =  0.8f ; //80% of the time the agent goes in the direction it selects;

    private final float p2 = 0.1f ;  //the rest of the time it moves at right angles to the intended direction.

    private float [][] V ;

    private int [][] policy ;

    private int [][] reward ;

    private List<Float> Q ;

    private int[][] possible_moves ;

    public Value_Iteration_Solver(int[][] reward) {

        this.reward = reward;

        V = new float [ROWS][COLS] ;

        policy = new int[ROWS][COLS] ;

        for(int[] i : policy)
            Arrays.fill(i,-1);

        Q = new LinkedList<>();

        possible_moves = new int[][]{ { -1,0,1,0} , {0,1,0,-1}  };
    }

    public void solve (){

        while (true)
        {

            float[][] oldV = V.clone();

            int [][] oldPolicy = policy.clone();

            float expected_val = 0f;

            for (int i = 0 ; i < ROWS ; i++)
            {
                for (int j = 0 ; j < COLS ; j++)
                {

                    float max_v = Float.MIN_VALUE;

                    int max_index = -1 ;

                    if ( (i == 0 && j == 0) || (i == 0 && j == 2)){

                        max_v = reward[i][j] ;

                        max_index = 4 ;
                    }

                    else {

                        for( int action = 0 ; action < MOVES ; action++){

                            int selected_action_x = i + possible_moves[0][action] ;
                            int selected_action_y = j + possible_moves[1][action] ;

                            if( selected_action_x < MOVES && selected_action_x >= 0 && selected_action_y < MOVES && selected_action_y >= 0) //A collision with a wall results in no movement.
                            {

                                for (int k = 0 ; k < MOVES ; k++)
                                {
                                    int move_x = i + possible_moves[0][k] ;

                                    int move_y = j + possible_moves[1][k] ;

                                    if( move_x < ROWS && move_x >= 0 && move_y < COLS && move_y >= 0){ //A collision with a wall results in no movement.

                                        if( !( Math.abs(selected_action_x -  move_x) == 2 || Math.abs(selected_action_y -  move_y) == 2 ) ){ // ignore the opposite direction for selected direction

                                            if( selected_action_x == move_x && selected_action_y == move_y ){ // selected direction

                                                expected_val += p1 * ( reward[i][j] + gamma * oldV[move_x][move_y] ) ;
                                            }
                                            else {

                                                expected_val += p2 * ( reward[i][j] + gamma * oldV[move_x][move_y] ) ;
                                            }

                                        }

                                    }
                                }
                            }

                            if(max_v < expected_val) {

                                max_v = expected_val;

                                max_index = action;
                            }
                            expected_val = 0;

                        }

                    }

                    V[i][j] = max_v ;
                    policy[i][j] = max_index ;
                }
            }

            if (check_converge(policy , oldPolicy))  break;
        }

        print_array(policy , V);
    }

    public boolean check_converge(int[][] arr1 , int[][] arr2){

        for (int i=0 ; i< ROWS ; i++)
        {
            for (int j = 0 ; j < COLS ; j++){

                if (arr1[i][j] != arr2[i][j]) return false ;
            }
        }

        return true ;
    }

    private void print_array ( int[][] policy , float[][] V ){

        for (int i = 0 ; i < ROWS ; i++)
        {
            for (int j = 0 ; j < COLS ; j++)
            {
                if(policy[i][j] == 0) System.out.print("UP ");

                else if(policy[i][j] == 1) System.out.print("RIGHT ");

                else if(policy[i][j] == 2) System.out.print("DOWN ");

                else if(policy[i][j] == 3) System.out.print("LEFT ");

                else if (policy[i][j] == 4) System.out.print("EXIT ");
            }

            System.out.println("\n");
        }

        System.out.println("\n");
        System.out.println("|-----|-----|-----|");

        for (int i = 0 ; i < 3 ; i++) {
                System.out.println("|  " + V[i][0] + "  |  " + V[i][1] + "  |  " + V[i][2] + "  |");
                System.out.println("|-----|-----|-----|");
        }

            System.out.println("\n");
    }
}
