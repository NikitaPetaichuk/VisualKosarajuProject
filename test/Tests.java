import VisualKosarajuLogic.KosarajuAlgorithm;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Tests {
    private KosarajuAlgorithm tka = new KosarajuAlgorithm();

    @Before
    public void Inic(){
        List<String[]> e = new ArrayList<String[]>();
        List<String> v = new ArrayList<String>();

        v.add("A");
        v.add("B");
        v.add("C");
        v.add("D");
        v.add("F");

        e.add(new String[]{"A", "B"});
        e.add(new String[]{"A", "D"});
        e.add(new String[]{"B", "C"});
        e.add(new String[]{"B", "D"});
        e.add(new String[]{"D", "C"});
        e.add(new String[]{"D", "F"});
        e.add(new String[]{"F", "A"});

        tka.createGraph(v, e);
    }

    @Test
    public void testTranposeGraph(){
        KosarajuAlgorithm tka1 = new KosarajuAlgorithm();
        List<String[]> e = new ArrayList<String[]>();
        List<String> v = new ArrayList<String>();

        v.add("A");
        v.add("B");
        v.add("C");
        v.add("D");
        v.add("F");

        e.add(new String[]{"B", "A"});
        e.add(new String[]{"D", "A"});
        e.add(new String[]{"C", "B"});
        e.add(new String[]{"D", "B"});
        e.add(new String[]{"C", "D"});
        e.add(new String[]{"F", "D"});
        e.add(new String[]{"A", "F"});

        tka1.createGraph(v, e);
        tka.graph = tka.graph.transposeGraph();
        assertEquals(tka.graph.vertexMap, tka1.graph.vertexMap);
    }

    @Test
    public void testTOutDepthTraversal(){
        List<String> res = Arrays.asList("C", "A", "F", "D", "B");
        assertEquals(tka.tOutDepthTraversal(tka.graph.transposeGraph()), res);
    }

    @Test
    public void testDepthFirstTraversal(){
        List<String> res = Arrays.asList("C", null, "A", "B", "D", "F");
        assertEquals(tka.depthFirstTraversal(tka.tOutDepthTraversal(tka.graph.transposeGraph())), res);
    }
}
