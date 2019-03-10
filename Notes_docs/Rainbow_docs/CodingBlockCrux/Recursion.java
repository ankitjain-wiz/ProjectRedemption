
public class Recursion {

	public static int  factorial(int n){
		if(n == 0){
			return 1;
		}
		int ans = n*factorial(n-1);;
		return ans;
	}

	public static int fib(int n){
		if(n == 0 || n== 1){
			return n;
		}

		return fib(n-1) + fib(n-2);
	}


	public static String getString(int n){
		String str = "";
		switch(n){
		case 0 :
			str = "zero";
			break;
		case 1 :
			str = "one";
			break;
		case 2 :
			str = "two";
			break;
		case 3 :
			str = "three";
			break;
		case 4 :
			str = "four";
			break;
		default	:
			str = "";
		}
		return str;
	}

	public static String numberToWords(int num){
		if(num < 10){
			String word = getString(num);
			//	System.out.print(word+" ");
			return word;
		}		
		String smallAns = numberToWords(num/10);
		int lastDigit = num % 10;
		String word = getString(lastDigit);
		String ans = smallAns + word;
		return ans;
	}

	
	public static boolean checkSorted(int[] input){
		if(input.length <= 1){
			return true;
		}
		if(input[0] < input[1]){
			int[] smallInput = new int[input.length-1];
			for(int i = 1; i < input.length; i++){
				smallInput[i-1] = input[i];
			}
			boolean smallAns = checkSorted(smallInput);
			return smallAns;	
		}
		return false;
	}
	// check if array is sorted from beginIndex to end
	public static boolean checkSortedBetter(int input[], int beginIndex){
		if(beginIndex >= input.length - 1){
			return true;
		}
		if(input[beginIndex] < input[beginIndex + 1]){
			boolean smallAns = checkSortedBetter(input, beginIndex+ 1);
			return smallAns;
		}
		return false;
	}

	public static String replacePi(String input){
		if(input.length() <= 1){
			return input;
		}
		if(input.charAt(0) == 'p' && input.charAt(1) == 'i'){
			String smallAns = replacePi(input.substring(2));
			return "3.14" + smallAns;
		}
		else{
			String smallAns = replacePi(input.substring(1));
			return input.charAt(0) + smallAns;
		}
	}
	
	
	public static int firstIndex(int[] input, int beginIndex){
		
		if(beginIndex > input.length-1){
			return -1;
		}
		
		if(input[beginIndex] == 7){
			return beginIndex;
		}
	//	beginIndex++;
		return firstIndex(input, beginIndex+1);
	}
	
	public static int lastIndex(int[] input, int beginIndex){
		if(beginIndex > input.length-1){
			return -1;
		}	
		int smallAns = lastIndex(input, beginIndex+1);
		if(smallAns != -1){
			return smallAns;
		}
		if(input[beginIndex] == 7){
			return beginIndex;
		}
		 return -1;
	}
		
	
	public static void selectionSortR(int[] input, int beginIndex){
		if(beginIndex >= input.length - 1){
			return;
		}
		int minPos = beginIndex;
		int min = input[beginIndex];
		for(int i  = beginIndex+1; i < input.length;i++){
			if(input[i] < min){
				min = input[i];
				minPos = i;
			}
		}
		if(minPos != beginIndex){
			input[minPos] = input[beginIndex];
			input[beginIndex] = min;
		}	
		selectionSortR(input, beginIndex+1);
		
	}
	
	public static void printArray(int[] array){
		for(int i = 0; i < array.length; i++){
			System.out.print(array[i]+" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//	System.out.println(factorial(5));
		//System.out.println(fib(4));
//		String str = numberToWords(321);
//		System.out.println(str);
//		int input[] = {1,2,3,6,5};
//		System.out.println(checkSorted(input));
//		System.out.println(checkSortedBetter(input, 0));
//		String str = "pipxpi";
//		System.out.println(replacePi(str));
		int input[] = {2,1,6,5,3};
		//System.out.println(firstIndex(input, 0));
		selectionSortR(input, 0);
		printArray(input);
	}

}
