package edu.pace.cs242;

/**
 * A class to represent the nodes in a Huffman tree.  It must distinguish between code word (leaf) nodes,
 * and internal nodes with children.  The class is Comparable in order to provide a native order as needed by the
 * priority queue used in the Huffman code computation algorithm.
 */
public class HNode implements Comparable<HNode>
{
  private static int sequence = 0;
  String letter;
  String code;
  String bit;
  private int seq;
  double weight;
  HNode parent;
  HNode sibling;
  HNode left;
  HNode rght;
/**
 * Construct an instance from  letter and a frequency (or weight).
 * @param ltr a letter
 * @param wgt the weight (frequency)
 */
public HNode(String ltr, Double wgt)
{
  this.letter = ltr;
  this.weight = wgt;
  this.seq = ++sequence;
}
/**
 * Construct an instance from  letter and a numeric String frequency.
 * @param ltr a letter
 * @param wgt the weight (frequency) as a String
 */
public HNode(String ltr, String wgt)
{
  this.letter = ltr;
  this.seq = ++sequence;
  try
  {
    this.weight = Double.parseDouble(wgt);
  }
  catch (NumberFormatException e)
  {
    this.weight = 1.0f;
  }
}
/**
 * Constructor to construct an internal (pseudo) instance from a weight, a seq, and references to two children.
 * The missing letter makes it a pseudo-element.
 * @param wgt the frequency (weight) of the instance
 */
public HNode(double wgt)
{
  this.weight = wgt;
  this.seq = ++sequence;
}
/**
 * Implements the native order for this class, which should order instances first by weight, and for equal weights,
 * by earlier instances less than later instances (seq), so that a comparison will never return 0.
 * @param rgt the right operand of a node comparison request
 * @return -1 if this node is less than the passed node, +1 if this node is greater than the passed node, 0 otherwise
 */
@Override
public int
compareTo(HNode rgt)
{
  double rgtw;
  int rgts;
  if (weight < (rgtw = rgt.getWeight()))
    return -1;
  if (weight > rgtw)
    return 1;
  if (seq < (rgts = rgt.getseq()))
    return -1;
  if (seq > rgts)
    return 1;
  return 0;
}
private int getseq() {
  return seq;
}
public boolean isPseudo() {
  return null == letter;
}
/* public accessors and mutators follow. */
public String getLetter()
{
  return letter;
}
public double getWeight()
{
  return weight;
}
public HNode getParent()
{
  return parent;
}
public void setParent(HNode parent)
{
  this.parent = parent;
}
public HNode getLeft()
{
  return left;
}
public void setLeft(HNode left)
{
  this.left = left;
}
public HNode getRight()
{
  return rght;
}
public void setRight(HNode rght)
{
  this.rght = rght;
}
public String getCode()
{
  return code;
}
public void setCode(String code)
{
  this.code = code;
}
public String getBit()
{
  return bit;
}
public void setBit(String bit)
{
  this.bit = bit;
}
public HNode getSibling()
{
  return sibling;
}
public void setSibling(final HNode sibling)
{
  this.sibling = sibling;
}
}