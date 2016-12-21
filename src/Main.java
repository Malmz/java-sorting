import java.util.Date;

//Used for multiple return values
class BubbleReturn {
    int early;
    int[] intArray;
    String[] stringArray;
}

class Main {

    //Swap two numbers in an int[]
    private static void swap(int[] array, int index1, int index2) {
        int tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }

    //Swap two numbers in an String[]
    private static void swap(String[] array, int index1, int index2) {
        String tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }

    //Generate an int[] with random numbers
    private static int[] genRandomArray(int length, int range) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = (int)(Math.random() * range + 1);
        }
        return array;
    }

    //Print an int[] with nice formatting
    private static void printArray(int[] array) {
        int rowIndex = 1;
        for (int item: array) {
            System.out.print(item + "\t");

            //Print in rows of 5.
            if (rowIndex == 5) {
                System.out.println();
                rowIndex = 0;
            }
            rowIndex++;
        }
        System.out.println();
    }

    //Print an String[] with nice formatting
    private static void printArray(String[] array) {
        int rowIndex = 1;
        for (String item: array) {
            System.out.print(item + "\t");

            //Print in rows of 5.
            if (rowIndex == 5) {
                System.out.println();
                rowIndex = 0;
            }
            rowIndex++;
        }
        System.out.println();
    }

    //Java implementation of simple sort for int[]
    private static int[] simpleSort(int[] array) {
        int[] newArray = array.clone();
        for (int i = 0; i < newArray.length; i++) {
            int smallest = i;
            for (int j = i+1; j < newArray.length; j++) {
                if (newArray[j] < newArray[smallest]) {
                    smallest = i;
                }
            }
            swap(newArray, i, smallest);
        }
        return newArray;
    }

    //Java implementation of simple sort for String[]
    private static String[] simpleSort(String[] array) {
        String[] newArray = array.clone();
        for (int i = 0; i < newArray.length; i++) {
            int smallest = i;
            for (int j = i+1; j < newArray.length; j++) {
                if (newArray[j].compareTo(newArray[smallest]) < 0) {
                    smallest = i;
                }
            }
            swap(newArray, i, smallest);
        }
        return newArray;
    }

    //Java implementation of bubble sort for int[]
    private static BubbleReturn bubbleSort(int[] array) {
        BubbleReturn retVal = new BubbleReturn();
        retVal.intArray = array.clone();
        for (int i = 0; i < retVal.intArray.length; i++) {
            boolean hasSwapped = false;
            for (int j = retVal.intArray.length-1; j-1 >= i; j--) {
                if (retVal.intArray[j-1] > retVal.intArray[j]) {
                    swap(retVal.intArray, j-1, j);
                    hasSwapped = true;
                }
            }
            // If nothing has swapped, exit early
            if (!hasSwapped) {
                retVal.early = (i+1);
                return retVal;
            }
        }
        retVal.early = 0;
        return retVal;
    }

    //Java implementation of bubble sort for String[]
    private static BubbleReturn bubbleSort(String[] array) {
        BubbleReturn retVal = new BubbleReturn();
        retVal.stringArray = array.clone();
        for (int i = 0; i < retVal.stringArray.length; i++) {
            boolean hasSwapped = false;
            for (int j = retVal.stringArray.length-1; j-1 >= i; j--) {
                if (retVal.stringArray[j-1].compareTo(retVal.stringArray[j]) > 0) {
                    swap(retVal.stringArray, j-1, j);
                    hasSwapped = true;
                }
            }
            // If nothing has swapped, exit early
            if (!hasSwapped) {
                retVal.early = (i+1);
                return retVal;
            }
        }
        retVal.early = 0;
        return retVal;
    }

    //Java implementation of insertion sort for int[]
    private static int[] insertionSort(int[] array) {
        int[] sorted = new int[array.length];
        sorted[0] = array[0];
        for (int i = 1; i < array.length; i++) {
            int j = i;
            while (0 < j && array[i] < sorted[j-1]) {
                sorted[j] = sorted[j-1];
                j--;
            }
            sorted[j] = array[i];
        }
        return sorted;
    }

    //Java implementation of insertion sort for String[]
    private static String[] insertionSort(String[] array) {
        String[] sorted = new String[array.length];
        sorted[0] = array[0];
        for (int i = 1; i < array.length; i++) {
            int j = i;
            while (0 < j && array[i].compareTo(sorted[j-1]) < 0) {
                sorted[j] = sorted[j-1];
                j--;
            }
            sorted[j] = array[i];
        }
        return sorted;
    }

    public static void main(String[] args) {

        int length = Kbd.readInt("Array length? ");
        int range = Kbd.readInt("Number range? ");

        //Create an array with random values
        int[] array = genRandomArray(length, range);


        long sSimpleTime = new Date().getTime();
        int[] newSimpleArray = simpleSort(array);
        long eSimpleTime = new Date().getTime();
        //printArray(newSimpleArray);

        long sBubbleTime = new Date().getTime();
        BubbleReturn bubVal = bubbleSort(array);
        long eBubbleTime = new Date().getTime();
        //printArray(bubVal.newArray);

        long sInsertTime = new Date().getTime();
        int[] newInsertArray = insertionSort(array);
        long eInsertTime = new Date().getTime();
        //printArray(newInsertArray);

        //Print time taken
        System.out.println("Simple sort took " + (eSimpleTime-sSimpleTime) + " milliseconds");
        System.out.println("Insertion sort took " + (eInsertTime-sInsertTime) + " milliseconds");
        System.out.println("Bubble sort took " + (eBubbleTime-sBubbleTime) + " milliseconds");
        if (bubVal.early != 0) {
            System.out.println("Bubble sort exited early on iteration " + bubVal.early);
        }
    }
}
