package edu.pace.cs242;

public class BtreeNode {
  public int keyCount;
  public String[] keys;
  public BtreeNode[] links;
  boolean leafNode = true;
public BtreeNode(int keys) {
  keyCount = 0;
  this.keys = new String[keys];
  links = new BtreeNode[keys + 1];
}
private BtreeNode(BtreeNode nod, boolean lft, boolean lfn, int kc) {
  this(kc);
  int ix, lo, half;
  half = kc / 2;
  lo = (lft) ? 0 : half + 1;
  for (ix = 0; ix < half; ++ix, ++lo)
  {
    keys[ix] = nod.keys[lo];
    links[ix] = nod.links[lo];
  }
  links[ix] = nod.links[lo];
  keyCount = half;
  leafNode = lfn;
}
int compare(String key) {
  int ix;
  int cmpCod;
  for (ix = 0; ix < keyCount; ++ix)
  {
    cmpCod = key.compareTo(keys[ix]);
    if (cmpCod <= 0)
      return ix;
  }
  return keyCount;
}
public void insertKey(String key, int idx, BtreeNode nod) {
  int ix, jx, kx;
  jx = keyCount++;
  kx = keyCount;
  ix = jx - 1;
  for ( ; ix >= idx; --ix, --jx, --kx)
  {
    keys[jx] = keys[ix];
    links[kx] = links[jx];
  }
  keys[idx] = key;
  links[idx + 1] = nod;
}
public void split(BtreeNode par) {
  BtreeNode lft, rgt;
  int ix, half;
  half = keyCount / 2;
  if (null == par)
  {
    lft = new BtreeNode(this, true, leafNode, keyCount);
    rgt = new BtreeNode(this, false, leafNode, keyCount);
    keys[0] = keys[half];
    links[0] = lft;
    links[1] = rgt;
    leafNode = false;
    for (ix = 1; ix < keyCount; ++ix)
    {
      keys[ix] = null;
      links[ix + 1] = null;
    }
    keyCount = 1;
  }
  else
  {
    rgt = new BtreeNode(this, false, leafNode, keyCount);
    ix = par.compare(keys[half]);
    par.insertKey(keys[half], ix, rgt);
    for (ix = half; ix < keyCount; ++ix)
    {
      keys[ix] = null;
      links[ix + 1] = null;
    }
    keyCount = half;
  }
}

public boolean isLeaf()
{
  return leafNode;
}

public String toString() {
  StringBuilder sb = new StringBuilder();
  int ix, kc;
  sb.append(String.format("%s,keyCount=%d", (leafNode) ? "leaf" : "branch", keyCount));
  sb.append(",keys=[");
  for (ix = 0; ix < keyCount; ++ix)
  {
    sb.append(String.format("%s%s", (ix > 0) ? "," : "", keys[ix]));
  }
  sb.append("],links=[");
  kc = keyCount;
  if (!leafNode)
    kc += 1;
  for (ix = 0; ix < kc; ++ix)
  {
    sb.append(String.format("%s%s", (ix > 0) ? "," : "", (null != links[ix]) ? "nn" : "nl"));
  }
  sb.append("]");
  return sb.toString();
}
}
