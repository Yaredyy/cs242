package edu.pace.cs242.yehualashet;

import edu.pace.cs242.Metrics;
import edu.pace.cs242.StackException;

import java.io.PrintWriter;

import static edu.pace.cs242.DatasetP.datasetP;

/**
 * A class to verify balanced parentheses strings.
 */
public class BalancedParentheses
{
  static String metPushes = "stack pushes";
  static String metPops = "stack pops";
  Metrics metrics;
  StackNode stackTop;
/**
 * Construct a class instance and initialize the Metrics counters.
 * @param mtr a Metrics object
 */
BalancedParentheses(Metrics mtr) {
  if (null == mtr)
  {
    metrics = new Metrics();
  }
  else
  {
    metrics = mtr;
    metrics.addCounter(metPushes);
    metrics.addCounter(metPops);
  }
}
/**
 * Push the passed data item onto the stack, making it the new top.
 * @param dat the data item to be pushed on the stack
 */
void push(char dat)
{
  /* your code goes here */
  if(stackTop == null){
    stackTop = new StackNode(dat,null);

  }
  else{
    stackTop = new StackNode(dat,stackTop);
  }
  metrics.count(metPushes);
}
/**
 * Pop the top data item off the stack and return it.
 * @return the top data item
 * @throws StackException if the stack is empty
 */
char pop() throws StackException
{
  /* your code goes here */
  if(stackTop==null){
    throw new StackException();
  }
  else {
    char holder = stackTop.data;
    stackTop = stackTop.link;
    metrics.count(metPops);
    return holder;
  }
}
/**
 * Return the top data item in the stack without popping.
 * @return the stack top
 * @throws StackException if the stack is empty
 */
char top() throws StackException
{
  /* your code goes here */
  if(stackTop==null){
    throw new StackException();
  }
  else{
    return stackTop.data;
  }
}
/**
 * Verifies the passed String is a sequence of balanced parentheses.
 * @param data the ijnput sequence of parentheses
 * @return true if the input is balanced, false otherwise
 * @throws StackException on a problem
 */
boolean balanced(String data) throws StackException
{
  int ix;
  char ch;
  /* your code goes here */
  for (ix = 0; ix < data.length(); ix++) {
    ch = data.charAt(ix);
    if (ch == '(')
      push(ch);
    else if (ch == ')') {
      ch = pop();
      if (ch != '(')
        throw new StackException("unpaired parenthesis");
    }
    else
      throw new StackException(String.format("invalid input " + ch));
  }
  return (stackTop == null);
}
public static void main(String[] args)
{
  BalancedParentheses blp;
  Metrics metrics = new Metrics();
  PrintWriter pw = new PrintWriter(System.out);
  int ix;
  String[] datas;
  String[] testData = {"(())()", "()()()", "(((())))(())()",
      "(()(())((()(())((()))))"};
  datas = datasetP;
  blp = new BalancedParentheses(metrics);
  for (ix = 0; ix < datas.length; ++ix)
  {
    metrics.reset();
    try
    {
      if (blp.balanced(datas[ix]))
        pw.printf("record %d: balance check succeeded\n", ix);
      else
        pw.printf("record %d: balance check failed\n", ix);
    }
    catch (StackException stx)
    {
      pw.printf("record %d: %s\n", ix, stx.getMessage());
    }
    metrics.display(pw);
  }
  pw.close();
}
/**
 * A StackNode for a linked-list stack implementation.
 */
class StackNode
{
  private final StackNode link;
  private final char data;
  private StackNode(char dat, StackNode lnk)
  {
    data = dat;
    link = lnk;
  }
}
}
