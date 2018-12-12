package de.tuberlin.dima.dbt.exercises.bplustree;

import org.junit.Test;

import static de.tuberlin.dima.dbt.exercises.bplustree.BPlusTreeUtilities.*;
import static de.tuberlin.dima.dbt.grading.BPlusTreeMatcher.isTree;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class BPlusTreeHelperTest {

    private BPlusTree tree;

    ///// Test using DIMA Feedback
    ///// lookup feed log

    @Test
    public void Test1() {
        // given
        tree = newTree(newNode(keys(86, 104, 120, 146),
                nodes(newLeaf(keys(62, 76), values("Ssl", "UiH")),
                        newLeaf(keys(86, 97), values("BqN", "PoM")),
                        newLeaf(keys(104, 108), values("fgB", "swA")),
                        newLeaf(keys(120, 131), values("HBG", "upA")),
                        newLeaf(keys(146, 150), values("eBQ", "ivR")))));
        // when
        String value = tree.lookup(150);
        // then
        assertThat(value, is("ivR"));
    }

    @Test
    public void Test2() {
        // given
        tree = newTree(newLeaf(keys(25, 40, 46, 53), values("Ssl", "UiH", "BqN", "PoM")));

        // when
        String value = tree.delete(71);
        // then
        assertThat(tree, isTree(newTree(newLeaf(keys(25, 40, 46, 53), values("Ssl", "UiH", "BqN", "PoM")))));
    }

    @Test
    public void Test3() {
        // given
        tree = newTree(newNode(keys(63), nodes(newLeaf(keys(45, 52), values("Ssl", "UiH")),
                newLeaf(keys(63, 71), values("BqN", "PoM")))));

        // when
        String value = tree.delete(52);
        // then
        assertThat(tree, isTree(newTree(newLeaf(keys(45, 63, 71), values("Ssl", "BqN", "PoM")))));
    }

    @Test
    public void Test4() {
        // given
        tree = newTree(newLeaf(keys(), values()));
        // when
        tree.insert(30, "Pdo");
        tree.insert(43, "XMu");
        tree.insert(47, "Kxg");

        // then
        assertThat(tree, isTree(
                newTree(newLeaf(keys(30, 43, 47), values("Pdo", "XMu", "Kxg")))));
    }

    @Test
    public void Test5() {
        // given
        tree = newTree(newNode(keys(115, 135, 153, 176),
                nodes(newLeaf(keys(94, 106), values("Ssl", "UiH")),
                        newLeaf(keys(115, 120), values("BqN", "PoM")),
                        newLeaf(keys(135, 145), values("fgB", "swA")),
                        newLeaf(keys(153, 162), values("HBG", "upA")),
                        newLeaf(keys(176, 180, 199, 205), values("eBQ", "ivR", "jUP", "klA")))));
        // when
        tree.insert(211, "Hbi");
        // then
        assertThat(tree, isTree(newTree(
                newNode(keys(153),
                        nodes(newNode(keys(115, 135), nodes(
                                newLeaf(keys(94, 106), values("Ssl", "UiH")),
                                newLeaf(keys(115, 120), values("BqN", "PoM")),
                                newLeaf(keys(135, 145), values("fgB", "swA")))),
                                newNode(keys(176, 199), nodes(
                                        newLeaf(keys(153, 162), values("HBG", "upA")),
                                        newLeaf(keys(176, 180), values("eBQ", "ivR")),
                                        newLeaf(keys(199, 205, 211), values("jUP", "klA", "Hbi")))))))));
    }

    @Test
    public void Test6() {
        // given
        tree = newTree(newNode(keys(86, 108, 130, 150),
                nodes(newLeaf(keys(65, 77), values("yxU", "sdF")),
                        newLeaf(keys(86, 103), values("LFr", "Dkp")),
                        newLeaf(keys(108, 116), values("qDN", "ajC")),
                        newLeaf(keys(130, 144), values("gQF", "zEn")),
                        newLeaf(keys(150, 156), values("ESu", "IMq"))
                )));
        // when
        String value = tree.lookup(156);
        // then
        assertThat(value, is("IMq"));
    }


    @Test
    public void Test7() {
        // given
        tree = newTree(newNode(keys(3, 5),
                nodes(newLeaf(keys(1, 2), values("a", "b")),
                        newLeaf(keys(3, 4), values("c", "d")),
                        newLeaf(keys(5, 6), values("e", "f")))));
        // when
        String value = tree.delete(2);
        // then
        assertThat(value, is("b"));
        assertThat(tree, isTree(newTree(newNode(
                keys(5), nodes(newLeaf(keys(1, 3, 4), values("a", "c", "d")),
                        newLeaf(keys(5, 6), values("e", "f")))))));
    }


    @Test
    public void Test8() {
        // given
        tree = newTree(newNode(keys(143),
                nodes(newNode(keys(105, 122),
                        nodes(newLeaf(keys(85, 91), values("Ssl", "UiH")),
                                newLeaf(keys(105, 112), values("BqN", "PoM")),
                                newLeaf(keys(122, 128), values("eBQ", "ivR")))),
                        newNode(keys(165, 180),
                                nodes(newLeaf(keys(143, 152), values("Qna", "jcf")),
                                        newLeaf(keys(165, 170), values("uIn", "TZL")),
                                        newLeaf(keys(180, 188), values("hzP", "dUW")))))));

        // when
        String value = tree.delete(128);
        // then
        assertThat(value, is("ivR"));
        assertThat(tree, isTree(newTree(newNode(
                keys(105, 143, 165, 180),
                nodes(nodes(
                        newLeaf(keys(85, 91), values("Ssl", "UiH")),
                        newLeaf(keys(105, 112, 122), values("BqN", "PoM", "eBQ")),
                        newLeaf(keys(143, 152), values("Qna", "jcf")),
                        newLeaf(keys(165, 170), values("uIn", "TZL")),
                        newLeaf(keys(180, 188), values("hzP", "dUW"))))))));

    }


    @Test
    public void Test9() {
        // given
        tree = newTree(newNode(keys(143),
                nodes(newNode(keys(105, 122),
                        nodes(newLeaf(keys(85, 91), values("Ssl", "UiH")),
                                newLeaf(keys(105, 112), values("BqN", "PoM")),
                                newLeaf(keys(122, 128), values("eBQ", "ivR")))),
                        newNode(keys(165, 180),
                                nodes(newLeaf(keys(143, 152), values("Qna", "jcf")),
                                        newLeaf(keys(165, 170), values("uIn", "TZL")),
                                        newLeaf(keys(180, 188), values("hzP", "dUW")))))));

        // when
        String value = tree.delete(180);
        // then
        assertThat(value, is("hzP"));
        assertThat(tree, isTree(newTree(newNode(
                keys(105, 122, 143, 165),
                nodes(nodes(
                        newLeaf(keys(85, 91), values("Ssl", "UiH")),
                        newLeaf(keys(105, 112), values("BqN", "PoM")),
                        newLeaf(keys(122, 128), values("eBQ", "ivR")),
                        newLeaf(keys(143, 152), values("Qna", "jcf")),
                        newLeaf(keys(165, 170, 188), values("uIn", "TZL", "dUW"))))))));

    }

    @Test
    public void Test10() {
        // given
        tree = newTree(newNode(keys(78, 99, 116, 137),
                nodes(newLeaf(keys(53, 66), values("Ssl", "UiH")),
                        newLeaf(keys(78, 81), values("BqN", "PoM")),
                        newLeaf(keys(99, 107), values("fgB", "swA")),
                        newLeaf(keys(116, 121, 137, 144), values("HBG", "upA", "Ite", "KJz")),
                        newLeaf(keys(162, 172), values("eBQ", "ivR")))));
        // when
        String value = tree.lookup(158);
        // then
        assertThat(value, is(nullValue()));
    }

    @Test
    public void Test11() {
        // given
        tree = newTree(newNode(keys(119),
                nodes(newLeaf(keys(95, 106), values("a", "b")),
                        newLeaf(keys(119, 130), values("c", "d")))));
        // when
        String value = tree.delete(130);
        // then
        assertThat(tree, isTree(newTree((newLeaf(keys(95, 106, 119), values("a", "b", "c"))))));
    }

    @Test
    public void Test12() {
        // given
        tree = newTree(newNode(keys(108),
                nodes(newNode(keys(69, 87),
                        nodes(newLeaf(keys(44, 55), values("Ssl", "UiH")),
                                newLeaf(keys(69, 75), values("BqN", "PoM")),
                                newLeaf(keys(87, 98), values("eBQ", "ivR")))),
                        newNode(keys(129, 145, 167),
                                nodes(newLeaf(keys(108, 116), values("Qna", "jcf")),
                                        newLeaf(keys(129, 141), values("uIn", "TZL")),
                                        newLeaf(keys(145, 153), values("gGh", "UuA")),
                                        newLeaf(keys(167, 178), values("hzP", "dUW")))))));

        // when
        String value = tree.delete(75);
        // then
        assertThat(value, is("PoM"));
        assertThat(tree, isTree(newTree(newNode(keys(129),
                nodes(newNode(keys(69, 108),
                        nodes(newLeaf(keys(44, 55), values("Ssl", "UiH")),
                                newLeaf(keys(69, 87, 98), values("BqN", "eBQ", "ivR")),
                                newLeaf(keys(108, 116), values("Qna", "jcf")))),
                        newNode(keys(145, 167),
                                nodes(newLeaf(keys(129, 141), values("uIn", "TZL")),
                                        newLeaf(keys(145, 153), values("gGh", "UuA")),
                                        newLeaf(keys(167, 178), values("hzP", "dUW")))))))));

    }

    @Test
    public void Test13() {
        tree = newTree(newLeaf(keys(), values(), 2));
        tree.insert(106, "Fnp");
        tree.insert(117, "lQh");
        tree.insert(127, "cCC");
    }

    @Test
    public void Test14() {
        tree = newTree(newLeaf(keys(), values(), 2));
        tree.insert(43, "Fnp");
        tree.insert(59, "lQh");
        tree.insert(61, "cCC");
        tree.insert(80, "Fnp");
        tree.insert(82, "lQh");

        assertThat(tree, isTree(newTree(newNode(keys(61),
                nodes(newNode(keys(59),
                        nodes(newLeaf(keys(43), values("Fnp"), 2),
                                newLeaf(keys(59), values("lQh"), 2)), 2),
                        newNode(keys(80),
                                nodes(newLeaf(keys(61), values("cCC"), 2),
                                        newLeaf(keys(80, 82), values("Fnp", "lQh"), 2)), 2)),2))));

    }

    @Test
    public void Test15() {
        // given
        tree = newTree(newNode(keys(108),
                nodes(newNode(keys(69, 87),
                        nodes(newLeaf(keys(44, 55), values("Ssl", "UiH")),
                                newLeaf(keys(69, 75), values("BqN", "PoM")),
                                newLeaf(keys(87, 98), values("eBQ", "ivR")))),
                        newNode(keys(129, 145, 167),
                                nodes(newLeaf(keys(108, 116), values("Qna", "jcf")),
                                        newLeaf(keys(129, 141), values("uIn", "TZL")),
                                        newLeaf(keys(145, 153), values("gGh", "UuA")),
                                        newLeaf(keys(167, 178), values("hzP", "dUW")))))));

        // when
        String value = tree.delete(75);
        // then
        assertThat(value, is("PoM"));
        assertThat(tree, isTree(newTree(newNode(keys(129),
                nodes(newNode(keys(69, 108),
                        nodes(newLeaf(keys(44, 55), values("Ssl", "UiH")),
                                newLeaf(keys(69, 87, 98), values("BqN", "eBQ", "ivR")),
                                newLeaf(keys(108, 116), values("Qna", "jcf")))),
                        newNode(keys(145, 167),
                                nodes(newLeaf(keys(129, 141), values("uIn", "TZL")),
                                        newLeaf(keys(145, 153), values("gGh", "UuA")),
                                        newLeaf(keys(167, 178), values("hzP", "dUW")))))))));

    }

    @Test
    public void Test16() {
        tree = newTree(newLeaf(keys(), values(), 4));
        tree.delete(36);
    }

    @Test
    public void Test17() {
        tree = newTree(newLeaf(keys(109), values("fUm"), 4));
        tree.delete(109);
    }

    @Test
    public void Test18() {
        // given
        tree = newTree(newNode(keys(188),
                nodes(newNode(keys(126,151,168),
                        nodes(  newLeaf(keys(108,115), values("Ssl", "UiH")),
                                newLeaf(keys(126,134), values("TLL", "CdX")),
                                newLeaf(keys(151,162), values("BqN", "PoM")),
                                newLeaf(keys(168,182), values("eBQ", "ivR")))),
                        newNode(keys(210,229),
                        nodes(  newLeaf(keys(188,195), values("Qna", "jcf")),
                                newLeaf(keys(210,220), values("gGh", "UuA")),
                                newLeaf(keys(229,239), values("hzP", "dUW")))))));

        // when
        String value = tree.delete(220);
        // then
        assertThat(value, is("UuA"));
        assertThat(tree, isTree(newTree(newNode(keys(168),
                nodes(newNode(keys(126, 151),
                        nodes(  newLeaf(keys(108,115), values("Ssl", "UiH")),
                                newLeaf(keys(126,134), values("TLL", "CdX")),
                                newLeaf(keys(151,162), values("BqN", "PoM")))),
                        newNode(keys(188,210),
                        nodes(  newLeaf(keys(168,182), values("eBQ", "ivR")),
                                newLeaf(keys(188,195), values("Qna", "jcf")),
                                newLeaf(keys(210, 229,239), values("gGh", "hzP", "dUW")))))))));

    }

}





