package edu.pace.cs242;
/**
 * A generic list node container with a String key and two links, to be used in all list situations.
 * Accessors to the two link  fields are doubly named as next/prev or right/left for convenience.
 * @param <T> the type to be contained by this object
 */
public class ListNode<T>
{
  private ListNode<T> next;
  private ListNode<T> prev;
  private String key;
  private T data;
/**
 * Construct a ListNode and set the key and data values.
 * @param ky the key value of the node
 * @param dt the data value of the node (may be null)
 */
public ListNode(String ky, T dt) {
  key = ky;
  data = dt;
}
/**
 * The next link accessor.
 * @return the next link
 */
public ListNode<T> getNext()
{
  return next;
}
/**
 * The next link mutator.
 * @param next the new link value
 */
public void setNext(final ListNode<T> next)
{
  this.next = next;
}
/**
 * The previous link accessor.
 * @return the previous link
 */
public ListNode<T> getPrev()
{
  return prev;
}
/**
 * The previous link mutator.
 * @param prev the new link value
 */
public void setPrev(final ListNode<T> prev)
{
  this.prev = prev;
}
/**
 * The right link accessor (a synonym for the next link).
 * @return the previous link
 */
public ListNode<T> getRight()
{
  return next;
}
/**
 * The right link mutator (a synonym for the next link).
 * @param next the new link value
 */
public void setRight(final ListNode<T> next)
{
  this.next = next;
}
/**
 * The left link accessor  (a synonym for the previous link).
 * @return the previous link
 */
public ListNode<T> getLeft()
{
  return prev;
}
/**
 * The left link mutator (a synonym for the previous link).
 * @param prev the new link value
 */
public void setLeft(final ListNode<T> prev)
{
  this.prev = prev;
}
/**
 * The data field link accessor.
 * @return the data field
 */
public T getData()
{
  return data;
}
/**
 * The data field link mutator.
 * @param data the new data value
 */
public void setData(final T data)
{
  this.data = data;
}
/**
 * The key accessor.
 * @return the key
 */
public String getKey()
{
  return key;
}
/**
 * The key mutator.
 * @param key the new key value
 */
public void setKey(final String key)
{
  this.key = key;
}
}
