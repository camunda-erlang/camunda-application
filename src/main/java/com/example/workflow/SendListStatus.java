package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendListStatus implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		 if (execution.getVariable("service_valid") == null ){
		        execution.setVariable("service_valid", true);
		   }
		 if (execution.getVariable("partner_dnd") == null ){
		        execution.setVariable("partner_dnd", false);
		   }
		 if (execution.getVariable("device_blacklist") == null ){
		        execution.setVariable("device_blacklist", false);
		   }
		 if (execution.getVariable("listed") == null ){
		        execution.setVariable("listed", "none");
		   }
		 if (execution.getBusinessKey() == null ){
		        execution.setProcessBusinessKey("businessKey");;
		   }
		 
		boolean serviceValid = (boolean)execution.getVariable("service_valid");
		boolean partnerDnd = (boolean)execution.getVariable("partner_dnd");
		boolean deviceBlacklist = (boolean)execution.getVariable("device_blacklist");
		String listed = (String)execution.getVariable("listed");

		

		
		execution.getProcessEngineServices().getRuntimeService()
		.createMessageCorrelation("ListStatus")
		.setVariable("service_valid", serviceValid)
		.setVariable("partner_dnd", partnerDnd)
		.setVariable("device_blacklist", deviceBlacklist)
		.setVariable("listed", listed)
		.processInstanceBusinessKey(execution.getBusinessKey())
		.correlate();

	}

}
