package com.bitwise;
public class SubsetBitMain
{
	// Print all subsets of given set[]
	static void printSubsets(int set[])
	{
		int n = set.length;
 
		// Run a loop from 0 to 2^n
		for (int i = 0; i < (1<<n); i++)
		{
			System.out.print("{ ");
			int m = 1; // m is used to check set bit in binary representation.
			// Print current subset
			for (int j = 0; j < n; j++)
			{
				if ((i & m) > 0)
				{		
					System.out.print(set[j] + " ");
				}
				m = m << 1;
			}
			
			System.out.println("}");
		}
	}
 
	// Driver code
	public static void main(String[] args)
	{
		int set[] = {1 ,2, 3};
		printSubsets(set);
		//https://java2blog.com/find-subsets-set-power-set/
	}
}