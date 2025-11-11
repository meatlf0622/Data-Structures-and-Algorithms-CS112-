package forensic;

import javax.xml.transform.stax.StAXResult;

/**
 * This class represents a forensic analysis system that manages DNA data using
 * BSTs.
 * Contains methods to create, read, update, delete, and flag profiles.
 * 
 * @author Kal Pandit
 */
public class ForensicAnalysis {

    private TreeNode treeRoot;            // BST's root
    private String firstUnknownSequence;
    private String secondUnknownSequence;

    public ForensicAnalysis () {
        treeRoot = null;
        firstUnknownSequence = null;
        secondUnknownSequence = null;
    }

    /**
     * Builds a simplified forensic analysis database as a BST and populates unknown sequences.
     * The input file is formatted as follows:
     * 1. one line containing the number of people in the database, say p
     * 2. one line containing first unknown sequence
     * 3. one line containing second unknown sequence
     * 2. for each person (p), this method:
     * - reads the person's name
     * - calls buildSingleProfile to return a single profile.
     * - calls insertPerson on the profile built to insert into BST.
     *      Use the BST insertion algorithm from class to insert.
     * 
     * DO NOT EDIT this method, IMPLEMENT buildSingleProfile and insertPerson.
     * 
     * @param filename the name of the file to read from
     */
    public void buildTree(String filename) {
        // DO NOT EDIT THIS CODE
        StdIn.setFile(filename); // DO NOT remove this line

        // Reads unknown sequences
        String sequence1 = StdIn.readLine();
        firstUnknownSequence = sequence1;
        String sequence2 = StdIn.readLine();
        secondUnknownSequence = sequence2;
        
        int numberOfPeople = Integer.parseInt(StdIn.readLine()); 

        for (int i = 0; i < numberOfPeople; i++) {
            // Reads name, count of STRs
            String fname = StdIn.readString();
            String lname = StdIn.readString();
            String fullName = lname + ", " + fname;
            // Calls buildSingleProfile to create
            Profile profileToAdd = createSingleProfile();
            // Calls insertPerson on that profile: inserts a key-value pair (name, profile)
            insertPerson(fullName, profileToAdd);
        }
    }

    /** 
     * Reads ONE profile from input file and returns a new Profile.
     * Do not add a StdIn.setFile statement, that is done for you in buildTree.
    */
    public Profile createSingleProfile() {
        // WRITE YOUR CODE HERE
        int s = StdIn.readInt();
        String str;
        int numOfOccur;
        STR[] arr = new STR[s];
        
        for(int i =0; i< s; i++){
            str = StdIn.readString();
            numOfOccur = StdIn.readInt();
            STR newStr = new STR(str, numOfOccur);
            arr[i] = newStr;
        }
        Profile newProf = new Profile(arr);

        
        return newProf; // update this line
    }

    /**
     * Inserts a node with a new (key, value) pair into
     * the binary search tree rooted at treeRoot.
     * 
     * Names are the keys, Profiles are the values.
     * USE the compareTo method on keys.
     * 
     * @param newProfile the profile to be inserted
     */
    public void insertPerson(String name, Profile newProfile) {

        // WRITE YOUR CODE HERE
        TreeNode newNode = new TreeNode(name, newProfile, null, null);
        TreeNode ptr = treeRoot;
        TreeNode prev = null;
        int comp=0;
        
       while(ptr != null){
         comp = newNode.getName().compareTo(ptr.getName());
        prev = ptr;
        if(comp< 0){
            ptr = ptr.getLeft();
        } else{
            ptr = ptr.getRight();
        }
       }
       if(comp == 0){
        treeRoot = newNode;
       } else
       if(comp > 0){
        prev.setRight(newNode); 
       } else{
        prev.setLeft(newNode);
       }
        
    }

    /**
     * Finds the number of profiles in the BST whose interest status matches
     * isOfInterest.
     *
     * @param isOfInterest the search mode: whether we are searching for unmarked or
     *                     marked profiles. true if yes, false otherwise
     * @return the number of profiles according to the search mode marked
     */
    public int getMatchingProfileCount(boolean isOfInterest) {
        
        // WRITE YOUR CODE HERE
       return match(treeRoot, isOfInterest); // update this line
    }
    //helper for recursion 
    private int match(TreeNode nodePtr, boolean mark){
        int count =0;
        if(nodePtr == null){
            return 0; 
        }
        if(mark && nodePtr.getProfile().getMarkedStatus()){
            count++;
        } else if( mark == false && nodePtr.getProfile().getMarkedStatus() == false){
           count++; 
        }
        count = count + match(nodePtr.getLeft(), mark);
        count = count + match(nodePtr.getRight(), mark);
        return count;
    }

    /**
     * Helper method that counts the # of STR occurrences in a sequence.
     * Provided method - DO NOT UPDATE.
     * 
     * @param sequence the sequence to search
     * @param STR      the STR to count occurrences of
     * @return the number of times STR appears in sequence
     */
    private int numberOfOccurrences(String sequence, String STR) {
        
        // DO NOT EDIT THIS CODE
        
        int repeats = 0;
        // STRs can't be greater than a sequence
        if (STR.length() > sequence.length())
            return 0;
        
            // indexOf returns the first index of STR in sequence, -1 if not found
        int lastOccurrence = sequence.indexOf(STR);
        
        while (lastOccurrence != -1) {
            repeats++;
            // Move start index beyond the last found occurrence
            lastOccurrence = sequence.indexOf(STR, lastOccurrence + STR.length());
        }
        return repeats;
    }

    /**
     * Traverses the BST at treeRoot to mark profiles if:
     * - For each STR in profile STRs: at least half of STR occurrences match (round
     * UP)
     * - If occurrences THROUGHOUT DNA (first + second sequence combined) matches
     * occurrences, add a match
     */
    public void flagProfilesOfInterest() {

        // WRITE YOUR CODE HERE
        markRecur(treeRoot);
    }
    //helper for recursion 
    private void markRecur(TreeNode node){
        int match = 0;
        int count = 0;
        
        if(node != null){
            markRecur(node.getLeft());
            STR[] dna = node.getProfile().getStrs();
            for(int i =0; i < dna.length; i++){
                String compare = dna[i].getStrString();
                count = numberOfOccurrences(firstUnknownSequence, compare) ;
                count = count + numberOfOccurrences(secondUnknownSequence, compare);
            if(count == dna[i].getOccurrences()){
                match++; 
            }   
            }
            
            if(match >= Math.ceil(dna.length/2.0)){ // rounds up 
                node.getProfile().setInterestStatus(true);
            }
            markRecur(node.getRight());
        }
    }

    /**
     * Uses a level-order traversal to populate an array of unmarked Strings representing unmarked people's names.
     * - USE the getMatchingProfileCount method to get the resulting array length.
     * - USE the provided Queue class to investigate a node and enqueue its
     * neighbors.
     * 
     * @return the array of unmarked people
     */
    public String[] getUnmarkedPeople() {

        // WRITE YOUR CODE HERE
        int count = 0;
        int length = getMatchingProfileCount(false);
        String[] unmark = new String[length];

        Queue<TreeNode> qwe = new Queue<>();
        qwe.enqueue(treeRoot);
        
        while(count < length){
            TreeNode nodePtr = qwe.dequeue();
            if(nodePtr.getProfile().getMarkedStatus() == false){
                unmark[count++] = nodePtr.getName();
            }
            if(nodePtr.getLeft() != null){
                qwe.enqueue(nodePtr.getLeft());
            }
            if(nodePtr.getRight() != null){
                qwe.enqueue(nodePtr.getRight());
            }
        }
        return unmark; // update this line
    }

    /**
     * Removes a SINGLE node from the BST rooted at treeRoot, given a full name (Last, First)
     * This is similar to the BST delete we have seen in class.
     * 
     * If a profile containing fullName doesn't exist, do nothing.
     * You may assume that all names are distinct.
     * 
     * @param fullName the full name of the person to delete
     */
    public void removePerson(String fullName) {
        // WRITE YOUR CODE HERE
        treeRoot = removeRecurisvly(treeRoot, fullName);
    } //helper for recursion 
    private TreeNode removeRecurisvly(TreeNode node, String name){
        
        if(node == null){
            return null;
        }
        int comp = name.compareTo(node.getName());
        if(comp == 0){
            //case 1, no children 
            if((node.getLeft() == null) && (node.getRight() == null)){
                return null;
            }
            // case 2. if only 1 kid 
           else if(node.getLeft() == null){
                node = node.getRight();
            }
           else  if(node.getRight() == null){
                node = node.getLeft();
            } else{
                //case 3 two kids
                TreeNode nodePtr = inOrderSuccess(node.getRight());
                //node = nodePtr;

                //make new node root
                node.setProfile(nodePtr.getProfile());
                node.setName(nodePtr.getName());
                node.setRight(delMin(node.getRight()));
            } 
        }else if(comp > 0 ){
            node.setRight(removeRecurisvly(node.getRight(), name));
        }else{
            node.setLeft(removeRecurisvly(node.getLeft(), name));
        }
        return node; 
    }
    private TreeNode inOrderSuccess(TreeNode node){
       // node  = node.getRight();
        while(node.getLeft() != null){
            node = node.getLeft();
        }
        return node; 
    }
    private TreeNode delMin(TreeNode currNode){
        if (currNode.getLeft() == null){
            return currNode.getRight();
        }
        currNode.setLeft(delMin(currNode.getLeft()));
        return currNode;
    }

    /**
     * Clean up the tree by using previously written methods to remove unmarked
     * profiles.
     * Requires the use of getUnmarkedPeople and removePerson.
     */
    public void cleanupTree() {
        // WRITE YOUR CODE HERE
        String[] unmark = getUnmarkedPeople();
        int size = unmark.length;

        for(int i = 0; i < size; i++){
            removePerson(unmark[i]);
        }

    }

    /**
     * Gets the root of the binary search tree.
     *
     * @return The root of the binary search tree.
     */
    public TreeNode getTreeRoot() {
        return treeRoot;
    }

    /**
     * Sets the root of the binary search tree.
     *
     * @param newRoot The new root of the binary search tree.
     */
    public void setTreeRoot(TreeNode newRoot) {
        treeRoot = newRoot;
    }

    /**
     * Gets the first unknown sequence.
     * 
     * @return the first unknown sequence.
     */
    public String getFirstUnknownSequence() {
        return firstUnknownSequence;
    }

    /**
     * Sets the first unknown sequence.
     * 
     * @param newFirst the value to set.
     */
    public void setFirstUnknownSequence(String newFirst) {
        firstUnknownSequence = newFirst;
    }

    /**
     * Gets the second unknown sequence.
     * 
     * @return the second unknown sequence.
     */
    public String getSecondUnknownSequence() {
        return secondUnknownSequence;
    }

    /**
     * Sets the second unknown sequence.
     * 
     * @param newSecond the value to set.
     */
    public void setSecondUnknownSequence(String newSecond) {
        secondUnknownSequence = newSecond;
    }

}
