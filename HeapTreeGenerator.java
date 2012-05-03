

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class is used to generate heap tree based on the starting element and the depth of the tree, 
 * the number of tree level will be generated.
 * @author siddabasappatm
 *
 */

public class HeapTreeGenerator {

	static List<Node<Integer>> reversTraversalTreeElement = new ArrayList<Node<Integer>>();
	
	/**
	 * This method takes beginning element of a tree and generates left and right elements nodes of tree 
	 * based on the left and right calculation formula.  
	 * 
	 * @return root node element of a tree
	 */
	
    private static Node<Integer> to_s() 
    {

    	List<Node<Integer>> arrayListTreeElement = new ArrayList<Node<Integer>>();

    	try {

    		BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    		System.out.println("Please Enter the Number to insert at the begining to generate HEAP :");
    		int parentNumber = Integer.parseInt(bis.readLine());
    		System.out.println("Please Enter the number of tree levels between 1 to 5 :");
    		int depth = Integer.parseInt(bis.readLine());
    		arrayListTreeElement.add(new Node<Integer>(parentNumber));

    		depth = (int) Math.pow(2, depth);
    		for(int i=1; i< depth; i++)
    		{
    			arrayListTreeElement.add( new Node<Integer>((2*(arrayListTreeElement.get(i-1)).data)));
    			arrayListTreeElement.add( new Node<Integer>(((2*(arrayListTreeElement.get(i-1)).data))+1));
    		}

    		reversTraversalTreeElement.addAll(arrayListTreeElement);
    		Iterator<Node<Integer>> arrayIterator= arrayListTreeElement.iterator();
    		System.out.println("Tree elements in array -:");
    		while(arrayIterator.hasNext())
    		{
    			System.out.print(arrayIterator.next().data);
    			System.out.print("-");
    		}

    		Node new_node = null;  
    		for(int l=0; l<arrayListTreeElement.size(); l++) 
    		{
    			for(int k=l+1; k<arrayListTreeElement.size(); k++ )
    			{
    				if( (arrayListTreeElement.get(l).data == (int)arrayListTreeElement.get(k).data/2 ) ) 
    				{
    					if ( (int)arrayListTreeElement.get(k).data % 2 == 0 )
    					{
    						new_node = ( arrayListTreeElement.get(l));
    						new_node.left = (arrayListTreeElement.get(k));
    					}
    					else {

    						new_node.right = (arrayListTreeElement.get(k));
    					}
    				} 
    			}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println("");

    	return arrayListTreeElement.get(0);
    }

    public static void main(String[] args) {

    	//stores tree array elements and gives the root element of a tree
    	HeapTreeDisplay.createNode(to_s());
    	
    	List<Integer> arrayListTreeElement = new ArrayList<Integer>();
    	
    	for(int j=0; j<reversTraversalTreeElement.size(); j++) {
    		arrayListTreeElement.add(reversTraversalTreeElement.get(j).data);
    	}
    	
    	//by reversing the traversal to display the parent node of a tree
    	for(int i=arrayListTreeElement.size(); i>0;) {
    		int element = (arrayListTreeElement.get(i-1))/2;
    		if(element==arrayListTreeElement.get(0)) {
    			System.out.println("Parent element of Heap tree is -->  "+element);
    		}
    		i= arrayListTreeElement.indexOf((arrayListTreeElement.get(i-1))/2);
    	}
    }
}

/**
 *  Node class implement Comparable interface, holds the left and right element of a node  
 * @author siddabasappatm
 *
 * @param <T>
 */
class Node<T extends Comparable<?>> {
    Node<T> left, right;
    T data;

    public Node(T data) {
        this.data = data;
    }
}

/**
 * This class generates an heap tree for the given list of node elements  
 * @author siddabasappatm
 *
 */

class HeapTreeDisplay {
	
	/**
	 *  This method is used to calculate the max level of a tree and sends immutable list data.  
	 * @param <T>
	 * @param root
	 */
	
	public static <T extends Comparable<?>> void createNode(Node<T> root) {

		System.out.println("");
		int maxLevel = HeapTreeDisplay.maxLevel(root);

		createInternalNode(Collections.singletonList(root), 1, maxLevel);  
	}

	/**
	 *  This method is used to construct heap tree, with considering the left and right node, space 
	 *  between the nodes     
	 * @param <T>
	 * @param nodes
	 * @param level
	 * @param maxLevel
	 */
	private static <T extends Comparable<?>> void createInternalNode(List<Node<T>> nodes, int level, int maxLevel) {
		
		if (nodes.isEmpty() || HeapTreeDisplay.isAllElementsNull(nodes))
		{
			return;
		}	
		int treeStep = maxLevel - level;
		int endgeLines = (int) Math.pow(2, (Math.max(treeStep -1, 0)));
		int firstSpaces = (int) Math.pow(2, (treeStep)) - 1;
		int betweenSpaces = (int) Math.pow(2, (treeStep + 1)) ;

		HeapTreeDisplay.blankWhitespaces(firstSpaces+8);

		List<Node<T>> newNodes = new ArrayList<Node<T>>();
		for (Node<T> node : nodes) {
			if (node != null) {
				System.out.print(node.data);
				newNodes.add(node.left);
				newNodes.add(node.right);
			} else {
				newNodes.add(null);
				newNodes.add(null);
				System.out.print(" ");
			}

			HeapTreeDisplay.blankWhitespaces(betweenSpaces+2);
		}
		System.out.println("");

		for (int i = 1; i <= endgeLines; i++) {
			for (int j = 0; j < nodes.size(); j++) {
				HeapTreeDisplay.blankWhitespaces(firstSpaces - i+9);
				if (nodes.get(j) == null) {
					HeapTreeDisplay.blankWhitespaces(endgeLines + endgeLines + i );
					continue;
				}

				if (nodes.get(j).left != null)
					System.out.print("/");
				else
					HeapTreeDisplay.blankWhitespaces(1);

				HeapTreeDisplay.blankWhitespaces(i + i - 1);

				if (nodes.get(j).right != null)
					System.out.print("\\");
				else
					HeapTreeDisplay.blankWhitespaces(1);

				HeapTreeDisplay.blankWhitespaces(endgeLines + endgeLines - i);
			}

			System.out.println("");
		}

		createInternalNode(newNodes, level + 1, maxLevel);
	}

	private static void blankWhitespaces(int count) {
		for (int i = 0; i < count; i++)
			System.out.print(" ");
	}

	private static <T extends Comparable<?>> int maxLevel(Node<T> node) {
		if (node == null) 
		{	
			return 0;
		}	
		return Math.max(HeapTreeDisplay.maxLevel(node.left)+1, HeapTreeDisplay.maxLevel(node.right)+1) ; 
	}

/**
 * This method is used to check for the null list value.   
 * @param <T>
 * @param list
 * @return returns true if the list contains null, else will return false  
 */	
	private static <T> boolean isAllElementsNull(List<T> list) {
		for (Object object : list) {
			if (object != null)
			{	
				return false;
			}	
		}
		return true;
	}

}