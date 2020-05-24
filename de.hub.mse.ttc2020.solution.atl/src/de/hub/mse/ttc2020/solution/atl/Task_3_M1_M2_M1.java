package de.hub.mse.ttc2020.solution.atl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import de.hub.mse.ttc2020.benchmark.AbstractTask;

public class Task_3_M1_M2_M1 extends AbstractTask {

	public Task_3_M1_M2_M1(EPackage model1, EPackage model2) {
		super(model1, model2);
	}

	@Override
	public EObject migrate(EObject instance) {
		EClass class1 = instance.eClass();
		EClass class2 = (EClass) getModel2().getEClassifier("Person");
		EObject instance2 = getModel2().getEFactoryInstance().create(class2);
		instance2.eSet(class2.getEStructuralFeature("name"), instance.eGet(class1.getEStructuralFeature("name")));
		instance2.eSet(class2.getEStructuralFeature("age"), instance.eGet(class1.getEStructuralFeature("age")));

		return instance2;
	}

	@Override
	public EObject migrateBack(EObject instance) {
		EClass class2 = instance.eClass();
		EClass class1 = (EClass) getModel1().getEClassifier("Person");
		EObject instance1 = getModel1().getEFactoryInstance().create(class1);
		instance1.eSet(class1.getEStructuralFeature("age"), instance.eGet(class2.getEStructuralFeature("age")));

		Object name = instance.eGet(class2.getEStructuralFeature("name"));
		if (name != null) {
			instance1.eSet(class1.getEStructuralFeature("name"), name.toString());
		} else {			
			instance1.eSet(class1.getEStructuralFeature("name"), "");
		}

		return instance1;
	}

}
