import VisualKosarajuLogic.KosarajuAlgorithm;
import org.junit.*;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class Tests1 {
    private KosarajuAlgorithm tka = new KosarajuAlgorithm();

    @Before
    public void Inic(){
        List<String[]> e = new ArrayList<String[]>();
        List<String> v = new ArrayList<String>();

        v.add("A");
        v.add("B");
        v.add("C");
        v.add("D");
        v.add("E");
        v.add("F");

        e.add(new String[]{"A", "B"});
        e.add(new String[]{"A", "F"});
        e.add(new String[]{"B", "F"});
        e.add(new String[]{"C", "A"});
        e.add(new String[]{"C", "E"});
        e.add(new String[]{"D", "C"});
        e.add(new String[]{"E", "D"});
        e.add(new String[]{"F", "C"});

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
        v.add("E");
        v.add("F");

        e.add(new String[]{"B", "A"});
        e.add(new String[]{"F", "A"});
        e.add(new String[]{"F", "B"});
        e.add(new String[]{"A", "C"});
        e.add(new String[]{"E", "C"});
        e.add(new String[]{"C", "D"});
        e.add(new String[]{"D", "E"});
        e.add(new String[]{"C", "F"});

        tka1.createGraph(v, e);
        tka.graph = tka.graph.transposeGraph();
        assertEquals(tka1.graph.vertexMap, tka.graph.vertexMap);
    }

    @Test
    public void testTOutDepthTraversal(){
        List<String> res = Arrays.asList("A", "C", "F", "B", "D", "E");
        assertEquals(res, tka.tOutDepthTraversal(tka.graph.transposeGraph()));
    }

    @Test
    public void testDepthFirstTraversal(){
        List<String> res = Arrays.asList("A", "B", "F", "C", "E", "D");
        assertEquals(res, tka.depthFirstTraversal(tka.tOutDepthTraversal(tka.graph.transposeGraph())));
    }

    @Test
    public void testGetTranspositionStepTrace(){
        List<String> res = Arrays.asList("A", "C", "D", "E", "D", "C", "F", "B", "F", "C", "A");
        tka.tOutDepthTraversal(tka.graph.transposeGraph());
        assertEquals(res, tka.getTranspositionStepTrace());
    }

    @Test
    public void testGetOriginalStepTrace(){
        List<String> res = Arrays.asList("A", "B", "F", "C", "E", "D");
        assertEquals(res, tka.depthFirstTraversal(tka.tOutDepthTraversal(tka.graph.transposeGraph())));
    }
}
