import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	   private double threshold[];
	   private double mean;
	   private double staddev;
	   private final double shu = 1.96;
	   private final int trials;
	   
	   // Constructor
	   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
	   {
		   // 用this 表示要 assign 此class中的某值
		   this.trials = trials;
		   threshold = new double[trials];
		   staddev = 0;
		   int i, row, col;
		   double counter, count_for_mean = 0;

           // Do trials time iterations, each time doing it until it percolates
           for (i = 0 ; i < trials ; i++) {
        	   Percolation model = new Percolation(n);
        	   counter = 0;
        	   // when the square haven't percolated
        	   while(!model.percolates()) {
        		   counter ++; 
        		   count_for_mean++;
        		   //生成隨機座標
        		   row = StdRandom.uniform(n) + 1;
                   col = StdRandom.uniform(n) + 1;        
                   model.open(row, col);
        	   }
        	   threshold[i] = counter/(n*n);
           }
           mean = count_for_mean / (trials*n*n);
           
           // find staddev
           for (i = 0 ; i < trials ; i++) {    
        	   staddev += (mean-threshold[i])*(mean-threshold[i]);   
           }
           staddev /= trials;
           
	   }
	   public double mean()                          // sample mean of percolation threshold
	   {
		   return mean;
	   }
	   public double stddev()                        // sample standard deviation of percolation threshold
	   {
		   return staddev;
	   }
	  // /*
	   public double confidenceLo()                  // low  endpoint of 95% confidence interval
	   {
		   return mean - shu * staddev / Math.sqrt(trials);
	   }
	   public double confidenceHi()                  // high endpoint of 95% confidence interval
	   {
		   return mean + shu * staddev / Math.sqrt(trials);
	   }
	  // */
	   public static void main(String[] args)        // test client (described below)
	   {
	        int n = StdIn.readInt();       //輸入一個n和試驗次數T
	        int T = StdIn.readInt();
	        
		    PercolationStats model = new PercolationStats(n,T);
		    
	        System.out.println("mean is " + model.mean());
	        System.out.println("stddev is " + model.stddev());
	        System.out.println(model.confidenceLo());
	        System.out.println(model.confidenceHi());
	   }
}