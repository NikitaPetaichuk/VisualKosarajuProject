import VisualKosarajuLogic.KosarajuAlgorithm;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Tests3 {
    private KosarajuAlgorithm tka = new KosarajuAlgorithm();

    @Before
    public void Inic(){
        List<String[]> e = new ArrayList<String[]>();
        List<String> v = new ArrayList<String>();

        v.add("I");
        v.add("J");

        e.add(new String[]{"I", "J"});
        e.add(new String[]{"J", "I"});

        tka.createGraph(v, e);
    }

    @Test
    public void testTranposeGraph(){
        KosarajuAlgorithm tka1 = new KosarajuAlgorithm();
        List<String[]> e = new ArrayList<String[]>();
        List<String> v = new ArrayList<String>();

        v.add("I");
        v.add("J");

        e.add(new String[]{"I", "J"});
        e.add(new String[]{"J", "I"});

        tka1.createGraph(v, e);
        tka.graph = tka.graph.transposeGraph();
        assertEquals(tka.graph.vertexMap, tka1.graph.vertexMap);
    }

    @Test
    public void testTOutDepthTraversal(){
        List<String> res = Arrays.asList("I", "J");
        assertEquals(tka.tOutDepthTraversal(tka.graph.transposeGraph()), res);
    }

    @Test
    public void testDepthFirstTraversal(){
        List<String> res = Arrays.asList("I", "J");
        assertEquals(tka.depthFirstTraversal(tka.tOutDepthTraversal(tka.graph.transposeGraph())), res);
    }
}
