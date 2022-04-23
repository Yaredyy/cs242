package edu.pace.cs242.yehualashet;

import edu.pace.cs242.Metrics;

import java.io.PrintWriter;

/**
 * An implementation of the partitioning algorithm as an independent class, to be used by other classes.
 */
public class Partition
{
  static String metCompare = "compares";
  static String metSwapCounter = "swaps";
  boolean partitionSelectMedian;
  Metrics ctrs;
/**
 * The constructor establishes the Metrics object.
 * @param mtr the Metrics object
 */
Partition(Metrics mtr)
{
  ctrs = mtr;
  ctrs.addCounter(metCompare);
  ctrs.addCounter(metSwapCounter);
}
/**
 * In array, swap the two elements at the two passed subscripts.
 * @param data an int array
 * @param s1 element one subscript
 * @param s2 element two subscript
 */
void swap(int[] data, int s1, int s2)
{
  int tmp;
  tmp = data[s1];
  data[s1] = data[s2];
  data[s2] = tmp;
  ctrs.count(metSwapCounter);
}
/**
 * Partition the passed subarray using the Partition algorithm.
 * @param data the array containing the subarray
 * @param lo the low subscript of the subarray
 * @param hi the high subscript of the subarray
 * @return the location of the pivot within the subarray; lo <= pivot <= hi
 */
int partition(int[] data, int lo, int hi)
{
  int lft, rgt, pivot, temp;
  boolean working;
  /* your code goes here */
  lft = lo;
  rgt = hi - 1;
  pivot = hi;
  working = true;
  while(working) {
    while(lft != rgt && data[lft] < data[pivot]) {
      lft++;
      ctrs.count(metCompare);
    }
    while(lft != rgt && data[rgt] > data[pivot]) {
      rgt--;
      ctrs.count(metCompare);
    }
    if(lft == rgt) {
      if(data[rgt] < data[pivot]) {
        temp = rgt + 1;
        swap(data, rgt+1, pivot);
        pivot = temp;
      }
      else if(data[rgt] > data[pivot]) {
        temp = rgt;
        swap(data, rgt, pivot);
        pivot = temp;
      }
      working = false;
    }
    else
      swap(data, lft, rgt);
  }
  return pivot; /* fix this */
}
public static void main(String[] args)
{
  Partition part;
  Metrics metrics = new Metrics();
  PrintWriter pw = new PrintWriter(System.out);
  int[] testData = {6, 2, 8, 0, 5, 1, 7, 3, 9, 4};
  int[] data;
  data = testData;
  part = new Partition(metrics);
  part.partitionSelectMedian = false;
  part.partition(data, 0, data.length - 1);
  metrics.display(pw);
  pw.close();
}
}
