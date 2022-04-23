package edu.pace.cs242.yehualashet;

import edu.pace.cs242.ListNode;
import edu.pace.cs242.Metrics;

import java.io.PrintWriter;

import static edu.pace.cs242.DatasetS.datasetS;

/**
 * CS 242 Assignment OrderedListInsert.
 */
public class OrderedListInsert
{
  static String metInsert = "inserts";
  static String metCompare = "compares";
  static String metDup = "duplicates";
  ListNode<String> head;
  Metrics metrics;
/**
 * Create an object to contain a singly-linked ordered list of Strings.  Add a 0-length string as the first string
 * in the list to act as a sentinel, since it will always sort less than any other string.  This avoids the special
 * cases for an empty list, and for an insertion before the current head of the list.
 * @param mtr metrics to track the behavior of the algorithm
 */
OrderedListInsert(Metrics mtr) {
  metrics = mtr;
  metrics.addCounter(metInsert);
  metrics.addCounter(metCompare);
  metrics.addCounter(metDup);
  head = new ListNode<>("", "");
}
/**
 * Insert the passed String value into the list referenced by this.head in the order determined by
 * String.compareTo().  The list always has a sentinel entry with an empty string key as the first element in the list.
 * @param key the key String of the data to be inserted in the list
 * @param val the String data to be inserted in the list
 */
void insert(String key, String val) {

  ListNode<String> temp = head.getNext();
  ListNode<String> back = head;
  ListNode<String> newNode = new ListNode<String>(key, val);

  while(temp != null && temp.getData().compareTo(newNode.getData()) < 0) {
    if(temp.getKey().compareTo(newNode.getKey()) == 0) {
      metrics.count(metDup);
    }
    back = temp;
    temp = temp.getNext();
    metrics.count(metCompare);
  }

  newNode.setNext(temp);
  back.setNext(newNode);
  metrics.count(metInsert);
}
/**
 * Verify the order of the list.  Skip the first element as it is the sentinel.
 * @param lst a reference to the first element to be displayed in the list
 * @param pw a PrintWriter to display diagnostics
 * @return true if the list is in order, false otherwise
 */
boolean verifyOrder(ListNode<String> lst, PrintWriter pw) {
  ListNode<String> seq, prev;
  int ct = 0;
  if (null == (prev = lst))
  {
    pw.printf("verify empty list\n");
    return false;
  }
  seq = prev.getNext();
  while (null != seq)
  {
    ++ct;
    if (prev.getData().compareTo(seq.getData()) > 0)
    {
      pw.printf("order fail between node %d and %d: %s and %s\n", ct, ct + 1, prev.getData(), seq.getData());
      return false;
    }
    prev = seq;
    seq = seq.getNext();
  }
  pw.printf("verify made %d compares\n", ct);
  return true;
}
/**
 * Display a list in a table of multiple elements per line.
 * @param lst a reference to the first element to be displayed in the list
 * @param pw a PrintWriter used to display the list
 * @param opts optional display parameters
 *             [0] the number of elements to display (default is all elements)
 *             [1] the number of elements per line in the display (default is 1)
 */
static void dispArray(ListNode<String> lst, PrintWriter pw, int... opts) {
  int ct, last, lj, ll = 1;
  ListNode<String> seq;
  last = Integer.MAX_VALUE;
  if (opts.length >= 1 && opts[0] >= 1)
    last = opts[0];
  if (opts.length >= 2 && opts[1] >= 1)
    ll = opts[1];
  ct = 0;
  seq = lst;
  while (null != seq && ct < last)
  {
    pw.printf("%s", seq.getData());
    ct += 1;
    lj = Math.min(last, ct + ll - 1);
    seq = seq.getNext();
    while (null != seq && ct < lj)
    {
      pw.printf(", %s", seq.getData());
      ct += 1;
      seq = seq.getNext();
    }
    pw.println();
  }
}
/**
 * List order stability check.
 * @param lst a reference to the first element in the list
 * @param pw a PrintWriter to display error messages
 * @return true if the list order is stable, false otherwise
 */
boolean stableCheck(ListNode<String> lst, PrintWriter pw) {
  ListNode<String> seq1, seq2, prev;
  String key1, key2;
  int ct = 0;
  if (null == (prev = lst))
  {
    pw.printf("stableCheck empty list\n");
    return false;
  }
  seq1 = prev.getNext();
  if (null == seq1)
  {
    pw.printf("list should be longer than one node\n");
    return false;
  }
  while (null != seq1)
  {
    key1 = prev.getKey();
    key2 = seq1.getKey();
    seq2 = seq1;
    while (key1.compareTo(key2) == 0 && null != seq1.getNext())
    {
      seq1 = seq1.getNext();
      key2 = seq1.getKey();
    }
    ++ct;
    key1 = prev.getData();
    key2 = seq2.getData();
    while (seq2 != seq1 && key1.compareTo(key2) < 0)
    {
      ++ct;
      prev = seq2;
      key1 = key2;
      seq2 = seq2.getNext();
      key2 = seq2.getData();
    }
    if (seq1 != seq2)
    {
      pw.printf("stability fail between node %d and %d: %s and %s\n", ct, ct + 1, prev.getData(), seq2.getData());
      return false;
    }
    prev = seq1;
    seq1 = seq1.getNext();
  }
  return true;
}
/**
 * Test the list insertion method insert() with the data from DatasetS.  Verify order and stability, and display
 * Metrics and the first few order records in the list.
 * @param args command line args unused
 */
public static void main(String[] args)
{
  OrderedListInsert loi;
  Metrics metrics = new Metrics();
  PrintWriter pw = new PrintWriter(System.out);
  String[] data;
  int ix, ndisp = 20, perline = 5;
  try
  {
    data = datasetS;
    loi = new OrderedListInsert(metrics);
    for (ix = 0; ix < data.length; ++ix)
    {
      loi.insert(data[ix].substring(0, 5), data[ix]);
    }
    if (loi.verifyOrder(loi.head.getNext(), pw))
      pw.println("ordered insertion succeeded");
    else
      pw.println("ordered insertion failed");
    if (loi.stableCheck(loi.head.getNext(), pw))
      pw.println("stability succeeded");
    else
      pw.println("stability failed");
    metrics.display(pw);
    ix = metrics.incr(metInsert, 0);
    pw.printf("average compares per insert %5.1f\n", metrics.incr(metCompare, 0) / (float)ix);
    pw.printf("data size %d strings\n", data.length);
    pw.printf("displaying first %d strings\n", ndisp);
    OrderedListInsert.dispArray(loi.head.getNext(), pw, ndisp, perline);
    pw.close();
  }
  catch (Exception ex)
  {
    System.err.printf("%s: %s\n", ex.getClass().toString(), ex.getMessage());
  }
}
}
