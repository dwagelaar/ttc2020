package de.hub.mse.ttc2020.solution.atl;

import java.util.Iterator;

import org.eclipse.m2m.atl.emftvm.CodeBlock;
import org.eclipse.m2m.atl.emftvm.Operation;
import org.eclipse.m2m.atl.emftvm.Rule;
import org.eclipse.m2m.atl.emftvm.RuleMode;
import org.eclipse.m2m.atl.emftvm.impl.ExecEnvImpl;
import org.eclipse.m2m.atl.emftvm.util.EMFTVMUtil;
import org.eclipse.m2m.atl.emftvm.util.StackFrame;
import org.eclipse.m2m.atl.emftvm.util.TimingData;
import org.eclipse.m2m.atl.emftvm.util.VMException;

public class ManualExecEnvImpl extends ExecEnvImpl {

	/**
	 * <!-- begin-user-doc. --> {@inheritDoc} <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public synchronized Object run(final TimingData timingData) {
		Object result = null;
		try {
			assert deletionQueue.isEmpty();
			if (!isRuleStateCompiled()) {
				for (final Rule r : getRules()) {
					r.compileState(this); // compile internal state for all registered rules
				}
			}
			for (final Rule r : getRules()) {
				resolveRuleModels(r);
			}
			final Iterator<Operation> mains = mainChain.iterator();
			if (!mains.hasNext()) {
				throw new UnsupportedOperationException(
						String.format("Operation %s not found", EMFTVMUtil.MAIN_OP_NAME));
			}
			// run all init() operations
			currentPhase = RuleMode.MANUAL;
			for (final Operation init : initChain) {
				final CodeBlock cb = init.getBody();
				if (cb.getStackLevel() > 0) {
					result = cb.execute(new StackFrame(this, cb));
				} else {
					cb.execute(new StackFrame(this, cb));
				}
			}
		} catch (final VMException e) {
			if (monitor != null) {
				monitor.error(e.getFrame(), e.getLocalizedMessage(), e);
			}
			throw e;
		}
		return result;
	}

	public synchronized void postRun() {
		if (monitor != null) {
			monitor.terminated();
		}
		this.matches = null;
		this.traces = null;
		this.uniqueResults = null;
		fieldContainer.clear();
		for (final Rule r : getRules()) {
			r.clearFields();
		}
		assert findStaticField(eClass(), "matches").getStaticValue() == null;
		assert findStaticField(eClass(), "traces").getStaticValue() == null;
	}

}
