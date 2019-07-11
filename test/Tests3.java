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
        assertEquals(tka1.graph.vertexMap, tka.graph.vertexMap);
    }

    @Test
    public void testTOutDepthTraversal(){
        List<String> res = Arrays.asList("I", "J");
        assertEquals(res, tka.tOutDepthTraversal(tka.graph.transposeGraph()));
    }

    @Test
    public void testDepthFirstTraversal(){
        List<String> res = Arrays.asList("I", "J");
        assertEquals(res, tka.depthFirstTraversal(tka.tOutDepthTraversal(tka.graph.transposeGraph())));
    }

    @Test
    public void testGetTranspositionStepTrace(){
        List<String> res = Arrays.asList("I", "J", "I");
        tka.tOutDepthTraversal(tka.graph.transposeGraph());
        assertEquals(res, tka.getTranspositionStepTrace());
    }

    @Test
    public void testGetOriginalStepTrace(){
        List<String> res = Arrays.asList("I", "J");
        assertEquals(res, tka.depthFirstTraversal(tka.tOutDepthTraversal(tka.graph.transposeGraph())));
    }
}
