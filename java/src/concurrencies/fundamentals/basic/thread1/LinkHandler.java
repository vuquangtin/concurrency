package basic.thread1;

/**
*
* @author Madalin Ilie
*/
public interface LinkHandler {

   /**
    * Returns the number of visited links
    * @return 
    */
	int size();

   /**
    * Checks if the link was already visited
    * @param link
    * @return 
    */
   boolean visited(String link);

   /**
    * Marks this link as visited
    * @param link 
    */
   void addVisited(String link);
}