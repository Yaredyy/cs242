package edu.pace.cs242;
/**
 * A convenience class used to pass an integer as a reference argument,
 * allowing the function to which it is passed to update its value.
*/
public class MutableInteger implements Comparable<MutableInteger>
{
/**
 * The value field.
*/
 int val;
/**
 * Construct a MutableInteger from the passed value.
 * @param v the initial integer value
*/
public MutableInteger(int v)
{
 val = v;
}
/**
 * Return the current integer value.
 * @return the current value
*/
public int
intValue()
{
 return val;
}
/**
 * Set a new integer value.
 * @param v the new integer value
*/
public void
setValue(int v)
{
 val = v;
}
/**
 * Add the passed value to this integer value.
 * @param v the increment value
 * @return the new integer value
 */
public int
incrValue(int v)
{
 return val += v;
}
/**
 * Return the integer value as a String.
 * @return a String representing the MutableInteger value
 */
public String toString() {
  return "" + val;
}
/**
 * Implement the Comparable interface for instances of this class. Compare this instance to
 * the passed instance.
 * @param mi the MutableInteger to be compared to this
 * @return -1 if this is less than o.val, +1 if this is greater than o.val, 0 otherwise
 */
@Override
public int compareTo(final MutableInteger mi)
{
  if (null == mi)
    throw new NullPointerException();
  return (val < mi.val) ? -1 : ((val > mi.val) ? 1 : 0);
}
}
