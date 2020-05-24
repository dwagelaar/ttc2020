package de.hub.mse.ttc2020.solution.atl;

import org.eclipse.emf.ecore.EPackage;

import de.hub.mse.ttc2020.benchmark.AbstractTask;
import de.hub.mse.ttc2020.benchmark.AbstractTaskFactory;
import de.hub.mse.ttc2020.benchmark.TaskInfo;

public class ATLTaskFactory extends AbstractTaskFactory {

	public ATLTaskFactory() {
		super();
	}
	
	@Override
	public AbstractTask createTask(TaskInfo info, EPackage model1, EPackage model2) {
		switch (info) {
		case TASK_1_M1_M2_M1:
			return new Task_1_M1_M2_M1(model1, model2);
		case TASK_1_M2_M1_M2:
			return new Task_1_M2_M1_M2(model1, model2);
		case TASK_2_M1_M2_M1:
			return new Task_2_M1_M2_M1(model1, model2);
		case TASK_2_M2_M1_M2:
			return new Task_2_M2_M1_M2(model1, model2);
		case TASK_3_M1_M2_M1:
			return new Task_3_M1_M2_M1(model1, model2);
		case TASK_3_M2_M1_M2:
			return new Task_3_M2_M1_M2(model1, model2);
		case TASK_4_M1_M2_M1:
			return new Task_4_M1_M2_M1(model1, model2);
		case TASK_4_M2_M1_M2:
			return new Task_4_M2_M1_M2(model1, model2);	
				
		default:
			return null;
		}
	}

}
