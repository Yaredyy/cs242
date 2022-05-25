package edu.pace.cs242.yehualashet;

import java.io.PrintWriter;

public class DFA
{
  static String[] data4 = {"23", "24.", "25.1", "26.23", "27.456", ".2", ".34", ".567", "100.001", "1.1.1", "1..0", "..1"};
  static boolean[] mtch4 = {true, true, true, true, true, true, true, true,
          true, false, false, false};
  FiniteStateMachine[] machines;
  String[][] data;
  boolean[][] match;
  DFA(PrintWriter pw) {
    machines = new FiniteStateMachine[] {new FSM0(pw)};
    data = new String[][] {data4};
    match = new boolean[][] {mtch4};
  }
  public static void main(String[] args)
  {
    int ix;
    PrintWriter pw = new PrintWriter(System.out);
    DFA dfa = new DFA(pw);
    FiniteStateMachine machine;
    String[] data;
    boolean[] match;
    int select = 0;
    machine = dfa.machines[select];
    data = dfa.data[select];
    match = dfa.match[select];
    pw.printf("executing FSM %d\n", select);
    for (ix = 0; ix < data.length; ++ix)
    {
      if (machine.exec(data[ix], pw))
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
    int[][] rules;  // semantic rules, for each delta
    int[] accept;  // accept state(s)
    String alpha;  // alphabet
    String[] cclass; // alphabet character classes

    PrintWriter pw;
    FiniteStateMachine() {

    }
    FiniteStateMachine(PrintWriter pw) {
      this();
      this.pw = pw;
    }
    int charIndex(char inp) {
      int ix;
      if (null == cclass)
      {
        return alpha.indexOf(inp);
      }
      for (ix = 0; ix < cclass.length; ++ix)
      {
        if (0 <= cclass[ix].indexOf(inp))
          return ix;
      }
      return -1;
    }
    boolean exec(String str, PrintWriter pw) {
      int state, ix, ln, sym;
      char input;
      if (null == delta || null == accept || null == alpha)
        throw new IllegalArgumentException(String.format("FSM %s configuration not set", getClass().getName()));
      ln = str.length();
      if (null != rules)
        rule(0, 0, ' ');
      state = start;
      for (ix = 0; ix < ln; ++ix)
      {
        input = str.charAt(ix);
        sym = charIndex(input);
        if (0 > sym)
          throw new IllegalArgumentException(String.format("input character %c not in alphabet", input));
        if (null != rules)
          rule(rules[state][sym], sym, input);
        state = delta[state][sym];
        if (state < 0 || state >= delta.length)
          return false;
      }
      for (ix = 0; ix < accept.length; ++ix)
      {
        if (state == accept[ix])
        {
          if (null != rules)
          {
            pw.printf("input string:\"%s\", double value:%.4f\n", str, getValue());
          }
          return true;
        }
      }
      return false;
    }
    void rule(int i, int cc, char c) {
    }
    double getValue() {
      return 0.0;
    }
  }
  static class FSM0 extends FiniteStateMachine {
    /* define needed semantic rule variables here */
<<<<<<< HEAD
    double s, i, decV, intV, r;
=======
    double v =0, f=0, r, s= 10;
>>>>>>> befe4ea016c0b95fd5ef205b8a3f16a2d23d6989

    FSM0(PrintWriter pw) {
      super(pw);
      start = 0;  /* the start state */
      alpha = "0123456789.";
      cclass = new String[]{"0123456789", "."}; /* two character classes, digits, and a decimal point */
      delta = new int[][] {
              /* state transitions, as needed */
              {1,2},{1,3},{4,8},{5,6},{7,8},{5,8},{8,8},{7,6},{8,8}
              /* rows are states 0-n, columns are character classes 0 and 1 */
      };
      rules = new int[][] {
              /* rules matching the state transitions, as needed */
              {0,-1},{0,-1},{1,-1},{1,-1},{1,-1},{1,-1},{1,-1},{1,-1},{1,-1}
      };
      accept = new int[] {1,3,4,5,6,7};  /* accept state(s) */
    }
    void rule(int rule, int cc, char chr) {
      //pw.printf("rule:%d char:'%c'\n", rule, chr);
      /* define any number of cases you need with any rule numbers. ignore -1. */
      /* rule 0 is special - the above print can be used to trace if necessary */
<<<<<<< HEAD
      switch(rule)
      {
        case 0:
        // initializing the variables
        s = 10;
        i = 0;
        decV = 0.0;
        break;
      case 1: // v = v * 10 + d; v is i
        i = i * 10  + (chr - '0');
        break;
      case 2: // f = f * 10 + d
        decV = decV + (chr - '0') / s;
        s *= 10;
        break;
      case 3:
        break;
      default:
        break;
=======
      double i = ((cc *(Math.pow(10,String.valueOf(chr).length())))+chr);
      switch (rule) {
        case 0 -> // initialize
                /* semantic code initialization */
                v = 10 * v + i;
        case 1 -> {
          /* rule 1 */
          f += i / s;
          s *= 10;
        }
        default -> s = 10;
>>>>>>> befe4ea016c0b95fd5ef205b8a3f16a2d23d6989
      }
    }
    double getValue() {
      /* return the final semantic result of the recognition */
<<<<<<< HEAD
      r=i+decV;
      i=0;
      decV=0;
=======
      r=v+f;
      v=0;
      f=0;
>>>>>>> befe4ea016c0b95fd5ef205b8a3f16a2d23d6989
      return r;
    }
  }
}