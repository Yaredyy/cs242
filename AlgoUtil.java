package edu.pace.cs242;

import java.io.PrintWriter;

/**
 * A collection of utility functions used for CS242.
 */
public class AlgoUtil
{
/**
 * Compute n factorial.
 * @param n the input n
 * @return n factorial (n * (n-1) * (n-2) * ... * 2 * 1)
 */
public static long nfact(long n) {
  if (n <= 1)
    return 1;
  if (n == 2)
    return 2;
  return n * nfact(n - 1);
}
/**
 * Compute the log base 2 of an integer.
 * @param n the logarithm argument
 * @return the base 2 log of n
 */
public static double log2(int n)
{
  return Math.log(n) / Math.log(2);
}
/**
 * Compute the sum of the first n numbers according to the formula s = n(n+1)/2.
 * @param n the first n numbers to sum
 * @return the sum according to the formula
 */
public static long sumn(long n) {
  if (n <= 1)
    return 1;
  return (n * (n + 1)) / 2;
}
/**
 * Combine the two byte array parameters into a single array of the first followed by the second.
 * @param a the first of the two arrays to be combined
 * @param b the second of the two arrays to be combined
 * @return a byte array of size a.length + b.length containing a followed by b
 */
public static byte[][] combine(byte[][] a, byte[][] b) {
  byte[][] nwa = new byte[a.length + b.length][];
  System.arraycopy(a, 0, nwa, 0, a.length);
  System.arraycopy(b, 0, nwa, a.length, b.length);
  return nwa;
}
/**
 * Return an array of all permutations of the passed array
 * @param source an array of the elements to be permuted (of length &lt;= 12)
 * @return an array of arrays of all permutations of the input parameter
 * @throws IllegalArgumentException if source.length &gt; 12
 */
public static byte[][] permutations(byte[] source) {
  int jx, kx, ax;
  byte ch;
  byte[] prm, src;
  byte[][] pm1, sav;
  if (source.length == 1)
  {
    return new byte[][] {{source[0]}};
  }
  if (source.length == 2)
    return new byte[][] {{source[0], source[1]}, {source[1], source[0]}};
  if (source.length > 12)
    throw new IllegalArgumentException("excessive memory requirement");
  sav = new byte[(int)AlgoUtil.nfact(source.length)][];
  prm = new byte[source.length - 1];
  ax = -1;
  ch = source[0];
  System.arraycopy(source, 1, prm, 0, source.length - 1);
  pm1 = permutations(prm);
  for (jx = 0; jx < pm1.length; ++jx)
  {
    src = pm1[jx];
    for (kx = 0; kx < source.length; ++kx)
    {
      prm = sav[++ax] = new byte[source.length];
      System.arraycopy(src, 0, prm, 0, kx);
      System.arraycopy(src, kx, prm, kx + 1, src.length - kx);
      prm[kx] = ch;
    }
  }
  return sav;
}
/**
 * Return all combinations of members of the array source taken n at a time, as an array of combination arrays.
 * @param source source of the things selected for combinations
 * @param n number of things in each combination
 * @return an array of size m!/(n!(m-n)!) where m=source.length, of combination arrays of size n
 */
public static byte[][] combinations(byte[] source, int n) {
    byte[][] combos;
    byte[] combo;
    int max, ix, jx, px, ax, sz, ln1, bits;
    ax = -1;
    ln1 = source.length;
    sz = (int)(AlgoUtil.nfact(ln1) / (AlgoUtil.nfact(ln1 - n) * AlgoUtil.nfact(n)));
    combos = new byte[sz][];
    combo = null;
    max = (int)Math.pow(2, ln1);
  outer:
    for (ix = 1; ix < max; ++ix)
    {
      if (null == combo)
        combo = new byte[n];
      bits = -1;
      for (jx = ix, px = 0; jx != 0; jx >>= 1, ++px)
      {
        if ((jx & 0x01) != 0)
        {
          bits += 1;
          if (bits > n - 1)
            continue outer;
          combo[bits] = source[px];
        }
      }
      if (bits + 1 != n)
        continue;
      combos[++ax] = combo;
      combo = null;
    }
    return combos;
}
/**
 * Verify that the passed integer array is sorted in ascending order.
 * @param data the target integer array
 * @return true if data is sorted in ascending order, false otherwise
 */
public static boolean verifyOrder(int[] data) {
  int nm1, ix, jx;
  nm1 = data.length - 1;
  for (ix = 0, jx = 1; ix < nm1; ix = jx++)
  {
    if (data[ix] > data[jx])
      return false;
  }
  return true;
}
/**
 * Verify that the passed String array is sorted in ascending order, using case-sensitivity.
 * @param data the target String array
 * @return true if data is sorted in ascending order, false otherwise
 */
public static boolean verifyOrder(String[] data) {
  int nm1, ix, jx;
  nm1 = data.length - 1;
  for (ix = 0, jx = 1; ix < nm1; ix = jx++)
  {
    if (data[ix].compareTo(data[jx]) > 0)
      return false;
  }
  return true;
}
/**
* Display an array of int's in a table formatting with optional parameters.
* @param arr the int array to be displayed
* @param pw the output PrintWriter
* @param opts display options
 *            [0] the last element of the array to e display, defaults to the full array
 *            [1] the number of int's to be displayed per line, defaults to 15
 *            [2] the width of each integer display, defaults to 3 digits
 */
public static void dispArray(int[] arr, PrintWriter pw, int... opts) {
  int ix, jx, lj, ll = 15, wd = 3;
  int last = arr.length - 1;
  if (opts.length >= 1 && opts[0] >= 1)
    last = opts[0];
  if (opts.length >= 2 && opts[1] > 0)
    ll = opts[1];
  for (ix = 0; ix <= last; ix += ll)
  {
    pw.printf("%3d", wd, arr[ix]);
    lj = Math.min(last - ix, ll);
    for (jx = 1; jx < lj; ++jx)
    {
      pw.printf(",%3d", wd, arr[ix + jx]);
    }
    pw.println();
  }
}
public static void dispArray(String[] arr, PrintWriter pw, int... opts) {
  int ix, jx, lj, ll = 15, wd = 5;
  int last = arr.length - 1;
  if (opts.length >= 1 && opts[0] >= 1)
    last = opts[0];
  if (opts.length >= 2 && opts[1] > 0)
    ll = opts[1];
  for (ix = 0; ix <= last; ix += ll)
  {
    pw.printf("%s", wd, arr[ix]);
    lj = Math.min(last - ix, ll);
    for (jx = 1; jx < lj; ++jx)
    {
      pw.printf(",%s", wd, arr[ix + jx]);
    }
    pw.println();
  }
}
}
