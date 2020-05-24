package de.hub.mse.ttc2020.solution.atl;

import java.util.Arrays;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.m2m.atl.emftvm.EmftvmFactory;
import org.eclipse.m2m.atl.emftvm.ExecEnv;
import org.eclipse.m2m.atl.emftvm.Field;
import org.eclipse.m2m.atl.emftvm.Metamodel;
import org.eclipse.m2m.atl.emftvm.Model;
import org.eclipse.m2m.atl.emftvm.util.ClassModuleResolver;

import de.hub.mse.ttc2020.benchmark.AbstractTask;

public class ATLTask extends AbstractTask {

	private final ClassModuleResolver classModuleResolver = new ClassModuleResolver(getClass());
	private final Metamodel v1;
	private final Metamodel v2;
	protected final ExecEnv migrate;
	protected final ExecEnv migrateBack;

	protected EObject trace;

	public ATLTask(final EPackage model1, final EPackage model2, final String atlModule) {
		super(model1, model2);
		v1 = createMetamodel(model1);
		v2 = createMetamodel(model2);
		migrate = createExecEnv(atlModule);
		migrateBack = createExecEnv(atlModule + "_back");
	}

	private Metamodel createMetamodel(final EPackage ePackage) {
		final Metamodel metamodel = EmftvmFactory.eINSTANCE.createMetamodel();
		metamodel.setResource(ePackage.eResource());
		return metamodel;
	}

	private ExecEnv createExecEnv(final String atlModule) {
		final ExecEnv execEnv = EmftvmFactory.eINSTANCE.createExecEnv();
		execEnv.registerMetaModel("V1", v1);
		execEnv.registerMetaModel("V2", v2);
		execEnv.loadModule(classModuleResolver, atlModule);
		return execEnv;
	}

	@Override
	public EObject migrate(final EObject instance) {
		trace = instance;
		return transform(migrate, instance, null);
	}

	@Override
	public EObject migrateBack(final EObject instance) {
		return transform(migrateBack, instance, trace);
	}

	protected EObject transform(final ExecEnv execEnv, final EObject instance, final EObject trace) {
		final Model outputModel = wrapAsModel();
		execEnv.registerInputModel("IN", wrapAsModel(instance));
		execEnv.registerOutputModel("OUT", outputModel);
		final Field traceField = execEnv.findStaticField(execEnv.eClass(), "trace");
		if (traceField != null) {
			traceField.setStaticValue(trace);
		}
		execEnv.run(null);
		return outputModel.getResource().getContents().get(0);
	}

	private Model wrapAsModel(EObject... instances) {
		final Model model = EmftvmFactory.eINSTANCE.createModel();
		model.setResource(new ResourceImpl(URI.createURI("")));
		model.getResource().getContents().addAll(Arrays.asList(instances));
		return model;
	}

}
