package edu.pace.cs242.yehualashet;

public class Crypto
{
  static final String A = "Alice";
  static final String B = "Bob";
/**
 * Modular exponentiation. Implement the "Russian Peasant Method" of modular exponentiation.  Consider the binary
 * representation of the exponent.  In a loop until all the bits of the exponent have been used, continually square
 * the value and compute its residue. For each 1 bit in the exponent, multiply an accumulating result by the squared
 * residue and accumulate the residue of the result.  Test the code if necessary with values from the lecture notes
 * on exponentiation ciphers.
 * @param val value to be exponentiated
 * @param exp exponent
 * @param mod modulus
 * @return val raised to the exponent exp modulo mod
 */
int expoMod(int val, int exp, int mod) {
  int rexp, pexp, accum, ct;
  //accum = 1;
  /* your code goes here */
  if(val == 0)
    return 0;
  if(exp == 0)
    return 1;
  if(exp % 2 == 0) {
    accum = expoMod(val, exp/2, mod);
    accum = (accum * accum) % mod;
  }
  else {
    accum = val % mod;
    accum = (accum * expoMod(val, exp-1, mod) % mod) % mod;
  }
  accum = ((accum + mod) % mod);
  return accum;
}
/**
 * Determine if the passed n is a primitive root of the passed prime.  According to a theorem by Fermat, if p is
 * a prime, then any number raised to the power (p-1) mod p is congruent to 1.  If n is a primitive root of p,
 * (p-1) is the only positive power less than p for which this is true.
 * @param n the number to check
 * @param m the prime modulus in question
 * @return true if n is a primitive root of p, false otherwise
 */
boolean isPrimitiveRoot(int n, int m) {
  int jx, e = 0, mm1;
  boolean fail;
  /* your code goes here */
  mm1 = m - 1;
  for(jx = 1; jx < m; jx++) {
    if(expoMod(n, jx, m) == 1) {
      e++;
    }
  }
  if(e == 1) {
    fail = false;
    return !fail;
  }

  fail = true;
  return !fail;
}
void dhmKeyExch(int p, int g, int as, int bs) {
  int a = 0, b = 0, sa = -1, sb = 1;
  a = expoMod(g, as, p);
  b = expoMod(g, bs, p);
  sa = expoMod(b, as, p);
  sb = expoMod(a, bs, p);
  System.out.printf("prime modulus= %d, primitive root= %d\n", p, g);
  /* key exchange code Alice message to Bob, the variable a */
  System.out.printf("%s with secret %d sends %s %d^%d mod %d= %d\n", A, as, B, g, as, p, a);
  /* key exchange code Bob message to Alice, the variable b */
  System.out.printf("%s with secret %d sends %s %d^%d mod %d= %d\n", B, bs, A, g, bs, p, b);
  /* key exchange code Alice computes shared secret, the variable sa */
  System.out.printf("%s computes %d^%d mod %d= %d\n", A, b, as, p, sa);
  /* key exchange code Bob computes shared secret, the variable sb */
  System.out.printf("%s computes %d^%d mod %d= %d\n", B, a, bs, p, sb);
  if (sb != sa)
    System.out.println("the shared secrets are not equal");
}
public static void main(String[] args)
{
  Crypto cr = new Crypto();
  int r1 = 101, r2 = 103, r3 = 105, mod = 199, pr, rts;
  int bs = 157, as = 137;   // Bob's secret, Alice's secret
  boolean r;
  rts = pr = 0;
  r = cr.isPrimitiveRoot(r1, mod);
  if (r)
  {
    pr = r1;
    rts += 1;
  }
  System.out.printf("%d is%s a primitive root of %d\n", r1, (r) ? "" : " not", mod );
  r = cr.isPrimitiveRoot(r2, mod);
  if (r)
  {
    pr = r2;
    rts += 1;
  }
  System.out.printf("%d is%s a primitive root of %d\n", r2, (r) ? "" : " not", mod );
  r = cr.isPrimitiveRoot(r3, mod);
  if (r)
  {
    pr = r3;
    rts += 1;
  }
  System.out.printf("%d is%s a primitive root of %d\n", r3, (r) ? "" : " not", mod );
  if (0 == pr || rts != 1)
  {
    System.out.println("no single primitive root - check failed");
    return;
  }
  cr.dhmKeyExch(mod, pr, as, bs);
}
}
