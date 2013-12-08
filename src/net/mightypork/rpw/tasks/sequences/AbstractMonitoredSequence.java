package net.mightypork.rpw.tasks.sequences;

import net.mightypork.rpw.App;
import net.mightypork.rpw.Config;
import net.mightypork.rpw.gui.windows.messages.Alerts;
import net.mightypork.rpw.gui.windows.messages.DialogProgressTerminal;


public abstract class AbstractMonitoredSequence extends AbstractSequence {

	private DialogProgressTerminal dpt = null;


	@Override
	protected abstract boolean step(int step);


	@Override
	public abstract int getStepCount();


	@Override
	protected final void before() {

		Alerts.loading(true);

		if(Config.SHOW_LOG_TERMINAL && App.getFrame()!=null) {
			dpt = new DialogProgressTerminal(getMonitorTitle(), getMonitorHeading());
		
			dpt.openDialog();
		}
		
		doBefore();
	}
	
	protected abstract String getMonitorHeading();


	protected String getMonitorTitle() {
		return getMonitorHeading();
	}


	@Override
	protected void beforeStep(int index) {
		if(dpt != null) dpt.onStepStarted(index, getStepCount(), getStepName(index));
	}
	
	protected abstract void doBefore();


	@Override
	protected final void after(boolean success) {

		Alerts.loading(false);
		
		if(dpt != null) {
			dpt.allowClose(true);
		}
		
		doAfter(success);
	}
	
	protected abstract void doAfter(boolean success);


	@Override
	public abstract String getStepName(int step);

}