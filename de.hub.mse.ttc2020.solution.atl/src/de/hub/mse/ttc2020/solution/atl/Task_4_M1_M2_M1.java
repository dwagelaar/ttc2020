package de.hub.mse.ttc2020.solution.atl;

import java.util.Calendar;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import de.hub.mse.ttc2020.benchmark.AbstractTask;

public class Task_4_M1_M2_M1 extends AbstractTask {

	private EObject trace;

	public Task_4_M1_M2_M1(EPackage model1, EPackage model2) {
		super(model1, model2);
	}

	@Override
	public EObject migrate(EObject instance) {
		EObject dinst1 = (EObject) instance.eGet(instance.eClass().getEStructuralFeature("dog"));
		this.trace = dinst1;
		EClass dclass1 = dinst1.eClass();
		EClass dclass2 = (EClass) getModel2().getEClassifier("Dog");
		EObject dinst2 = getModel2().getEFactoryInstance().create(dclass2);

		dinst2.eSet(dclass2.getEStructuralFeature("name"), dinst1.eGet(dclass1.getEStructuralFeature("name")));

		// Referenced person
		EObject pinst1 = (EObject) dinst1.eGet(dclass1.getEStructuralFeature("owner"));
		EClass pclass1 = pinst1.eClass();
		EClass pclass2 = (EClass) getModel2().getEClassifier("Person");
		EObject pinst2 = getModel2().getEFactoryInstance().create(pclass2);

		pinst2.eSet(pclass2.getEStructuralFeature("name"), pinst1.eGet(pclass1.getEStructuralFeature("name")));
		int age = Integer.parseInt(pinst1.eGet(pclass1.getEStructuralFeature("age")).toString());
		int ybirth = Calendar.getInstance().get(Calendar.YEAR) - age;
		pinst2.eSet(pclass2.getEStructuralFeature("ybirth"), ybirth);

		dinst2.eSet(dclass2.getEStructuralFeature("owner"), pinst2);

		// Container
		EClass cclass2 = (EClass) getModel2().getEClassifier("Container");
		EObject cinst2 = getModel2().getEFactoryInstance().create(cclass2);
		cinst2.eSet(cclass2.getEStructuralFeature("dog"), dinst2);
		cinst2.eSet(cclass2.getEStructuralFeature("person"), pinst2);

		return cinst2;
	}

	@Override
	public EObject migrateBack(EObject instance) {

		EObject dinst2 = (EObject) instance.eGet(instance.eClass().getEStructuralFeature("dog"));
		EClass dclass2 = dinst2.eClass();
		EClass dclass1 = (EClass) getModel1().getEClassifier("Dog");
		EObject dinst1 = getModel1().getEFactoryInstance().create(dclass1);

		dinst1.eSet(dclass1.getEStructuralFeature("name"), dinst2.eGet(dclass2.getEStructuralFeature("name")));
		dinst1.eSet(dclass1.getEStructuralFeature("age"), trace.eGet(dclass1.getEStructuralFeature("age")));

		// Referenced person
		EObject pinst2 = (EObject) dinst2.eGet(dclass2.getEStructuralFeature("owner"));
		EClass pclass2 = pinst2.eClass();
		EClass pclass1 = (EClass) getModel1().getEClassifier("Person");
		EObject pinst1 = getModel1().getEFactoryInstance().create(pclass1);
		pinst1.eSet(pclass1.getEStructuralFeature("name"), pinst2.eGet(pclass2.getEStructuralFeature("name")));

		int ybirth = Integer.parseInt(pinst2.eGet(pclass2.getEStructuralFeature("ybirth")).toString());
		int age = Calendar.getInstance().get(Calendar.YEAR) - ybirth;
		pinst1.eSet(pclass1.getEStructuralFeature("age"), age);

		dinst1.eSet(dclass1.getEStructuralFeature("owner"), pinst1);
		
		// Container
		EClass cclass1 = (EClass) getModel1().getEClassifier("Container");
		EObject cinst1 = getModel1().getEFactoryInstance().create(cclass1);
		cinst1.eSet(cclass1.getEStructuralFeature("dog"), dinst1);
		cinst1.eSet(cclass1.getEStructuralFeature("person"), pinst1);

		return cinst1;
	}
}
