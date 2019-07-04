package projects.bpt;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * A jUnit test suite for {@link BinaryPatriciaTrie}. You should extend this test suite with more tests of your own!
 *
 * @author Yi Liu
 */
public class StudentTests {


    @Test public void testEmptyTrie() {
        BinaryPatriciaTrie trie = new BinaryPatriciaTrie();

        assertTrue("Trie should be empty",trie.isEmpty());
        assertTrue("Trie size should be 0",trie.getSize() == 0);

        assertFalse("No string inserted so search should fail", trie.search("0101"));

    }

    @Test public void testFewInsertionsWithSearch() {
        BinaryPatriciaTrie trie = new BinaryPatriciaTrie();

        trie.insert("");
        trie.insert("000");
        trie.insert("001");
        trie.insert("010");
        trie.insert("011");
        trie.insert("100");
        trie.insert("101");
        trie.insert("110");
        trie.insert("111");
        trie.insert("1");
        trie.insert("0");
        trie.insert("00");
        trie.insert("01");
        trie.insert("10");
        trie.insert("11");

        //trie.delete("");

        trie.delete("000");
        trie.delete("001");
        trie.delete("010");
        trie.delete("011");
        trie.delete("1");
        trie.delete("0");
        trie.delete("00");
        trie.delete("01");
        trie.delete("10");
        trie.delete("11");


        Iterator<String> iter = trie.inorderTraversal();




        trie.delete("100");
        trie.delete("101");
        trie.delete("110");
        trie.delete("111");

        while(iter.hasNext()){
            System.out.println("iter   "+iter.next());
        }
        System.out.println("Longest    " + trie.getLongest());



        System.out.println("Longest    " + trie.getLongest());
        System.out.println(trie.search(""));
        System.out.println(trie.isJunkFree());
    }

    @Test public void delete() {
        BinaryPatriciaTrie trie = new BinaryPatriciaTrie();

        trie.insert("00100");
        trie.insert("001");
        trie.insert("11");
        trie.insert("1101");
        trie.insert("1111000");
        trie.insert("110001");

        trie.delete("11000");
        System.out.println(trie.search("00100")&&trie.search("001")&&trie.search("11")&&trie.search("1101")&&trie.search("1111000")&&trie.search("110001"));
        System.out.println(trie.search("001"));
        System.out.println(trie.getSize());
        System.out.println(trie.isJunkFree());
    }

    @Test public void Traversal_and_Longest(){
        BinaryPatriciaTrie trie = new BinaryPatriciaTrie();
        trie.insert("000");
        trie.insert("001");
        trie.insert("011");
        trie.insert("1001");
        trie.insert("1");
        while (trie.inorderTraversal().hasNext()){
            System.out.println(trie.inorderTraversal().next());
        }
    }


    //testing isEmpty function
    @Test public void testFewInsertionsWithDeletion() {
        BinaryPatriciaTrie trie = new BinaryPatriciaTrie();

        trie.insert("000");
        trie.insert("001");
        trie.insert("011");
        trie.insert("1001");
        trie.insert("1");
        assertTrue("should exist",trie.search("1001"));

        trie.insert("1001");
        assertTrue("should exist",trie.search("1001"));
        trie.insert("1");


/*        trie.insert("000");
        trie.insert("0");
        trie.insert("000");*/




        assertFalse("After inserting five strings, the trie should not be considered empty!", trie.isEmpty());
        assertEquals("After inserting five strings, the trie should report five strings stored.", 5, trie.getSize());

        trie.delete("0"); // Failed deletion; should affect exactly nothing.
        assertEquals("After inserting five strings and requesting the deletion of one not in the trie, the trie " +
                "should report five strings stored.", 5, trie.getSize());
        assertTrue("After inserting five strings and requesting the deletion of one not in the trie, the trie had some junk in it!",
                trie.isJunkFree());

        trie.delete("011"); // Successful deletion
        assertEquals("After inserting five strings and deleting one of them, the trie should report 4 strings.", 4, trie.getSize());

        assertTrue("After inserting five strings and deleting one of them, the trie had some junk in it!",
                trie.isJunkFree());
    }
}
