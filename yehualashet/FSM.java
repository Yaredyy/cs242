package edu.pace.cs242.yehualashet;

import java.io.PrintWriter;

public class FSM
{
  static String[] data1 = {"abbbabbba", "aaaaab", "bbaabbaa", "ababababaa"};
  static boolean[] mtch1 = {true, false, false, false};
  static String[] data2 = {"abbbabbba", "aaaaab", "bbaabbaa", "ababababaa"};
  static boolean[] mtch2 = {true, true, false, false};
  static String[] data3 = {"a", "b", "bbabbaa", "abababbab", "bbab", "bbabb", "bb", "bba",
          "bbaa", "bbaabbaabbaabbab", "bbabbab", "bbababbab", "abbbbbab"};
  static boolean[] mtch3 = {false, false, false, true, true, false, false, false,
          false, true, true, true, true};
  FiniteStateMachine[] machines;
  String[][] data;
  boolean[][] match;
  FSM() {
  machines = new FiniteStateMachine[] {new FSM1(), new FSM2(), new FSM3()};
  data = new String[][] {data1, data2, data3};
  match = new boolean[][] {mtch1, mtch2, mtch3};
}
public static void main(String[] args)
{
  int ix;
  PrintWriter pw = new PrintWriter(System.out);
  FSM fsm = new FSM();
  FiniteStateMachine machine;
  String[] data;
  boolean[] match;
  int select = 2;
  machine = fsm.machines[select];
  data = fsm.data[select];
  match = fsm.match[select];
  pw.printf("executing FSM %d\n", select + 1);
  for (ix = 0; ix < data.length; ++ix)
  {
    if (machine.exec(data[ix]))
    {
      if (match[ix])
        pw.printf("string \"%s\" correctly accepted\n", data[ix]);
      else
        pw.printf("--> machine failed by accepting string \"%s\"\n", data[ix]);
    }
    else
    {
      if (!match[ix])
        pw.printf("string \"%s\" correctly rejected\n", data[ix]);
      else
        pw.printf("--> machine failed by rejecting string \"%s\"\n", data[ix]);
    }
  }
  pw.flush();
}
static class FiniteStateMachine {
  int start;  // start state
  int[][] delta;  // transition function
  int[] accept;  // accept state(s)
  String alpha;  // alphabet
  boolean exec(String str) {
    int state, ix, ln, sym;
    if (null == delta || null == accept || null == alpha)
      throw new IllegalArgumentException(String.format("FSM %s configuration not set", getClass().getName()));
    ln = str.length();
    state = start;
    for (ix = 0; ix < ln; ++ix)
    {
      sym = alpha.indexOf(str.charAt(ix));
      if (0 > sym)
        throw new IllegalArgumentException(String.format("input character %c not in alphabet", str.charAt(ix)));
      state = delta[state][sym];
    }
    for (ix = 0; ix < accept.length; ++ix)
    {
      if (state == accept[ix])
        return true;
    }
    return false;
  }
}
class FSM1 extends FiniteStateMachine {
  // accepts [ab]*bbab
  FSM1() {
    start = 0;  // start state
    delta = new int[][] {{1, 0}, {0, 0}};  // transition function
    accept = new int[] {1};  // accept state(s)
    alpha = "ab";  // alphabet
  }
}
class FSM2 extends FiniteStateMachine {
  // accepts [ab]*bbab
  FSM2() {
    start = 0;  // start state
    delta = new int[][] {{1, 0}, {0, 1}};  // transition function
    accept = new int[] {1};  // accept state(s)
    alpha = "ab";  // alphabet
  }
}
class FSM3 extends FiniteStateMachine {
  // accepts [ab]*bbab
  FSM3() {
    start = 0;
    delta = new int[][] {{0,1},{0,2},{3,2},{0,4},{0,2}};
    accept = new int[] {4};
    alpha = "ab";
  }
}
}
