
//Name: Dayoung Lee
//EID: DL29923


public class Program3 {

    EconomyCalculator calculator;
    VibraniumOreScenario vibraniumScenario;    

    public Program3() {
        this.calculator = null;
        this.vibraniumScenario = null;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 1.
     */
    public void initialize(EconomyCalculator ec) {
        this.calculator = ec;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 2.
     */
    public void initialize(VibraniumOreScenario vs) {
        this.vibraniumScenario = vs;
    }

    /*
     * This method returns an integer that is maximum possible gain in the Wakandan economy
     * given a certain amount of Vibranium
     */
    public int computeGain() {
        int N = calculator.getNumProjects();
        int V = calculator.getNumVibranium();
       
        int[][] gains = new int[N+1][V+1];

        // Initialize array of maximum gains
        for(int n=0; n<=N; n++) {
            for(int v=0; v<=V; v++) {
                gains[n][v] = 0;
            }
        }

        // DP solution
        for(int n=1; n<=N; n++) {
            for(int v=1; v<=V; v++) {
                int max = 0;
                for(int i=0; i<=v; i++) {
                    if(calculator.calculateGain(n-1, i) + gains[n-1][v-i] > max) {
                        max = calculator.calculateGain(n-1, i) + gains[n-1][v-i];
                    }
                }
                gains[n][v] = max;
            }
        }
        
        return gains[N][V];
    }

    /*
     * This method returns an integer that is the maximum possible dollar value that a thief 
     * could steal given the weight and volume capacity of his/her bag by using the 
     * VibraniumOreScenario instance.
     */

     public int computeLoss() {
        int N = vibraniumScenario.getNumOres();
        int W = vibraniumScenario.getWeightCapacity();
        int V = vibraniumScenario.getVolumeCapacity();
        int[][][] value = new int[N+1][W+1][V+1];

        // Initialize table of values
        for(int n=0; n<=N; n++) {
            for(int w=0; w<=W; w++) {
                for(int v=0; v<=V; v++) {
                    value[n][w][v] = 0;
                }   
            }
        }

        // DP solution
        for(int n=1; n<=N; n++) {
            for(int w=1; w<=W; w++) {
                for(int v=1; v<=V; v++) {

                    VibraniumOre ore = vibraniumScenario.getVibraniumOre(n-1);
                    int price = ore.getPrice();
                    int weight = ore.getWeight();
                    int volume = ore.getVolume();

                    if(w-weight >= 0 && v-volume >= 0) {
                        int max = value[n-1][w-weight][v-volume] + price;
                        if(value[n-1][w][v] > max) {
                            max = value[n-1][w][v];
                        }
                        value[n][w][v] = max;
                    }
                    else {
                        value[n][w][v] = value[n-1][w][v];
                    }
                    
                }   
            }
        }

        return value[N][W][V];
     }
}


