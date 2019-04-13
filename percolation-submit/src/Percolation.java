import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{	
	    private final WeightedQuickUnionUF model;
		public	boolean Percolation;
		private final boolean[] open_table;
		//private boolean[] connect_top;
		//private boolean[] connect_down;
		private final int nvalue;
		private int site_count;
		
		
	   // constructor
	   public Percolation(int n)                // create n-by-n grid, with all sites blocked
	   {
		   if (n <= 0)
	            throw new java.lang.IllegalArgumentException();
		   
		   model = new  WeightedQuickUnionUF(n*n+2);
		   open_table = new boolean[n*n+2];
		   //connect_top = new boolean[n*n];
		   //connect_down = new boolean[n*n];
		   Percolation = false;
		   nvalue = n;
		   site_count = 0;
		   for (int i = 0; i < n*n+2 ; i++){
			   open_table[i] = false;
			  // connect_top[i] = false;
			   // connect_down[i] = false;
		   }
	   }
	   
	   private int Max(int a, int b) {
		   if (a >= b) return a;
		   else return b;
	   }
	   private int Min(int a, int b) {
		   if (a < b) return a;
		   else return b;
	   }
	   
	   // Fill water process ＆ connect with 1D array position
	   // key 是 每一次open時看看連接者是否
	   public void open(int row, int col)    // open site (row, col) if it is not open already
	   {
		   site_count++;
		   int i = row ,j = col;
		   open_table [twoDmap(row,col)] = true;
		   
		   // always union small one
		   //System.out.println((twoDmap(row,col)));
		   model.union(Max(twoDmap(row,col),twoDmap(i,j)), Min(twoDmap(row,col),twoDmap(i,j)));
		   
		   // model.union(Max(twoDmap(row,col),twoDmap(i,j)), Min(twoDmap(row,col),twoDmap(i,j)));
		   
		   if(row == 1)
			   model.union(twoDmap(row,col), nvalue * nvalue);
		   if(row == nvalue)
			   model.union(twoDmap(row,col), nvalue * nvalue + 1);		   
		   
		   // check 上是否連接, 先確定是否能access再access
		   i = row - 1;
		   j = col;
		  
		   if (isLeg(i,j)) 
			   if(open_table[twoDmap(row,col)])
				   model.union(Max(twoDmap(row,col),twoDmap(i,j)), Min(twoDmap(row,col),twoDmap(i,j)));
				   //model.union( Min(twoDmap(row,col),twoDmap(i,j)), Max(twoDmap(row,col),twoDmap(i,j)));
				   
		   // check 下是否連接
		   i = row + 1;
		   j = col;
		   if (isLeg(i,j)) 
			   if(open_table[twoDmap(row,col)]) {
				   //model.union(Max(twoDmap(row,col),twoDmap(i,j)), Min(twoDmap(row,col),twoDmap(i,j)));
				   model.union( Min(twoDmap(row,col),twoDmap(i,j)), Max(twoDmap(row,col),twoDmap(i,j)));
			   }
		   
		   // check 左是否連接
		   i = row;
		   j = col - 1;
		   if (isLeg(i,j)) 
			   if(open_table[twoDmap(row,col)]) {
				   model.union(Max(twoDmap(row,col),twoDmap(i,j)), Min(twoDmap(row,col),twoDmap(i,j)));
				   //model.union( Min(twoDmap(row,col),twoDmap(i,j)), Max(twoDmap(row,col),twoDmap(i,j)));			
		   		}
				  
		   
		   // check 右是否連接
		   i = row;
		   j = col + 1;
		   if (isLeg(i,j)) 
			   if(open_table[twoDmap(row,col)])
				   //model.union(Max(twoDmap(row,col),twoDmap(i,j)), Min(twoDmap(row,col),twoDmap(i,j)));
				   model.union( Min(twoDmap(row,col),twoDmap(i,j)), Max(twoDmap(row,col),twoDmap(i,j)));
		   
		   Percolation |= (model.connected(twoDmap(row,col), nvalue * nvalue) && model.connected(twoDmap(row,col), nvalue * nvalue + 1)); 
		  // System.out.println("the final root is " + model.find(twoDmap(row,col)));
	   }
	   
	   // Say row major, change 2D -> 1D
	   private int twoDmap (int row, int col) {
		   row --; col--;
		   return (row) * nvalue + col;
	   }
	   
	   
	   private boolean isLeg(int row, int col)  // is site (row, col) legal position?
	   {	
		   return (row >= 1) && (row <= nvalue) && (col >= 1) && (col <= nvalue);
	   }
	   
	   
	   
	   // check if open -> 是否已開
	   public boolean isOpen(int row, int col)  // is site (row, col) open?
	   {
		   if (!isLeg(row-1,col-1)) 
			   throw new java.lang.IllegalArgumentException();
		   else
			   return open_table[twoDmap(row,col)];
	   }
	   
	   
	   // check Full -> 是否有連接 row == 0的
	   public boolean isFull(int row, int col)  // is site (row, col) full?
	   {
		   if (!isLeg(row-1,col-1)) 
			   throw new java.lang.IllegalArgumentException();
		   else 
			   return model.connected(twoDmap(row,col), nvalue * nvalue);
	   }
	   
	   
	   // number of sites being open, when connect 就可 count
	   public int numberOfOpenSites()       // number of open sites
	   {
		   return site_count;
	   }
	   
	   public boolean percolates()              // does the system percolate?
	   {
		   return Percolation;
	   }
	   
	   
	   
	  
	public static void main(String[] args)
	{
		/*
		Percolation model = new Percolation(2);
		model.open(1,1);
		model.open(2,2);
		model.open(1,2);
		System.out.println(model.percolates());
		//*/
	}
}