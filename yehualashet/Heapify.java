package edu.pace.cs242.yehualashet;

import edu.pace.cs242.Metrics;

import java.io.PrintWriter;

import static edu.pace.cs242.DatasetOne.dataOne;

public class Heapify
{
  static String metMoves = "moves";
  static String metCompares = "compares";
  int heapMax = 2;
  int heapLast = 0;
  int[] heap = new int[heapMax];
  Metrics ctrs;
  Heapify(Metrics mtr) {
  ctrs = mtr;
  ctrs.addCounter(metMoves);
  ctrs.addCounter(metCompares);
  heap[0] = 0;
}
  void reheapDn(int par) {
    int val, lastPar, child;
    /* your code goes here */
    ctrs.count(metMoves);
    val = heap[par];
    lastPar = heapLast / 2;
    while (par <= lastPar) {
      child = par * 2;
      if (child < heapLast) {
        ctrs.count(metCompares);
        if (heap[child] > heap[child + 1]) {
          child++;
        }
      }

      ctrs.count(metCompares);
      if (val <= heap[child]) {
        break;
      }

      ctrs.count(metMoves);
      heap[par] = heap[child];
      par = child;
    }
    ctrs.count(metMoves);
    heap[par] = val;
  }

  void heapify(int[] data) {
    int ix;
    heap = new int[data.length + 1];
    System.arraycopy(data, 0, heap, 1, data.length);
    /* your code goes here */
    heapLast = heap.length - 1;
    for(ix = heapLast / 2; ix > 0; ix--) {
      reheapDn(ix);
    }
  }

  static void dispArray(int[] arr, int last, PrintWriter pw) {
    int ix, jx, lj, ll = 15;
    for (ix = 1; ix < last; ix += ll)
    {
      pw.printf("%3d", arr[ix]);
      lj = Math.min(last - ix, ll);
      for (jx = 2; jx <= lj; ++jx)
      {
        pw.printf(",%3d", arr[jx]);
      }
      pw.println();
    }
  }
  boolean verify(int par) {
    int child = par * 2;
    int child2 = child++;
    boolean rt = false;
    if(child < heapLast) {
      if (heap[par] <= heap[child] && heap[par] <= heap[child2]) {
        rt = true;
      }
      par++;
      verify(par);
    }
    return rt;
  }
  public static void main(String[] args)
  {
    PrintWriter pw = new PrintWriter(System.out);
    Metrics metrics = new Metrics();
    Heapify hc = new Heapify(metrics);
//    int ix;
    int[] data;
//    data = new int[]{25, 29, 35, 26, 24, 18, 21, 23, 17, 11, 16, 7};
    data = dataOne;
//    dispArray(data, (data.length-1), pw);
    hc.heapify(data);
    if (hc.verify(0))
    {
      pw.printf("Heap condition verified after heapify\n");
    }
    else
    {
      pw.printf("Heap condition failed after heapify\n");
    }
    metrics.display(pw);
    pw.close();
  }
}
