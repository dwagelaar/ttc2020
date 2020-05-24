package de.hub.mse.ttc2020.solution.atl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

public class ATLReverseTask extends ATLTask {

	public ATLReverseTask(final EPackage model1, final EPackage model2, final String atlModule) {
		super(model1, model2, atlModule);
	}

	@Override
	public EObject migrate(final EObject instance) {
		trace = instance;
		return transform(migrateBack, instance, null);
	}

	@Override
	public EObject migrateBack(final EObject instance) {
		try {
			return transform(migrate, instance, trace);
		} finally {
			trace = null;
			migrate.getOutputModels().get("OUT").getResource().getContents().clear();
			migrateBack.getOutputModels().get("OUT").getResource().getContents().clear();
		}
	}

}
