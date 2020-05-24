package de.hub.mse.ttc2020.solution.atl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.m2m.atl.emftvm.CodeBlock;
import org.eclipse.m2m.atl.emftvm.EmftvmFactory;
import org.eclipse.m2m.atl.emftvm.ExecEnv;
import org.eclipse.m2m.atl.emftvm.Field;
import org.eclipse.m2m.atl.emftvm.Metamodel;
import org.eclipse.m2m.atl.emftvm.Model;
import org.eclipse.m2m.atl.emftvm.Rule;
import org.eclipse.m2m.atl.emftvm.util.ClassModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.StackFrame;

import de.hub.mse.ttc2020.benchmark.AbstractTask;

public class ATLTask extends AbstractTask {

	protected static final CodeBlock EMPTY_CB = EmftvmFactory.eINSTANCE.createCodeBlock();

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
		final ExecEnv execEnv = new ManualExecEnvImpl();
		execEnv.registerMetaModel("V1", v1);
		execEnv.registerMetaModel("V2", v2);
		execEnv.loadModule(classModuleResolver, atlModule);
		execEnv.registerInputModel("IN", createModel());
		execEnv.registerOutputModel("OUT", createModel());
		execEnv.run(null); // trigger rule initialization
		return execEnv;
	}

	private Model createModel() {
		final Model model = EmftvmFactory.eINSTANCE.createModel();
		model.setResource(new ResourceImpl(URI.createURI("")));
		return model;
	}

	@Override
	public EObject migrate(final EObject instance) {
		trace = instance;
		return transform(migrate, instance, null);
	}

	@Override
	public EObject migrateBack(final EObject instance) {
		try {
			return transform(migrateBack, instance, trace);
		} finally {
			trace = null;
			migrate.getOutputModels().get("OUT").getResource().getContents().clear();
			migrateBack.getOutputModels().get("OUT").getResource().getContents().clear();
		}
	}

	protected EObject transform(final ExecEnv execEnv, final EObject instance, final EObject trace) {
		final Field traceField = execEnv.findStaticField(execEnv.eClass(), "trace");
		if (traceField != null) {
			traceField.setStaticValue(trace);
		}
		final Rule rule = execEnv.getRules().get(0);
		try {
			return (EObject) rule.matchManual(new StackFrame(execEnv, EMPTY_CB), new Object[] { instance });
		} finally {
			((ManualExecEnvImpl) execEnv).postRun();
		}
	}

}
