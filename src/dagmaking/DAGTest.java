package dagmaking;

import static org.junit.Assert.*;

import org.junit.Test;


import parsing.Parser;
import parsing.TreeNode;


public class DAGTest
{
/**
 * Tests for when the root Node from non directed graph is a leaf
 */
  @Test
  public void rootLeafTest()
  {
    TreeNode root = new TreeNode("x");
    assertTrue("leaf tree", root.equals(DAG.makeDAG(root)));
  }//rootLeafTest

  @Test
  public void oneLevelTreeTest() throws Exception
  {
    TreeNode tree = Parser.parse("sum(x, x)");
    TreeNode dag = DAG.makeDAG(tree);
    assertTrue("tree and dag must have the same string representation",
               dag.equals(tree));
    TreeNode treeKid1 = tree.getChildren().get(0);
    TreeNode treeKid2 = tree.getChildren().get(1);
    TreeNode dagKid1 = dag.getChildren().get(0);
    TreeNode dagKid2 = dag.getChildren().get(1);
    assertTrue("two children are the same", dagKid1.equals(dagKid2));
    // hashCode() is not overridden for TreeNode class, so it depends on the reference
    assertFalse("two identical tree children must not have the same hash", 
                treeKid1.hashCode() == treeKid2.hashCode()); // PASS
    assertTrue("two identical dag children must have the same hash", 
                dagKid1.hashCode() == dagKid2.hashCode()); // PASS
    // checking references directly
    assertFalse("two identical tree children must not have the same reference", 
               treeKid1 == treeKid2); // PASS
    assertTrue("two identical children must have the same reference", 
               dagKid1 == dagKid2); // PASS
    
  }
  
  @Test
  public void twoLevelTreeTest() throws Exception
  {
    TreeNode tree = Parser.parse("wsum(mult(x, x),mult(x, x), x)");
    TreeNode dag = DAG.makeDAG(tree);
    assertTrue("tree and dag must have the same string representation",
               dag.equals(tree));
    TreeNode multKid1 = dag.getChildren().get(0);
    TreeNode multKid2 = dag.getChildren().get(1);
    TreeNode xKidLevel2 = dag.getChildren().get(2);
    TreeNode xKidLevel3 = multKid1.getChildren().get(0);
    
    assertTrue("two mult subtrees must have the same hash", 
               multKid1.hashCode() == multKid2.hashCode());
    assertTrue("two x subtrees on different levels must have the same hash", 
               xKidLevel2.hashCode() == xKidLevel3.hashCode());
  }
}
