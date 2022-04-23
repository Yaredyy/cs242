package edu.pace.cs242;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * A convenience class to store and increment named counters, with display capability.
 */
public class Metrics
{
  Map<String,MutableInteger> counters = new HashMap<>();
/**
 * Optionally pass a varying list of counter names to the constructor.
 * @param keys a list of 0 or more counter names
 */
public Metrics(String... keys) {
  int ix;
  for (ix = 0; ix < keys.length; ++ix)
  {
    addCounter(keys[ix]);
  }
}
/**
 * Create a new named counter.
 * @param key the name of the counter
 */
public void addCounter(String... key) {
  int ix;
  for (ix = 0; ix < key.length; ++ix)
  {
    counters.put(key[ix], new MutableInteger(0));
  }
}
/**
 * Increment a named counter by 1.
 * @param ctr the name of the counter to be incremented
 */
public void count(String ctr) {
  MutableInteger val;
  val = counters.get(ctr);
  if (null == val)
    return;
  val.incrValue(1);
}
/**
 * Increment the named counter by a passed value. To get the value of the counter, increment it by 0.
 * @param ctr the name of the counter to be incremented
 * @param inc the increment value to be used
 * @return the new int value of the counter
 */
public int incr(String ctr, int inc) {
  MutableInteger val;
  val = counters.get(ctr);
  if (null == val)
    return 0;
  val.incrValue(inc);
  return val.intValue();
}
/**
 * Reset all counters to 0.
 */
public void reset() {
  MutableInteger mi;
  for (String ky : counters.keySet())
  {
    mi = counters.get(ky);
    mi.setValue(0);
  }
}
/**
 * Display the counters, using the passed PrintWriter, as a sequence of names and values, one per line.
 * @param wtr the PrintWriter output target
 */
public void display(PrintWriter wtr) {
  wtr.println("--Begin Metrics--");
  for (String key : counters.keySet())
  {
    wtr.printf("  %s: %d\n", key, counters.get(key).intValue());
  }
  wtr.println("--End Metrics--");
}
/**
 * Iterate over the counters and pass each key and value to the passed binary consumer.  Can be used to
 * display the keys and values in a custom format.
 * @param fnc the binary function
 */
public void display(BiConsumer<String,MutableInteger> fnc) {
  for (String key : counters.keySet())
  {
    fnc.accept(key, counters.get(key));
  }
}
}
