package edu.pace.cs242.yehualashet;

import java.io.PrintWriter;
import java.util.ArrayList;
import edu.pace.cs242.HNode;

public class HuffmanWithPriorityHeap
{
  int heapMax = 2;
  int heapLast = 0;
  ArrayList<HNode> heap = new ArrayList<HNode>(heapMax);
  HNode btRoot = new HNode("", 0.0);   // sentinel
  HuffmanWithPriorityHeap() {
  int ix;
  for (ix = 0; ix < heapMax; ++ix)
    heap.add(ix, null);
}
void expandHeap() {
  int sz, ix;
  sz = heap.size();
  heap.ensureCapacity(2 * heap.size());
  for (ix = sz; ix < 2 * sz; ++ix)
    heap.add(ix, null);
  heapMax = heap.size();
}
void expandHeap(int sz) {
  int jx, ix;
  jx = heap.size();
  heap.ensureCapacity(sz);
  for (ix = jx; ix < sz; ++ix)
    heap.add(ix, null);
  heapMax = heap.size();
}
void reheapDn(int par) {
  HNode val;
  int lastPar, chld;
  val = heap.get(par);
  lastPar = heapLast / 2;
  while (par <= lastPar)
  {
    chld = par + par;
    if (chld < heapLast)  // there is a chld + 1 sibling
    {
      if (heap.get(chld).compareTo(heap.get(chld + 1)) > 0)
        chld += 1;
    }
    if (val.compareTo(heap.get(chld)) <= 0)
      break;
    heap.set(par, heap.get(chld));
    par = chld;
  }
  heap.set(par, val);
}
void reheapUp(int chld) {
  int par;
  HNode val;
  val = heap.get(chld);
  par = chld / 2;
  while (par > 0 && val.compareTo(heap.get(par)) <= 0)
  {
    heap.set(chld, heap.get(par));
    chld = par;
    par = chld / 2;
  }
  heap.set(chld, val);
}
void insert(HNode e) {
  if (++heapLast >= heapMax)
    expandHeap();
  heap.set(heapLast, e);
  reheapUp(heapLast);
}
/**
 * Remove the first priority item from the heap, and restablish the heap condition.
 * @return the first priority item
 */
HNode remove() {
  HNode lo;
  lo = heap.get(1);
  heap.set(1, heap.get(heapLast--));
  reheapDn(1);
  return lo;
}
/**
 * Binary tree insertion.
 * @param hn the node to be inserted
 * @param subtree the subtree examined for an insertion location
 * @return null if the node was inserted, otherwise the node reference if the node is in the tree
 */
HNode btInsert(HNode hn, HNode subtree) {
  int cc;
  cc = hn.getLetter().compareTo(subtree.getLetter());
  /* your code goes here - about 17 line or less */
  if (cc < 0) {
    if (subtree.getLeft() != null) {
      btInsert(hn, subtree.getLeft());
    }
    else {
      subtree.setLeft(hn);
      return null;
    }
  }
  if (cc > 0){
    if(subtree.getRight() != null) {
      btInsert(hn, subtree.getRight());
    }
    else {
      subtree.setRight(hn);
      return null;
    }
  }
  if(cc==0){
    return null;
  }
  return hn;
}
/**
 * Binary tree find.
 * @param key the search key
 * @param subtree the subtree being searched
 * @return the node containing the search key, or null if the key was not found
 */
HNode btFind(String key, HNode subtree) {
  int cc;
  HNode hn = null;
  cc = key.compareTo(subtree.getLetter());
  /* your code goes here - about 10 lines or less */
  if(key.equals(null)){
    return null;
  }
  if(subtree.getLetter().equals(key)){
    return subtree;
  }
  else {
    HNode left = null;
    HNode right = null;
    if (subtree.getRight() != null) {
      right = btFind(key, subtree.getRight());
    }
    if (subtree.getLeft() != null) {
      left = btFind(key, subtree.getLeft());
    }

    if (right != null) {
      hn = right;
    }
    if (left != null) {
      hn = left;
    }
    return hn;
  }
}
/**
 * For each letter/frequency pair, construct an HNode to represent it and insert that node into a heap, to be
 * used as a Huffman priority queue.  For future use, also insert the node into a binary tree using its letter as the
 * key, so it can later be found by that key. (about 9 lines of code)
 * @param freqPairs the array of letter/frequency pairs
 */
void buildPriorityQueueAndTree(String[][] freqPairs) {
  HNode hn;
  int ix;
  /* your code goes here - about 9 lines */

  for(ix = 0; ix < freqPairs.length; ix++) {
    hn = new HNode(freqPairs[ix][0], freqPairs[ix][1]);
    btInsert(hn, btRoot);
    insert(hn);
  }

}
/**
 * Build a Huffman code tree by removing a pair of elements from the priority queue, combining them into a new
 * element, and reinserting the new element into the priority queue.
 * @return the root node of the Huffman tree after it is completely built
 */
HNode buildHuffmanTree()
{
  HNode op1, op2, par;
  /* your code goes here - about 13 lines */
  op1 = remove();
  while (heapLast > 0)
  {
    op2 = remove();
    par = new HNode(op1.getWeight() + op2.getWeight());
    op1.setBit("0");
    op2.setBit("1");
    op1.setParent(par);
    op2.setParent(par);
    insert(par);
    op1 = remove();
  }
  return op1; /* fix this */
}
/**
 * Build the minimum redundancy code for each letter node in the binary tree by visiting the tree nodes
 * in pre-order and constructing the reversed bit string by traversing upward in the Huffman tree.  Note
 * that StringBuilder has a reverse() method. Set the code in the letter node.
 * @param leaf an HNode object that contains one of the letters for which a code is to be generated.
 * @param root the Huffman root node acts as a terminator; code generation ends at the root
 * @return
 */
void makeCodes(HNode leaf, HNode root) {
  StringBuilder sb;
  HNode nod;
  if (null == leaf)
    return;
  sb = new StringBuilder();
  /* your code goes here - about 9 lines */
  if(null == leaf)
    return;
  nod = leaf;
  while(nod != root) {
    sb.append(nod.getBit());
    nod = nod.getParent();
  }
  leaf.setCode(sb.reverse().toString());
  makeCodes(leaf.getLeft(), root);
  makeCodes(leaf.getRight(), root);
}
/**
 * Display the nodes in the tree, in in-order, by letter with Huffman code.
 * @param nod the current noide
 * @param pw a PrintWriter for the output
 */
void inOrder(HNode nod, PrintWriter pw) {
  /* your code gboes here - about 5 lines */
  if (nod.getLeft() != null){inOrder(nod.getLeft(), pw);}
  pw.println(nod.getCode());
  if (nod.getRight() != null){inOrder(nod.getRight(), pw);}
}
/**
 * Select each of the letters from the phrase, call btFind() to locate the letter node, then display the
 * phrase encoding. If btFind() returns a null, display "letter . not found".  The phrase is
 * displayed on one line as " letter:code ...".
 * @param phrase the phrase whose encoding is displayed
 * @param pw the PrintWriter for the display
 */
void displayPhrase(String phrase, PrintWriter pw) {
  int ix;
  String ky;
  HNode hn;
  /* your code goes here - about 10 lines */
  String letter;
  for(int i=0; i<phrase.length(); i++){
    if (i != phrase.length() - 1) {
      letter = phrase.substring(i,i+1);
    }
    else{
      letter = phrase.substring(i);
    }

    hn = btFind(letter, btRoot);

    if(hn!=null) {
      pw.print("\n\"" + letter + "\" " + hn.getCode());
    }
    else{
      pw.print("\nLetter \"" + letter + "\" not found.");
    }
  }
}
/**
 * The execution code container.
 * @param freqPairs letter frequency pairs [i][0]:letter, [i][1]:freq
 * @param pw a PrinterWriter for writing the letter codes and the final encoded string
 */
void execute(String[][] freqPairs, PrintWriter pw) {
  HNode root;
  String phrase = "pace university";
  /*
   * Build the priority queue and a binary tree to track the letter nodes.
   */
  buildPriorityQueueAndTree(freqPairs);
  /*
   * Build the Huffman code tree
   */
  root = buildHuffmanTree();
  /*
   * Make the Huffman codes.
   */
  makeCodes(btRoot.getRight(), root);
  /*
   * display the Huffman codes
   */
  inOrder(btRoot.getRight(), pw);
  /*
   * display the encoded phrase
   */
  displayPhrase(phrase, pw);
}
public static void main(String[] args)
{
  PrintWriter pw = new PrintWriter(System.out);
  HuffmanWithPriorityHeap hc = new HuffmanWithPriorityHeap();
  String[][] freqPairs = {
      {"p", "74319"},
      {"a", "188742"},
      {"c", "99301"},
      {"e", "277759"},
      {"u", "80992"},
      {"n", "167704"},
      {"i", "224262"},
      {"v", "22822"},
      {"r", "171232"},
      {"s", "237423"},
      {"t", "162759"},
      {"y", "39733"},
      {" ", "150333"}};
  hc.execute(freqPairs, pw);
  pw.close();
}
}
