import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;

import java.util.List;

/**
 * Created by Alexander on 14.04.2015.
 */
public interface ComparisonService {
    //TODO: implement the real ComparisonService Interface
    public void init();
    public void createGraph();
    public void getGraphOptimalSolution();
    public void startAlgorithm();
    public void runComparison();
}
