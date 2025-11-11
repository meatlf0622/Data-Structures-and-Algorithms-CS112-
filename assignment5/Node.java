package spiderman; 

public class Node{
    private int dimension; 
    private int cannonEvents; 
    private int weight; 
    private Node next; 
   public  Node (int Dim, int canEvent, int weight, Node next ){
    this.dimension = Dim; 
    this.cannonEvents = canEvent; 
    this.weight = weight; 
    this.next = next; 
   } 
   //Setters
   public void setDim(int dim){
    this.dimension = dim; 
   }
   public void setCanEvent(int can){
    this.cannonEvents = can;
   }
   public void setWeight(int weight){
    this.weight = weight; 
   }
   public void setNext(Node next){
    this.next = next; 
   }
   //Getters 
   public Node getNext(){
    return next; 
   }
   public int getDimension(){
    return dimension; 
   }
   public int getCannonEvents(){
    return dimension; 
   }
   
   
}
