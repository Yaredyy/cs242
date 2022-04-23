package edu.pace.cs242.yehualashet;

import java.io.PrintStream;
import java.io.PrintWriter;

import edu.pace.cs242.BtreeNode;
import edu.pace.cs242.Metrics;

import static edu.pace.cs242.DatasetK.datasetK;

public class BTree
{
  static final int KEYS = 5;  // number of keys must be odd
  static String metDReads = "disk reads";
  static String metDWrites = "disk writes";
  static String metKeys = "key count";
  static String metFKeys = "keys found";
  static String metNKeys = "keys not found";
  BtreeNode root;
  Metrics metrics;
  BTree(Metrics mtr) {
    root = new BtreeNode(KEYS);
    if (null != mtr)
    {
      mtr.addCounter(metDReads, metDWrites);
      metrics = mtr;
    }
    else
      metrics = new Metrics();
  }
  String insertR(String key, BtreeNode nod) {
    int idx;
    BtreeNode chld;
    if (nod == root && nod.keyCount == KEYS) {
      nod.split(null);
    }
    idx = nod.keyCount;
    if (nod.isLeaf()) {
      while (idx >= 1 && key.compareTo(nod.keys[idx - 1]) < 0) {
        nod.keys[idx - 1 + 1] = nod.keys[idx - 1];
        idx -= 1;
      }
      nod.keys[idx - 1 + 1] = key;
      nod.keyCount += 1;
      metrics.count(metDWrites);
      return null;
    }
    while (idx >= 1 && key.compareTo(nod.keys[idx - 1]) < 0)
      idx -= 1;
    idx += 1;
    metrics.count(metDReads);
    chld = nod.links[idx - 1];
    if (chld.keyCount == KEYS) {
      chld.split(nod);
      if (key.compareTo(nod.keys[idx - 1]) > 0)
        chld = nod.links[idx - 1 + 1];
    }
    return insertR(key, chld);
  }
  void display(BtreeNode nod, int hgt, String ind, PrintStream ps) {
    int ix;
    ps.printf("%s%s\n", ind, nod.toString());
    ind += "  ";
    for (ix = 0; ix < nod.keyCount + 1; ++ix) {
      if (null != nod.links[ix])
        display(nod.links[ix], hgt + 1, ind + "  ", ps);
    }
  }
  boolean verify(BtreeNode nod, String ky, boolean less) {
    int ix;
    boolean flag = true;
    for (ix = 0; ix < nod.keyCount; ++ix) {
      if (less) {
        if (ky.compareTo(nod.keys[ix]) < 0)
          return false;
      } else {
        if (ky.compareTo(nod.keys[ix]) > 0)
          return false;
      }
      if (!nod.isLeaf() && null != nod.links[ix])
        flag = flag && verify(nod.links[ix], nod.keys[ix], true);
    }
    if (!nod.isLeaf() && ix > 0 && null != nod.links[ix]) {
      flag = flag && verify(nod.links[ix], nod.keys[ix - 1], false);
    }
    return flag;
  }
String findR(BtreeNode x, String k) {
  int i;
  i = 1;
  while (i <= x.keyCount && k.compareTo(x.keys[i - 1]) > 0)
  {
    i = i + 1;
  }
  if (i <= x.keyCount && k.compareTo(x.keys[i - 1]) == 0)
    return x.keys[i - 1];
  if (x.isLeaf())
    return null;
  metrics.count(metDReads);
  return findR(x.links[i - 1], k);
}
int compare(BtreeNode nod, String key) {
  int ix;
  int cmpCod;
  ix = 0;
  while (ix < nod.keyCount)
  {
    cmpCod = key.compareTo(nod.keys[ix]);
    if (cmpCod <= 0)
      return ix;
    ++ix;
  }
  return nod.keyCount;
}
String find(BtreeNode nod, String key) {
  int idx=1, cc = -1;
  /* your code goes here */
  BtreeNode node = nod;

  while (nod != null) {
    idx = 1;
    while (idx <= nod.keyCount && key.compareTo(nod.keys[idx - 1]) > 0) {
      idx++;
    }
    if (idx <= nod.keyCount && key.compareTo(nod.keys[idx - 1]) == 0)
      return nod.keys[idx - 1];
    if (nod.isLeaf())
      return null;
    metrics.count(metDReads);
    nod = nod.links[idx - 1];
  }
  return null;

}

public static void main(String[] args)
{
  int ix;
  Metrics mtr = new Metrics(metKeys, metFKeys, metNKeys);
  BTree at = new BTree(mtr);
  PrintWriter pw = new PrintWriter(System.out);
  String str;
  String[] testData2 = {"K","L","M","C", "A", "E", "D", "B", "I", "J", "H", "G", "F"};
  String[] data;
  data = datasetK;
  mtr.incr(BTree.metKeys, data.length);
  for (ix = 0; ix < data.length; ++ix)
  {
    at.insertR(data[ix], at.root);
  }
  if (!at.verify(at.root, "", false))
  {
    pw.println("verify failed");
  }
//  at.display(at.root, 1, "", System.out);
  for (ix = 0; ix < data.length; ++ix)
  {
    str = at.find(at.root, data[ix]);
    if (null != str)
    {
      mtr.count(metFKeys);
    }
    else
    {
      mtr.count(metNKeys);
    }
  }
  mtr.display(pw);
  pw.flush();
}
}
