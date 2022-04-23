package edu.pace.cs242.yehualashet;


import edu.pace.cs242.AlgoUtil;
import edu.pace.cs242.Metrics;

import java.io.PrintWriter;

import static edu.pace.cs242.DatasetOne.dataOne;

/**
 * An implementation of the QuickSort algorithm.
 */
public class QuickSort
{
  static String metCompare = "compares";
  static String metSwapCounter = "swaps";
  static String metSortSize = "data size";
  Metrics ctrs;
  Partition part;
/**
 * The constructor establishes the Metrics and instantiates a Partition class instance tgo be used by the algorithm.
 * @param mtr the Metrics object
 */
QuickSort(Metrics mtr) {
  ctrs = mtr;
  ctrs.addCounter(metCompare);
  ctrs.addCounter(metSwapCounter);
  part = new Partition(mtr);
}
/**
 * In the data array, swap the two elements at the two passed subscripts.
 * @param data an int array
 * @param s1 element one subscript
 * @param s2 element two subscript
 */
void swap(int[] data, int s1, int s2) {
  int tmp;
  tmp = data[s1];
  data[s1] = data[s2];
  data[s2] = tmp;
  ctrs.count(metSwapCounter);
}
/**
 * Sort the data in the passed subarray using the QuickSort algorithm.
 * @param data the data subarray to be sorted
 * @param lo the low subscript of the subarray
 * @param hi the high subscript of the subarray
 */
void sort(int[] data, int lo, int hi) {
  int dl = hi - lo + 1;
  int pv;
  if (dl < 2)
    return;
  if (dl == 2)
  {
    ctrs.count(metCompare);
    if (data[lo] > data[hi])
      swap(data, lo, hi);
  }
  else
  {
    /* your code goes here */
      pv = part.partition(data,lo,hi);
      sort(data, lo, pv - 1);
      sort(data, pv + 1, hi);
  }
}
/**
 * Test the QuickSort implementation
 * @param args unused
 */
public static void main(String[] args)
{
  QuickSort qs;
  Metrics metrics = new Metrics(metSortSize);
  PrintWriter pw = new PrintWriter(System.out);
  int[] testData1 = {6, 2, 8, 0, 5, 1, 7, 3, 9, 4};
  int[] testData2 = {3, 2, 1, 0};
  int[] testData3 = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
  int[] data;
//  data = testData3;
  data = dataOne;
  metrics.incr(metSortSize, data.length);
  qs = new QuickSort(metrics);
  qs.sort(data, 0, data.length - 1);
  if (AlgoUtil.verifyOrder(data))
    pw.println("QuickSort succeeded");
  else
    pw.println("QuickSort failed");
  metrics.display(pw);
  pw.printf("n lg n = %5.1f\n", data.length * AlgoUtil.log2(data.length));
  pw.close();
}
}
