package edu.pace.cs242.yehualashet;

import edu.pace.cs242.AlgoUtil;

import edu.pace.cs242.Metrics;

import java.io.PrintWriter;
import static edu.pace.cs242.DatasetOne.dataOne;

/**
 * An implementation of the BubbleSortTmplt algorithm.
 */
public class BubbleSort
{
  static String cmpCounter = "compares";
  static String swapCounter = "swaps";
  static String passCounter = "passes";
  Metrics ctrs;
/**
 * Construct the BubbleSortTmplt class using the passed Metrics object.
 * @param mtr the Metrics object
 */
BubbleSort(Metrics mtr) {
  ctrs = mtr;
  ctrs.addCounter(cmpCounter);
  ctrs.addCounter(swapCounter);
  ctrs.addCounter(passCounter);
}
/**
 * Construct the BubbleSortTmplt class and provide a default Metrics object.
 */
BubbleSort()
{
  this(new Metrics());
}
/**
 * In array, swap the two elements at the two passed subscripts.
 * @param data an int array
 * @param s1 element one subscript
 * @param s2 element two subscript
 */
void swap(int[] data, int s1, int s2) {
  int tmp;
  tmp = data[s1];
  data[s1] = data[s2];
  data[s2] = tmp;
  ctrs.count(swapCounter);
}
/**
 * Sort the passed int array with the BubbleSortTmplt algorithm.
 * @param data the array to be sorted in place
 */
public void sort(int[] data) {
/* your code goes here */
  boolean flag = false;
  int size = data.length-1;
  while(flag == false){
    flag = true;
    for(int n1=0;n1 < size; n1++){
      int n2 = n1+1;
      if(data[n1] > data[n2]){
        swap(data, n1, n2);
        flag = false;
      }
      ctrs.count(cmpCounter);
    }
    size--;
      ctrs.count(passCounter);
  }
}
/**
 * Sort the passed int array with the BubbleSort algorithm, using heuristics.
 * @param data the array to be sorted in place
 */
public void sorth(int[] data) {
 /* your code goes here */
  boolean flag = false;
  int sizeR=data.length-1;
  int sizeL=0;
  while(flag == false){
    //true is to the left, false is to the right
    boolean direction = true;
    flag = true;
    if(direction) {
      for (int n1 = sizeL; n1 < sizeR; n1++) {
        int n2 = n1 + 1;
        if (data[n1] > data[n2]) {
          swap(data, n1, n2);
          flag = false;
        }
        ctrs.count(cmpCounter);
      }
      direction = false;
      sizeR--;
    }
    if(!direction){
      for (int n1 = sizeR; n1 > sizeL; n1--) {
        int n2 = n1 - 1;
        if (data[n1] < data[n2]) {
          swap(data, n1, n2);
          flag = false;
        }
        ctrs.count(cmpCounter);
      }
      sizeL++;
      direction = true;
    }
    ctrs.count(passCounter);
  }
}
/**
 * Use a BubbleSort class instance to sort the array edu.pace.cs242.DatasetOne.dataOne.
 * @param args command line args (unused)
 */
public static void main(String[] args)
{
  BubbleSort bbl;
  PrintWriter pw = new PrintWriter(System.out);
  Metrics metrics = new Metrics();
  int[] testData = {6, 2, 8, 0, 5, 1, 7, 3, 9, 4};
  int[] data;
  int ix;
  bbl = new BubbleSort(metrics);
  for (ix = 0; ix < 2; ++ix)
  {
    data = new int[testData.length];
    System.arraycopy(testData, 0, data, 0, data.length);
    data = new int[dataOne.length];
    System.arraycopy(dataOne, 0, data, 0, data.length);
    metrics.reset();
    if (ix == 0)
    {
      pw.printf("array length is %d\n", data.length);
      pw.printf("estimated compares is n(n-1)/2 = %d\n", (data.length * (data.length - 1)) / 2);
      bbl.sort(data);
    }
    else
    {
      bbl.sorth(data);
    }
    if (AlgoUtil.verifyOrder(data))
    {
      pw.println("BubbleSort succeeded");
      metrics.display(pw);
    }
    else
      pw.println("BubbleSort failed");
  }
  pw.close();
}
}
