package io.pddl.executor.support;

import java.util.HashMap;
import java.util.Map;

import io.pddl.datasource.PartitionDataSource;
import io.pddl.executor.ExecutorContext;
import io.pddl.sqlparser.SQLParsedResult;
import io.pddl.util.MapUtils;

public class ExecutorContextSupport implements ExecutorContext{

	private int operationType= 0;
	
	private PartitionDataSource partitionDataSource;
	
	private Map<String,Object> attributes;
	
	private String statementName;
	
	private Object parameterObject;
	
	private SQLParsedResult sqlParsedResult;
	
	@Override
	public PartitionDataSource getPartitionDataSource() {
		return partitionDataSource;
	}

	@Override
	public int getOperationType() {
		return operationType;
	}
	
	public void setOperationType(int op){
		operationType= op;
	}
	
	public void setPartitionDataSource(PartitionDataSource partitionDataSource){
		this.partitionDataSource= partitionDataSource;
	}

	@Override
	public boolean isSelectable() {
		return operationType == OP_SELECT;
	}

	@Override
	public boolean isPersistent() {
		return (operationType & OP_PERSISTENCE) != 0;
	}

	@Override
	public boolean isTransactional() {
		return (operationType & OP_TRANSACTION) != 0;
	}

	@Override
	public String getStatementName() {
		return statementName;
	}
	
	public void setSatementName(String statementName){
		this.statementName= statementName;
	}

	@Override
	public Object getParameterObject() {
		return parameterObject;
	}
	
	public void setParameterObject(Object parameterObject){
		this.parameterObject= parameterObject;
	}

	@Override
	public Object getAttribute(String key) {
		if(attributes== null){
			return null;
		}
		return attributes.get(key);
	}
	
	public synchronized void setAttribute(String key,Object value){
		if(attributes== null){
			attributes= new HashMap<String,Object>();
		}
		attributes.put(key, value);
	}
	
	@Override
	public ExecutorContext clone(){
		ExecutorContextSupport ctx= new ExecutorContextSupport();
		ctx.statementName= this.statementName;
		ctx.parameterObject= this.parameterObject;
		ctx.operationType= this.operationType;
		ctx.partitionDataSource= this.partitionDataSource;
		if(!MapUtils.isEmpty(this.attributes)){
			(ctx.attributes= new HashMap<String,Object>()).putAll(this.attributes);
		}
		return ctx;
	}
	
	public void setSQLParsedResult(SQLParsedResult sqlParsedResult){
		this.sqlParsedResult= sqlParsedResult;
	}

	@Override
	public SQLParsedResult getSQLParsedResult() {
		return sqlParsedResult;
	}
}
