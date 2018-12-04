/**
 * The driver for the Heap class, will make a Heap object and call the go function
 * @author Kevin Filanowski
 * @author Jacob Ginn
 */
public class Driver {


    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Only one input is allowed.");
            System.exit(1);
        }else{    
            Heap heap = new Heap();
            heap.go(args[0]);
        }
    }
}
