package spiderman;
import java.util.*; 

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * DimensionInputFile name is passed through the command line as args[0]
 * Read from the DimensionsInputFile with the format:
 * 1. The first line with three numbers:
 *      i.    a (int): number of dimensions in the graph
 *      ii.   b (int): the initial size of the cluster table prior to rehashing
 *      iii.  c (double): the capacity(threshold) used to rehash the cluster table 
 * 
 * Step 2:
 * ClusterOutputFile name is passed in through the command line as args[1]
 * Output to ClusterOutputFile with the format:
 * 1. n lines, listing all of the dimension numbers connected to 
 *    that dimension in order (space separated)
 *    n is the size of the cluster table.
 * 
 * @author Seth Kelley
 */

public class Clusters {
    
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Clusters <dimension INput file> <collider OUTput file>");
                return;
        }
        String inputFile = args[0];
        String outputFile = args[1]; 
        System.out.println(inputFile + ", " + outputFile);

        //StdIn.setFile(inputFile);
        // read the file of stdin.readInt 

        StdOut.setFile(outputFile);
        //StdOut.print(); // use std out to print them into file 
        // print to file 

        // WRITE YOUR CODE HERE
        StdIn.setFile(inputFile);
        StdOut.setFile(outputFile);
         
        // creates new arraylist 
        
        ArrayList<Integer>[] cluster  = createHash(inputFile); 

        //wraps around
        ArrayList<Integer> firstclump = cluster[0];
        ArrayList<Integer> secongClump = cluster[1]; 

        //first clump 
        int firstnum = cluster.length-1;
        int secNum = cluster.length-2; 
        ArrayList<Integer> lastClump = cluster[firstnum];
        ArrayList<Integer> secondLastClump = cluster[secNum];
        firstclump.add(lastClump.get(0));
        firstclump.add(secondLastClump.get(0));

        // Second clump 
        secongClump.add(firstclump.get(0));
        secongClump.add(lastClump.get(0));

        // rest of clumps
        for(int i = 2; i < cluster.length; i ++){
            ArrayList<Integer> currentList = cluster[i]; 
            ArrayList<Integer> firstList = cluster[i -1];
            ArrayList<Integer> secondList = cluster[i -2];
            int value1 = firstList.get(0);
            int value2 = secondList.get(0);
            currentList.add(value1);
            currentList.add(value2);
        }
        printList(cluster);

}
public static ArrayList<Integer>[]createHash(String inputFile){
        StdIn.setFile(inputFile);
        int numOfDimensions = StdIn.readInt();
        int sizeOfClusters  = StdIn.readInt(); 
        double threshold = StdIn.readDouble();  
        ArrayList<Integer>[] newCluster = new ArrayList[sizeOfClusters]; 
        
        int count =0; 
        while(StdIn.hasNextLine()){
        //rehash
            if(count == threshold){
                newCluster = rehash(newCluster, threshold, sizeOfClusters*2);
                threshold = threshold *2;
                sizeOfClusters = sizeOfClusters *2; 
            }
        // adds normally 
            int dimensionNumber  = StdIn.readInt(); 
            int cannonEvents = StdIn.readInt(); 
            int dimWeight = StdIn.readInt();
            int dimensionIndex = dimensionNumber % sizeOfClusters;
            ArrayList<Integer> newList = newCluster[dimensionIndex];
            if(newList == null){
                newList = new ArrayList<Integer>();
            }
            newList.add(0, dimensionNumber);
            newCluster[dimensionIndex] = newList;
            count ++ ;
        
        }
    return newCluster; 
}
public static ArrayList<Integer>[]rehash(ArrayList<Integer>[] cluster , double threshold, int sizeOfClusters){
        ArrayList<Integer>[] newClust = new ArrayList[sizeOfClusters];
        for(int i = 0; i < cluster.length; i++ ){
            ArrayList<Integer> curList = cluster[i]; 
            if(curList != null ){
                int c = 0; 
                while(c<curList.size()){
                    int indexOfDim = curList.get(c) % sizeOfClusters;  
                    ArrayList<Integer> hashList = newClust[indexOfDim]; 
                    if(hashList == null){
                        hashList = new ArrayList<Integer>();
                    }
                    hashList.add(0, curList.get(c));
                    newClust[indexOfDim] = hashList; 
                    c++; 
                }
            }
        }
     
    return newClust; 
}
public static void printList(ArrayList<Integer>[] cluster){
    //StdOut.setFile(output);
    for(int i = 0; i < cluster.length; i++){
        ArrayList<Integer> temp = cluster[i]; 
        int count = 0; 
        while(count < temp.size()){
            StdOut.print(temp.get(count) + " "); 
            count++; 
        }
        StdOut.println(); 
    }
}
}