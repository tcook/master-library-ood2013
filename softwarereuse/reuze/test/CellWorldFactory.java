package reuze.test;
import com.software.reuze.a_CellWorld;

//package aima.core.environment.cellworld;

/**
 * 
 * @author Ciaran O'Reilly
 * 
 */
public class CellWorldFactory {

	/**
	 * Create the cell world as defined in Figure 17.1 in AIMA3e. (a) A simple 4
	 * x 3 environment that presents the agent with a sequential decision
	 * problem.
	 * 
	 * @return a cell world representation of Fig 17.1 in AIMA3e.
	 */
	public static a_CellWorld<Double> createCellWorldForFig17_1() {
		a_CellWorld<Double> cw = new a_CellWorld<Double>(4, 3, -0.04);

		cw.removeCell(2, 2);

		cw.getCellAt(4, 3).setContent(1.0);
		cw.getCellAt(4, 2).setContent(-1.0);

		return cw;
	}
}
