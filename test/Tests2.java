import VisualKosarajuLogic.KosarajuAlgorithm;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Tests2 {
    private KosarajuAlgorithm tka = new KosarajuAlgorithm();

    @Before
    public void Inic(){
        List<String[]> e = new ArrayList<String[]>();
        List<String> v = new ArrayList<String>();

        v.add("K");
        v.add("G");
        v.add("H");

        e.add(new String[]{"K", "K"});
        e.add(new String[]{"G", "H"});
        e.add(new String[]{"H", "K"});

        tka.createGraph(v, e);
    }

    @Test
    public void testTranposeGraph(){
        KosarajuAlgorithm tka1 = new KosarajuAlgorithm();
        List<String[]> e = new ArrayList<String[]>();
        List<String> v = new ArrayList<String>();

        v.add("K");
        v.add("G");
        v.add("H");

        e.add(new String[]{"K", "K"});
        e.add(new String[]{"H", "G"});
        e.add(new String[]{"K", "H"});

        tka1.createGraph(v, e);
        tka.graph = tka.graph.transposeGraph();
        assertEquals(tka.graph.vertexMap, tka1.graph.vertexMap);
    }

    @Test
    public void testTOutDepthTraversal(){
        List<String> res = Arrays.asList("K", "H", "G");
        assertEquals(tka.tOutDepthTraversal(tka.graph.transposeGraph()), res);
    }

    @Test
    public void testDepthFirstTraversal(){
        List<String> res = Arrays.asList("K", null, "H", null, "G");
        assertEquals(tka.depthFirstTraversal(tka.tOutDepthTraversal(tka.graph.transposeGraph())), res);
    }
}
