package dnasplicing;

import static sbcc.Core.*;

import java.util.regex.*;

public class LinkedDnaStrand implements DnaStrand {

	private StringBuilder nucleotides = new StringBuilder();
	private DnaSequenceNode tail;
	private DnaSequenceNode head;
	private int nodeCount = 1;
	private int appendCount = 0;


	public LinkedDnaStrand(String dnaSequence) {
		DnaSequenceNode first = new DnaSequenceNode(dnaSequence);
		first.next = null;
		first.previous = null;
		head = first;
		tail = head;
		nucleotides.append(dnaSequence);

	}


	@Override
	public long getNucleotideCount() {
		return nucleotides.length();
	}


	@Override
	public void append(String dnaSequence) {
		DnaSequenceNode newNode = new DnaSequenceNode(dnaSequence);

		if (this.nucleotides == null) {
			newNode.previous = null;
			newNode.next = null;
			this.head = newNode;
			this.nucleotides.append(dnaSequence);
		}

		else {
			this.tail.next = newNode;
			newNode.previous = tail;
			newNode.next = null;
			this.tail = newNode;
			this.nucleotides.append(dnaSequence);
			this.appendCount++;
			this.nodeCount++;
		}

	}


	@Override
	public DnaStrand cutSplice(String enzyme, String splicee) {
		LinkedDnaStrand spliced = null;
		int pos = 0;
		int start = 0;
		StringBuilder temp = nucleotides;
		boolean first = true;

		while ((pos = temp.indexOf(enzyme, pos)) >= 0) {
			if (first) {
				if (temp.indexOf(enzyme) == 0) {
					spliced = new LinkedDnaStrand(splicee);
				} else {
					spliced = new LinkedDnaStrand(temp.substring(start, pos));
				}
				first = false;
			} else if (temp.substring(start, pos).length() != 0) {
				spliced.append(temp.substring(start, pos));
			}
			start = pos + enzyme.length();
			if (pos != 0) {
				spliced.append(splicee);
			}
			pos++;
		}

		if (start < temp.length()) {
			if (spliced == null) {
				spliced = new LinkedDnaStrand("");
			} else {
				spliced.append(temp.substring(start));
			}
		}

		return spliced;
	}


	@Override
	public DnaStrand createReversedDnaStrand() {
		String nReversed = this.nucleotides.reverse().toString();
		LinkedDnaStrand nucleotidesReversed = new LinkedDnaStrand(nReversed);

		return nucleotidesReversed;
	}


	@Override
	public int getAppendCount() {
		return this.appendCount;
	}


	@Override
	public DnaSequenceNode getFirstNode() {
		return head;
	}


	@Override
	public int getNodeCount() {
		return nodeCount;
	}


	@Override
	public String toString() {
		return this.nucleotides.toString();
	}


	@Override
	public void printN() {
		println(this.nucleotides.toString());
	}
}
