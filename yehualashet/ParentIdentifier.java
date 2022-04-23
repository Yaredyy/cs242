package edu.pace.cs242.yehualashet;

import edu.pace.cs242.StackException;

import java.io.PrintWriter;

/**
 * An illustration of the use of a stack data structure to perform infix to postfix expression conversion.
 */
public class ParentIdentifier
{
  /**
   * Expression test examples.
   */
  static String[] examples = {"a-b+c-d", "a+b*c", "(a+b)*c", "(a+(b*c))/d^e^f", "(a - b/c) * (a/6-e)"};
  /**
   * The stack.
   */
  char[] stack = new char[20];
  /**
   * the stack top index.
   */
  int stx = -1;
/**
 * Identifies the characters considered as operators in expressions.
 * @param ch the character in question
 * @return true if the character is an operator, false otherwise
 */
boolean isOperator(char ch) {
  switch (ch)
  {
  case '+':
  case '-':
  case '*':
  case '/':
  case '^':
    return true;
  default:
    break;
  }
  return false;
}
/**
 * Identifies the left parenthesis grouping character.
 * @param ch the character in question
 * @return true if the character is a left parenthesis, false otherwise
 */
boolean isLParen(char ch) {
  return ch == '(';
}
/**
 * Identifies the right parenthesis grouping character.
 * @param ch the character in question
 * @return true if the character is a right parenthesis, false otherwise
 */
boolean isRParen(char ch) {
  return ch == ')';
}
/**
 * Identifies the characters that are single- character operands: letters and digits.
 * @param ch the character in question
 * @return true if the character is an operand, false otherwise
 */
boolean isOperand(char ch) {
  return Character.isAlphabetic(ch) || Character.isDigit(ch);
}
/**
 * Push a character to the stack.
 * @param ch the character pushed to the stack top
 * @throws StackException if the stack size is exceeded
 */
void push(char ch)
    throws StackException
{
  if (stx == stack.length - 1)
    throw new StackException("stack overflow");
  stack[++stx] = ch;
}
/**
 * Pop the top character from the stack and return it.
 * @return the popped character
 * @throws StackException if the stack is empty
 */
char pop()
    throws StackException
{
  if (stx < 0)
    throw new StackException("stack underflow");
  return stack[stx--];
}
/**
 * Return the stack top without popping the stack.
 * @return the character at the top of the stack
 */
char top() {
  return stack[stx];
}
/**
 * Check if the stack is empty.
 * @return true if the stack is empty, false otherwise
 */
boolean isEmpty() {
  return stx < 0;
}
/**
 * Check if the passed operator is right associative.
 * @param op the operator character
 * @return true if the operator is right associative, false otherwise
 */
boolean isRAssoc(char op) {
  return op == '^';
}
/**
 * Return the priority (precedence) of the passed operator.  The higher the return value, the higher the precedence.
 * If passed a non-operator, the return is 0.
 * @param ch the operator
 * @return the priority as an integer
 */
int getPriority(char ch) {
  switch (ch)
  {
  case '^':
    return 3;
  case '*':
  case '/':
    return 2;
  case '+':
  case '-':
    return 1;
  default:
    break;
  }
  return 0;
}
/**
 * Convert the passed expression from infix to postfix.  The expression is assumed to be syntactically correct,
 * with single-character operands and operators defined by the methods of this class.
 * @param exp the string expression to be converted
 * @return the postfix conversion of the passed expression
 * @throws StackException if the stack overflows or underflows, most likely indicating invalid expression syntax
 */
String postfix(String exp)
    throws StackException
{
  int ix;
  char ch;
  StringBuilder sb = new StringBuilder();
  exp = '(' + exp + ')';  // avoids extra stack empty checks
  for (ix = 0; ix < exp.length(); ++ix)
  {
    ch = exp.charAt(ix);
    if (isOperand(ch)) // If next char is an operand, add it to the output string
      sb.append(ch);
    else if (isLParen(ch)) // If a left paren, push it to the stack
      push(ch);
    else if (isRParen(ch)) // if a left paren, pop from the stack to the output until the matching right paren
    {
      while (!isLParen(top()))
        sb.append(pop());
      pop(); // pop '('
    }
    else if (isOperator(ch))  // if an operator, pop higher priority operators to the output
    {
      if (isOperator(top()))
      {
        if (isRAssoc(ch))
          while (getPriority(ch) < getPriority(top()))
            sb.append(pop());
        else
          while (getPriority(ch) <= getPriority(top()))  // <= left associativity, < right associativity
            sb.append(pop());
      }
      push(ch); // push the input operator to the stack
    }
  }
  while (!isEmpty())  // pop remaining operators from the stack to the output
    sb.append(pop());
  return sb.toString();  // return postfix string
}
/**
 * Pass the class variable test expressions one at a time to the postfix conversion method, and display the
 * results on standard out.
 * @param args no command line arguments are used
 */
public static void main(String[] args)
{
  PrintWriter pw = new PrintWriter(System.out);
  int ix;
  String str;
  ParentIdentifier pf = new ParentIdentifier();
  pw.printf("execution from %s\n", pf.getClass().getPackage());
  for (ix = 0; ix < examples.length; ++ix)
  {
    try
    {
      str = pf.postfix(examples[ix]);
    }
    catch (StackException stx)
    {
      pw.println(stx.getMessage());
      break;
    }
    pw.printf("%s as postfix is %s\n", examples[ix], str);
  }
  pw.close();
}
}
