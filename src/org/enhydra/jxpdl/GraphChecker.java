/**
 * Together XPDL Model
 * Copyright (C) 2010 Together Teamsolutions Co., Ltd. 
 * 
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 *
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU General Public License for more details. 
 *
 * You should have received a copy of the GNU General Public License 
 * along with this program. If not, see http://www.gnu.org/licenses
 */

package org.enhydra.jxpdl;

import java.util.Vector;

/**
 * This class can be used to check if the graph is cyclic, or to find index of
 * corresponding join node for the given split node index. When constructing class, you
 * have to pass it the incidence matrix, which has to be the two-dimensional array of
 * booleans, where the rows and column indexes represents the graph node indexes, and
 * values represents if there is a connection between these nodes. If there is connection
 * from node <b>i</b> to the node <b>j</b> it is represented by putting <b>true</true>
 * into j'th column of the i'th row.
 * 
 * @author Predrag Djojic
 */
public class GraphChecker {
   /** The two dimensional array of booleans representing the graphs incidence matrix. */
   private boolean[][] mat;

   /** The original incidence matrix at a time of creation of this object instance. */
   private boolean[][] tempMat;

   /** Dimension of the incidence matrix. */
   private int dim = 0;

   /**
    * Returns true if source node is linked to the destination node.
    * 
    * @param srcNode Source node.
    * @param dstNode Destination node.
    * @return true if source node is linked to the destination node.
    */
   private boolean isLinked(int srcNode, int dstNode) {
      return mat[srcNode][dstNode];
   }

   // private void link(int srcNode, int dstNode){
   // mat[srcNode][dstNode]=true;
   // }

   /**
    * Unlinks source and destination node.
    * 
    * @param srcNode Source node.
    * @param dstNode Destination node.
    */
   private void unlink(int srcNode, int dstNode) {
      mat[srcNode][dstNode] = false;
   }

   /**
    * Unlinks the parents of the node at given position.
    * 
    * @param node Node which will be unlinked from its parents.
    */
   private void unlinkParents(int node) {
      for (int i = 0; i < dim; i++) {
         unlink(i, node);
      }
   }

   /**
    * Unlinks the children of the node at given position.
    * 
    * @param node Node which will be unlinked from its parents.
    */
   private void unlinkChildren(int node) {
      for (int i = 0; i < dim; i++) {
         unlink(node, i);
      }
   }

   /**
    * Returns the Integer value of the node.
    * 
    * @param index Index.
    * @return The Integer value of the node.
    */
   private Integer node(int index) {
      return new Integer(index);
   }

   /**
    * Returns the int value of the node.
    * 
    * @param node Node
    * @return The int value of the node.
    */
   private int index(Integer node) {
      return node.intValue();
   }

   /**
    * Returns the value of the node with a given position.
    * 
    * @param nodeSet The vecotr of nodes.
    * @param pos Position of a node to check.
    * @return The int value of the node.
    */
   private int indexAt(Vector nodeSet, int pos) {
      return index((Integer) nodeSet.elementAt(pos));
   }

   /**
    * Returns true if node at given position is contained within given set.
    * 
    * @param nodeSet The vector of nodes.
    * @param nodeIndex Index of a node to check.
    * @return true if node at given position is contained within given set.
    */
   private boolean isInSet(Vector nodeSet, int nodeIndex) {
      for (int i = 0; i < nodeSet.size(); i++) {
         if (nodeIndex == indexAt(nodeSet, i)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Returns true if graph is empty.
    */
   private boolean isGraphEmpty() {
      boolean link = false;
      for (int i = 0; i < dim; i++) {
         for (int j = 0; j < dim; j++) {
            link = link || isLinked(i, j);
         }
      }
      return !link;
   }

   // private boolean isJoin(int node){
   // int parentCount=0;
   // for(int i=0;i<dim;i++){
   // if(isLinked(i,node)){
   // parentCount++;
   // }
   // }
   // return (parentCount>1);
   //
   // }

   /**
    * Returns true if node at given position is split node.
    * 
    * @param node Node
    * @return true if node at given position is split node.
    */
   private boolean isSplit(int node) {
      int childCount = 0;
      for (int i = 0; i < dim; i++) {
         if (isLinked(node, i)) {
            childCount++;
         }
      }
      return (childCount > 1);
   }

   /**
    * Returns true if node at given position is isolated.
    * 
    * @param node Node
    * @return true if node at given position is isolated.
    */
   private boolean isIsolated(int node) {
      return (!hasChild(node)) || (!hasParent(node));
   }

   /**
    * Returns true if node at given position has child.
    * 
    * @param node Node
    * @return true if node at given position has child.
    */
   private boolean hasChild(int node) {
      boolean child = false;
      for (int i = 0; i < dim; i++) {
         child = child || isLinked(node, i);
      }
      return child;
   }

   /**
    * Returns true if node at given position has parent.
    * 
    * @param node Node
    * @return true if node at given position has parent.
    */
   private boolean hasParent(int node) {
      boolean parent = false;
      for (int i = 0; i < dim; i++) {
         parent = parent || isLinked(i, node);
      }
      return parent;
   }

   /**
    * Constructs the GraphChecker object.
    * 
    * @param matParam The two dimensional array of booleans representing the graphs
    *           incidence matrix.
    */
   public GraphChecker(boolean[][] matParam) {
      tempMat = matParam;
      dim = tempMat.length;
      mat = new boolean[dim][dim];
      for (int x = 0; x < dim; x++) {
         for (int y = 0; y < dim; y++) {
            mat[x][y] = tempMat[x][y];
         }
      }
   }

   /** Undoes the changes by filling with the original one. */
   private void undo() {
      for (int x = 0; x < dim; x++) {
         for (int y = 0; y < dim; y++) {
            mat[x][y] = tempMat[x][y];
         }
      }
   }

   /**
    * @return <code>true</code> if the graph is cyclic, and <code>false</code> otherwise.
    */
   public boolean isGraphCyclic() {
      boolean ret = false;
      boolean changed = true;
      undo();
      while (changed) {
         changed = false;
         for (int i = 0; i < dim; i++) {
            if ((!hasChild(i)) || (!hasParent(i))) {
               if (hasChild(i)) {
                  unlinkChildren(i);
                  changed = true;
               }
               if (hasParent(i)) {
                  unlinkParents(i);
                  changed = true;
               }
            }
         }
      }
      ret = !isGraphEmpty();
      undo();
      return ret;
   }

   /**
    * @return The array of graph node indexes that are within some graph cycle. If the
    *         graph is not cyclic, returns <code>null</code>.
    */
   public int[] getCyclicNodes() {
      undo();
      boolean changed = true;
      while (changed) {
         changed = false;
         for (int i = 0; i < dim; i++) {
            if ((!hasChild(i)) || (!hasParent(i))) {
               if (hasChild(i)) {
                  unlinkChildren(i);
                  changed = true;
               }
               if (hasParent(i)) {
                  unlinkParents(i);
                  changed = true;
               }
            }
         }
      }
      if (!isGraphEmpty()) {
         int nodeCount = 0;
         for (int i = 0; i < dim; i++) {
            if (!isIsolated(i)) {
               nodeCount++;
            }
         }
         int[] nodeArray = new int[nodeCount];
         int index = 0;
         for (int i = 0; i < dim; i++) {
            if (!isIsolated(i)) {
               nodeArray[index++] = i;
            }
         }
         undo();
         return nodeArray;
      }

      undo();
      return null;
   }

   /**
    * Returns index of corresponding join node for the given split node index.
    * 
    * @param nodeX The index of split node
    * @return Index of corresponding join node if it exists, <b>-1</b> otherwise.
    */
   public int getJoinIndex(int nodeX) {
      undo();
      if (!isGraphCyclic()) {
         undo();
         if (isSplit(nodeX)) { // checking if the node has at least two outgoing branches
            Vector workingSet = new Vector();
            // putting the children of given node into the workingSet (there
            // must be at least two children)
            for (int i = 0; i < dim; i++) {
               if (isLinked(nodeX, i)) {
                  unlink(nodeX, i);
                  workingSet.addElement(node(i));
               }
            }
            boolean changed = true;
            while (changed) {
               changed = false;
               Vector tempSet = new Vector();
               // remove all nodes that have parent (they are waiting for
               // parent to get to them) from working set, and place them
               // into temporary set
               // System.out.println("WS1="+workingSet);
               for (int j = workingSet.size() - 1; j >= 0; j--) {
                  int actual = indexAt(workingSet, j);
                  if (hasParent(actual) && !(isInSet(tempSet, actual))) {
                     tempSet.addElement(node(actual));
                     workingSet.remove(j);
                  }
               }
               // System.out.println("WS2="+workingSet);
               // if temporary set is empty, and working set has only one
               // node -> this node is one we are looking for
               if (workingSet.size() == 1 && tempSet.size() == 0) {
                  return indexAt(workingSet, 0);
               }

               // iterating through the whole set
               for (int j = 0; j < workingSet.size(); j++) {
                  // determine the index of the node from j'th position within the working
                  // set
                  int actual = indexAt(workingSet, j);
                  boolean actualChanged = false;
                  // Iterating through incidence matrix and finding the children
                  for (int k = 0; k < dim; k++) {
                     if (isLinked(actual, k)) { // if the k'th node is the child
                        unlink(actual, k); // unlinking it
                        changed = true; // something is is changed
                        actualChanged = true;
                        if (!(isInSet(tempSet, k))) { // if child was not in the tempSet
                           tempSet.addElement(node(k)); // put it to wait for the next
                                                        // while loop iteration
                        }
                     }
                  }
                  // if nothing changed to the actual node->it is possibly the
                  // the wanted node, so put it into tempSet
                  if (!actualChanged && !(isInSet(tempSet, actual))) {
                     tempSet.addElement(node(actual));
                  }
                  // System.out.println("actual="+actual+"WSS="+(workingSet.size()-1)+", j="+j+", TSS="+tempSet.size()+", TS="+tempSet);
                  // System.out.println("Iter "+j+", ts="+tempSet);
               }
               // working set for the next iteration becomes tempSet
               workingSet = tempSet;
            }
         }
         return -1;
      }
      return -1;
   }
}
