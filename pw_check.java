package cs1501.P1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
public class pw_check
{
	static class DLB
	{
		Node n;
		private class Node
		{
			Node horizontalNode, verticalNode;
			char letter;
			float time;
			Node(char c, Node h, Node v, float t)
			{
				letter = c;
				horizontalNode = h;
				verticalNode = v;
				time = t;
			}
		}
		private DLB()
		{
			@SuppressWarnings("unused")
			Node n = null;
		}
		
		public boolean contains(String word)
		{
			int letterCount = word.length();
			int count=0;
			Node next = n;
			while(count < letterCount)
			{				
				if(next==null)
				{
					return false;
				}
				else if(word.charAt(count) == next.letter)
				{
					next = next.verticalNode;
					count++;
					if((count == letterCount) && (next.letter=='^'))
					{
						return true;
					}
				}
				else 
				{
					if(next.horizontalNode != null)
					{
						next = next.horizontalNode;
					}
					else
					{
						return false;
					}
				}
			}
			return false;
		}
		public boolean insert(String word)
		{
			if(contains(word))
			{
				return false;
			}
			else
			{
				int letterCount = word.length();
				int count=0;
				Node next = n;
				while(count < letterCount)
				{					
					if(next == null)
					{
						n = new Node(word.charAt(0), null, null, 0);
						next = n;
					}
					else if(next.letter == word.charAt(count))
					{
						//if the vertical node is null, a new one must be added
						if(next.verticalNode == null)
						{
							//if this is the last index, add a termination character
							if(count+1==letterCount)
							{
								next.verticalNode = new Node('^', null, null, 0);
								return true;
							}
							//if not, add a new letter of the word to the linked list
							else 
							{
								next.verticalNode = new Node(word.charAt(count+1), null, null, 0);
								next = next.verticalNode;
							}
						}
						else if(word.charAt(count)==',')
						{
							next.time = Float.parseFloat(word.substring(6));
							next.verticalNode = new Node('^', null, null, 0);
							return true;
						}
						//if the vertical node is not null, move onto the next letter
						else
						{
							next = next.verticalNode;
						}
						count++;
					}
					else if(next.horizontalNode == null)
					{
						next.horizontalNode = new Node(word.charAt(count), null, null, 0);
						next = next.horizontalNode;
					}
					else 
					{
						next = next.horizontalNode;
					}
					
				}
			}
			return false;
		}
	}
	
	static final char[] LETTERS = {'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l','m', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v','w', 'x', 'y', 'z'};
	static final char[] NUMBERS = {'0', '2', '3', '5', '6', '7', '8', '9'};
	static final char[] SYMBOLS = {'!', '@', '$', '^', '_', '*'};
	static PrintWriter fileOut;
	static int count=0;
	
	static void addACharacter(char[] arry, DLB dict, StringBuilder pass)
	{
		for(char c: arry)
		{
			pass.append(c);
			String s = pass.toString();
			if(dict.contains(s))
			{
				pass.deleteCharAt(pass.length()-1);
			}
			else
			{
				passwordMaker(dict, pass, pass.length());
				pass.deleteCharAt(pass.length()-1);
			}
		}
	}
	
	
	static void passwordMaker(DLB dict, StringBuilder s, int p) 
	{
		if(p<5)
		{
			addACharacter(LETTERS, dict, s);
			addACharacter(NUMBERS, dict, s);
			addACharacter(SYMBOLS, dict, s);
		}
		String t = new String(s);
		int charCount = 0, numCount=0, symCount=0;
		for(int i=0; i<t.length(); i++)
		{
			if(t.charAt(i)=='b' || t.charAt(i)=='c'|| t.charAt(i)=='d'|| t.charAt(i)=='e'|| t.charAt(i)=='f'|| t.charAt(i)=='g'|| t.charAt(i)=='h'|| t.charAt(i)=='j'|| t.charAt(i)=='k'|| t.charAt(i)=='l'|| t.charAt(i)=='m'|| t.charAt(i)=='n'|| t.charAt(i)=='o'|| t.charAt(i)=='p'|| t.charAt(i)=='q'|| t.charAt(i)=='r'|| t.charAt(i)=='s'|| t.charAt(i)=='t'|| t.charAt(i)=='u'|| t.charAt(i)=='v'|| t.charAt(i)=='w'|| t.charAt(i)=='x'|| t.charAt(i)=='y'|| t.charAt(i)=='z')
			{
				charCount++;
			}
			else if(t.charAt(i)=='2'|| t.charAt(i)=='3'|| t.charAt(i)=='5'|| t.charAt(i)=='6'|| t.charAt(i)=='7'|| t.charAt(i)=='8'|| t.charAt(i)=='9')
			{
				numCount++;
			}
			else if(t.charAt(i)=='!' || t.charAt(i)=='@' || t.charAt(i)=='$' || t.charAt(i)=='^' || t.charAt(i)=='_' || t.charAt(i)=='*')
			{
				symCount++;
			}
		}
		if(charCount<=3 && numCount<=2 && symCount<=2 && t.length()==5)
		{
			
			fileOut.println(t + "," + (System.nanoTime()/100000));
			count++;
		}
	}
	
	
	public static void main(String[] args) throws FileNotFoundException
	{
		if(args[0].equals("-find"))
		{
			DLB dela = new DLB();
			File dict = new File("dictionary.txt");
			File passes = new File("all_passwords.txt");
			fileOut = new PrintWriter("all_passwords.txt");
			Scanner inScan = new Scanner(dict);
			while(inScan.hasNext())
			{
				dela.insert(inScan.nextLine());
			}
			inScan.close();
			StringBuilder sb = new StringBuilder();
			passwordMaker(dela, sb, 0);
			System.out.println(count);
			fileOut.close();
		}
		else if(args[0].equals("-check"))
		{
			ArrayList<String> input = new ArrayList<String>();
			Scanner scan = new Scanner(System.in);
			DLB passwords = new DLB();
			File allPass = new File("all_passwords.txt");
			if(allPass.exists())
			{
				Scanner passScan= new Scanner(allPass);
				System.out.println("Loading passwords");
				while(passScan.hasNextLine())
				{
					passwords.insert(passScan.nextLine());
				}
				System.out.println("Please enter in passwords");
				System.out.println("Enter q to quit");
				String str = scan.nextLine();
				while(!str.equalsIgnoreCase("q"))
				{
					input.add(str);
					str = scan.nextLine();
				}
				System.out.println("Here are the passwords you entered and the time it took to make them!");
				for(int i=0; i<input.size(); i++)
				{
					String s = new String(input.get(i));
					if(passwords.contains(s))
					{
						System.out.println(s + " took " + passwords.n.time + "ms to generate");
					}
					else
					{
						System.out.println("Here are 10 passwords close to the ones you entered");
					}
				}
			
			}
		else
		{
				System.out.println("Please run -find before you run -check");
		}
	}
	}
}
