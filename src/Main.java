import main.Konsol;

import java.util.Date;

//Used for multiple return values
class BubbleReturn {
    int early;
    int[] newArray;
}

class Main {

    //Swap to numbers in an int array
    private static void swap(int[] array, int index1, int index2) {
        int tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }

    //Generate an int array with random numbers.
    private static int[] genRandomArray(int length, int range) {
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = (int)(Math.random() * range + 1);
        }
        return array;
    }

    //Print an int array with nice formatting.
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

    //Java implementation of simple sort.
    private static int[] simpleSort(int[] array) {
        int[] newArray = array.clone();
        for (int index = 0; index < newArray.length; index++) {
            int smallest = index;
            for (int innerIndex = index+1; innerIndex < newArray.length; innerIndex++) {
                if (newArray[innerIndex] < newArray[smallest]) {
                    smallest = innerIndex;
                }
            }
            swap(newArray, index, smallest);
        }
        return newArray;
    }

    //Java implementation of bubble sort
    private static BubbleReturn bubbleSort(int[] array) {
        int[] newArray = array.clone();
        BubbleReturn retVal = new BubbleReturn();
        retVal.newArray = newArray;
        for (int i = 0; i < newArray.length; i++) {
            boolean hasSwapped = false;
            for (int j = newArray.length-1; j-1 >= i; j--) {
                if (newArray[j-1] > newArray[j]) {
                    swap(newArray, j-1, j);
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

    public static void main(String[] args) {

        int length = Konsol.readInt("Array length? ");
        int range = Konsol.readInt("Number range? ");

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

        //Print time taken
        System.out.println("Simple sort took " + (eSimpleTime-sSimpleTime) + " milliseconds");
        System.out.println("Bubble sort took " + (eBubbleTime-sBubbleTime) + " milliseconds");
        if (bubVal.early != 0) {
            System.out.println("Bubble sort exited early on iteration " + bubVal.early);
        }
    }
}
