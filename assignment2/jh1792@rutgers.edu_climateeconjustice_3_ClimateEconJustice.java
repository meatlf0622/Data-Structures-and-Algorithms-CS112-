package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        StateNode currentState = new StateNode();
        String[] arrOfData = inputLine.split(",");
        currentState.setName(arrOfData[2]);
        if(firstState == null){
            firstState = currentState;
            return;
        }
        currentState.next = null;   //next
        StateNode ptr = firstState;  //last
        if(firstState.getName().equals(currentState.getName())){
            return;
        }
        //goes through the nodes(states) and then adds the currentstate to the last node which would be ptr.next 
        while(ptr.next != null){
            if(ptr.next.getName().equals(currentState.getName())){
                 return;
            }else{
                ptr = ptr.next;
                
            }
        }
        ptr.next = currentState;
    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        // WRITE YOUR CODE HERE
        StateNode state = new StateNode();
        CountyNode county = new CountyNode();
        String[] arrOfData = inputLine.split(",");
        state.setName(arrOfData[2]);
        county.setName(arrOfData[1]);

        //PTRS TO traverse both state and country 
        StateNode Stateptr  = new StateNode();
        CountyNode ptrCount = new CountyNode(); 
        Stateptr = firstState;
        
        Stateptr.setDown(firstState.getDown()); // 
        ptrCount.setName(county.getName());//


        
        while(Stateptr != null){
           if(Stateptr.getDown() == null){
              Stateptr.setDown(county);
          }else{
                if(state.getName().equals(Stateptr.getName())){
                ptrCount = Stateptr.getDown();
                while(ptrCount.next != null){
                       ptrCount = ptrCount.next; 
                }
                if(ptrCount.getName().equals(county.getName())){
                return; 
                }else{
                    ptrCount.next = county;
                    }
                }
            }
            Stateptr =  Stateptr.next;
        }
    }
    

    /* 
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {
        
        // WRITE YOUR CODE HERE
        StateNode state  = new StateNode();
        CountyNode county = new CountyNode();
        CommunityNode community = new CommunityNode();

        String[] arrOfData = inputLine.split(",");
        state.setName(arrOfData[2]);
        county.setName(arrOfData[1]);
        community.setName(arrOfData[0]);

        double african = Double.parseDouble(arrOfData[3]);
        double nativeA = Double.parseDouble(arrOfData[4]);
        double asian = Double.parseDouble(arrOfData[5]);
        double white = Double.parseDouble(arrOfData[8]);
        double hispanic = Double.parseDouble(arrOfData[9]);
        double Pm = Double.parseDouble(arrOfData[49]);
        double floodChance = Double.parseDouble(arrOfData[37]);
        double poverty = Double.parseDouble(arrOfData[121]);
        Data commData = new Data(african, nativeA, asian, white, hispanic, arrOfData[19], Pm, floodChance, poverty);
        community.setInfo(commData);

        StateNode stateptr= firstState;
        while(stateptr != null){
            if(stateptr.getName().equals(state.getName())){
            CountyNode countyNode = stateptr.getDown();
            while(countyNode != null){
                if(countyNode.getName().equals(county.getName())){
                if(countyNode.getDown() == null){
                    countyNode.setDown(community);
                }else{
                    CommunityNode comPtr = countyNode.getDown();
                    while(comPtr.next != null){
                        comPtr = comPtr.next;
                    }if(comPtr.getName().equals(community.getName())){
                        return;
                    }else{
                      comPtr.next = community;  
                    }
                }
            }
                countyNode = countyNode.next;
            }
        }
        stateptr = stateptr.next;
        }

    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        // WRITE YOUR CODE HERE
        StateNode statePtr = new StateNode();
        statePtr = firstState; 
        int count = 0;
        while(statePtr != null){
            CountyNode countyPtr = statePtr.getDown();
            while(countyPtr != null){
                CommunityNode commPtr = countyPtr.getDown();
                while(commPtr != null){
                    if(commPtr.getInfo().getAdvantageStatus().equals("True")){
                        if(race.equals("African American")){
                            if(commPtr.getInfo().getPrcntAfricanAmerican()*100 >= userPrcntage){
                                count++;
                            }
                        }
                        if(race.equals("Native American")){
                            if(commPtr.getInfo().getPrcntNative()*100 >= userPrcntage){
                                count++;
                            }
                        }
                        if(race.equals("Asian American")){
                            if(commPtr.getInfo().getPrcntAsian()*100 >= userPrcntage){
                                count++;
                            }
                        }
                        if(race.equals("White American")){
                            if(commPtr.getInfo().getPrcntWhite()*100 >= userPrcntage){
                                count++;
                            }
                        }
                        if(race.equals("Hispanic American")){
                            if(commPtr.getInfo().getPrcntHispanic()*100 >= userPrcntage){
                                count++;
                            }
                        }
                    }

                    commPtr = commPtr.next;
                }
                countyPtr = countyPtr.next;
            }
            statePtr = statePtr.next;
        }


        return count; // replace this line
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        //WRITE YOUR CODE HERE
        StateNode statePtr = new StateNode();
        statePtr = firstState; 
        int count = 0;
        while(statePtr != null){
            CountyNode countyPtr = statePtr.getDown();
            while(countyPtr != null){
                CommunityNode commPtr = countyPtr.getDown();
                while(commPtr != null){
                    if(commPtr.getInfo().getAdvantageStatus().equals("False")){
                        if(race.equals("African American")){
                            if(commPtr.getInfo().getPrcntAfricanAmerican()*100 >= userPrcntage){
                                count++;
                            }
                        }
                        if(race.equals("Native American")){
                            if(commPtr.getInfo().getPrcntNative()*100 >= userPrcntage){
                                count++;
                            }
                        }
                        if(race.equals("Asian American")){
                            if(commPtr.getInfo().getPrcntAsian()*100 >= userPrcntage){
                                count++;
                            }
                        }
                        if(race.equals("White American")){
                            if(commPtr.getInfo().getPrcntWhite()*100 >= userPrcntage){
                                count++;
                            }
                        }
                        if(race.equals("Hispanic American")){
                            if(commPtr.getInfo().getPrcntHispanic()*100 >= userPrcntage){
                                count++;
                            }
                        }
                    }

                    commPtr = commPtr.next;
                }
                countyPtr = countyPtr.next;
            }
            statePtr = statePtr.next;
        }
        return count; // replace this line
    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        // WRITE YOUR METHOD HERE
        StateNode statePtr = new StateNode();
        statePtr = firstState; 
        ArrayList<StateNode> list = new ArrayList<>();

        while(statePtr != null){
            CountyNode countyPtr = statePtr.getDown();
            while(countyPtr != null){
                CommunityNode commPtr = countyPtr.getDown();
                while(commPtr != null){
                    if(commPtr.getInfo().PMlevel >= PMlevel){
                        if(!list.contains(statePtr)){
                            list.add(statePtr);   
                        }
                        
                    }

                    commPtr = commPtr.next;
                }
                countyPtr = countyPtr.next;
            }
            statePtr = statePtr.next;
        }
        return list; // replace this line
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

        // WRITE YOUR METHOD HERE
        StateNode statePtr = new StateNode();
        statePtr = firstState; 
        int count = 0;
        while(statePtr != null){
            CountyNode countyPtr = statePtr.getDown();
            while(countyPtr != null){
                CommunityNode commPtr = countyPtr.getDown();
                while(commPtr != null){
                  if(commPtr.getInfo().chanceOfFlood >= userPercntage){
                    count++;
                  }
                    commPtr = commPtr.next;
                }
                countyPtr = countyPtr.next;
            }
            statePtr = statePtr.next;
        }
        return count; // replace this line
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

        //WRITE YOUR METHOD HERE
        ArrayList<CommunityNode> list = new ArrayList<>(); // list has to be 10 
        StateNode statePtr = new StateNode();
        statePtr = firstState;
        //int indexOfchange = 0;
        
         while(statePtr != null){
            if(statePtr.getName().equals(stateName)){
                CountyNode countyPtr = statePtr.getDown();
                while(countyPtr != null ){
                    CommunityNode commPtr = countyPtr.getDown();
                    while(commPtr != null){
                        if(list.size() <10){
                            list.add(commPtr);
                       } else{
                        double lowVal = list.get(0).getInfo().getPercentPovertyLine();
                        int indexOfchange = 0;
                         for(int i = 0; i<list.size(); i++){
                            if(list.get(i).getInfo().getPercentPovertyLine() < lowVal){
                                lowVal = list.get(i).getInfo().getPercentPovertyLine();
                                indexOfchange = i;
                            } 
                            
                         }
                         if(lowVal < commPtr.getInfo().getPercentPovertyLine()){
                            list.set(indexOfchange, commPtr);
                         }
                        }
                        
                        commPtr = commPtr.next;
                    }
                    countyPtr = countyPtr.next;
                }
            }
            statePtr = statePtr.next;
       }
        

        return list; // replace this line
    }
}
    
